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
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String savePlantSql;
    private final String deletePlantSql;
    private final String updatePlantSql;
    private final String getPlantSql;

    @Autowired
    public PlantsJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.savePlantSql = sqlReader.readSql(SAVE_PLANT_PATH);
        this.deletePlantSql = sqlReader.readSql(DELETE_PLANT_PATH);
        this.updatePlantSql = sqlReader.readSql(UPDATE_PLANT_PATH);
        this.getPlantSql = sqlReader.readSql(GET_PLANT_PATH);
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
                    .addValue("id_garden", params.getId_garden())
                    .addValue("is_favorite", params.getIs_favorite())
                    .addValue("is_active", params.getIs_active())
                    .addValue("height", params.getHeight())
                    .addValue("notes", params.getNotes())
                    .addValue("planting_date", params.getPlantingDate());

            log.info("Updating plant with sql [{}] with params: [{}]", updatePlantSql, sqlParams);
            this.namedParameterJdbcTemplate.update(updatePlantSql, sqlParams);
            return params.getId();
        } catch (ErrorResponseException ex) {
            log.error("Error updating plant", ex);
            throw new ErrorPlantSaveException("No se pudo actualizar la planta");
        }
    }

    @Override
    public Plant get(Integer idUser, Integer idPlant) {
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
}
