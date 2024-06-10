package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.PlantModel;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.exceptions.ErrorPlantSaveException;
import ar.germin.api.application.exceptions.PlantNotFoundException;
import ar.germin.api.application.port.out.DeletePlantRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import ar.germin.api.application.port.out.SavePlantRepository;
import ar.germin.api.application.port.out.UpdatePlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponseException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class PlantsJdbcAdapter implements SavePlantRepository, DeletePlantRepository, UpdatePlantRepository, GetPlantRepository {
    private static final String SAVE_PLANT_PATH = "sql/savePlant.sql";
    private static final String DELETE_PLANT_PATH = "sql/deletePlant.sql";
    private static final String UPDATE_PLANT_PATH = "sql/updatePlant.sql";
    private static final String GET_PLANT_PATH = "sql/selectPlantByUserIdAndPlantId.sql";
    private static final String GET_PLANTS_BY_ID_GARDEN_AND_ID_USER_PATH = "sql/selectPlantsByIdGardenAndIdUser.sql";
    private static final String GET_PLANTS_BY_ID_USER_PATH = "sql/selectPlantsByIdUser.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String savePlantSql;
    private final String deletePlantSql;
    private final String updatePlantSql;
    private final String getPlantSql;
    private final String getPlantsByIdGardenAndIdUserSql;
    private final String getPlantsByIdUserSql;

    @Autowired
    public PlantsJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.savePlantSql = sqlReader.readSql(SAVE_PLANT_PATH);
        this.deletePlantSql = sqlReader.readSql(DELETE_PLANT_PATH);
        this.updatePlantSql = sqlReader.readSql(UPDATE_PLANT_PATH);
        this.getPlantSql = sqlReader.readSql(GET_PLANT_PATH);
        this.getPlantsByIdGardenAndIdUserSql = sqlReader.readSql(GET_PLANTS_BY_ID_GARDEN_AND_ID_USER_PATH);
        this.getPlantsByIdUserSql = sqlReader.readSql(GET_PLANTS_BY_ID_USER_PATH);
    }

    @Override
    public Integer save(SavePlantRepository.Params params) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("alias", params.getAlias())
                    .addValue("idGarden", params.getIdGarden()) // TODO: setear
                    .addValue("idUser", params.getIdUser())
                    .addValue("idPlantsCatalog", 1) // TODO: setear
                    //.addValue("favorite", params.getIsFavorite())
                    .addValue("height", params.getHeight())
                    .addValue("notes", params.getNotes())
                    .addValue("plantingDate", params.getPlantingDate());

            log.info("Saving plant with sql [{}] with params: [{}]", savePlantSql, sqlParams);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.namedParameterJdbcTemplate.update(savePlantSql, sqlParams, keyHolder, new String[]{"id"});
            Integer generatedId = Optional.ofNullable(keyHolder.getKey()).map(Number::intValue).orElse(-1);

            return generatedId;
        } catch (ErrorResponseException ex) {
            log.error("Error saving plant", ex);
            throw new ErrorPlantSaveException("No se pudo guardar la planta");
        }

    }

    @Override
    public void delete(Integer id) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("id", id);

            log.info("deleting plant with sql [{}] with params: [{}]", deletePlantSql, sqlParams);
            this.namedParameterJdbcTemplate.update(deletePlantSql, sqlParams);

        } catch (ErrorResponseException ex) {
            log.error("Error deleting plant", ex);
            throw new ErrorPlantSaveException("No se pudo borrar la planta");
        }
    }

    @Override
    public Integer update(UpdatePlantRepository.Params params) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("id", params.getId())
                    .addValue("alias", params.getAlias())
                    .addValue("idGarden", params.getIdGarden())
                    .addValue("isFavorite", params.getIsFavorite())
                    .addValue("isActive", params.getIsActive())
                    .addValue("height", params.getHeight())
                    .addValue("notes", params.getNotes())
                    .addValue("plantingDate", params.getPlantingDate());

            log.info("Updating plant with sql [{}] with params: [{}]", updatePlantSql, sqlParams);
            this.namedParameterJdbcTemplate.update(updatePlantSql, sqlParams);
            return params.getId();
        } catch (ErrorResponseException ex) {
            log.error("Error updating plant", ex);
            throw new ErrorPlantSaveException("No se pudo actualizar la planta");
        }
    }

    @Override
    public Plant getByIdUserAndIdPlant(Integer idUser, Integer idPlant) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("idUser", idUser)
                .addValue("idPlant", idPlant);

        log.info("Querying garden with sql [{}] with params: [{}]", getPlantSql, params);
        List<PlantModel> plantModels = this.namedParameterJdbcTemplate.query(getPlantSql, params, BeanPropertyRowMapper.newInstance(PlantModel.class));

        if (plantModels.isEmpty()) {
            log.error("Plant with id [{}] not found", idPlant);
            throw new PlantNotFoundException();
        }

        return plantModels.get(0).toDomain();
    }

    @Override
    public List<Plant> getByIdGardenAndIdUser(Integer idGarden, Integer idUser) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("idUser", idUser)
                .addValue("idGarden", idGarden);

        log.info("Querying plants with sql [{}] with params: [{}]", getPlantsByIdGardenAndIdUserSql, params);
        List<PlantModel> plantModels =
                this.namedParameterJdbcTemplate.query(getPlantsByIdGardenAndIdUserSql, params, BeanPropertyRowMapper.newInstance(PlantModel.class));

        return PlantModel.toDomainFromList(plantModels);
    }

    @Override
    public List<Plant> getByIdUser(Integer idUser) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("idUser", idUser);

        log.info("Querying plants with sql [{}] with params: [{}]", getPlantsByIdUserSql, params);
        List<PlantModel> plantModels =
                this.namedParameterJdbcTemplate.query(getPlantsByIdUserSql, params, BeanPropertyRowMapper.newInstance(PlantModel.class));

        return PlantModel.toDomainFromList(plantModels);
    }
}
