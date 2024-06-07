package ar.germin.api.adapter.jdbc;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.exceptions.ErrorPlantSaveException;
import ar.germin.api.application.port.out.SavePlantRepository;
import ar.germin.api.application.port.out.UpdatePlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponseException;

@Slf4j
@Component
public class PlantsJdbcAdapter implements SavePlantRepository, UpdatePlantRepository {
    private static final String SAVE_PLANT_PATH = "sql/savePlant.sql";
    private static final String UPDATE_PLANTS_SET_ID_GARDEN_TO_NULL_PATH = "sql/updatePlantsSetIdGardenToNull.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String savePlantSql;
    private final String updatePlantsSetIdGardenToNullSql;

    @Autowired
    public PlantsJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.savePlantSql = sqlReader.readSql(SAVE_PLANT_PATH);
        this.updatePlantsSetIdGardenToNullSql = sqlReader.readSql(UPDATE_PLANTS_SET_ID_GARDEN_TO_NULL_PATH);
    }

    @Override
    public Plant save(SavePlantRepository.Params params) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("alias", params.getAlias())
                    .addValue("idGarden", params.getIdGarden()) // TODO: setear
                    .addValue("idPlantsCatalog", 1) // TODO: setear
                    .addValue("height", params.getHeight())
                    .addValue("notes", params.getNotes())
                    .addValue("plantingDate", params.getPlantingDate());

            log.info("Saving plant with sql [{}] with params: [{}]", savePlantSql, sqlParams);
            this.namedParameterJdbcTemplate.update(savePlantSql, sqlParams);
            return Plant.builder().build();
        } catch (ErrorResponseException ex) {
            log.error("Error saving plant", ex);
            throw new ErrorPlantSaveException("No se pudo guardar la planta");
        }
    }

    @Override
    public void updatePlantsSetIdGardenToNull(Integer gardenId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("idGarden", gardenId);
        log.info("Updating plants with sql [{}] with params: [{}]", updatePlantsSetIdGardenToNullSql, sqlParams);
        this.namedParameterJdbcTemplate.update(updatePlantsSetIdGardenToNullSql, sqlParams);
    }

    @Override
    public void removeGardenIdFromPlants(Integer gardenId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("gardenId", gardenId);
        log.info("Removing garden ID from plants with SQL [{}] with params: [{}]",
                updatePlantsSetIdGardenToNullSql, sqlParams);
        this.namedParameterJdbcTemplate.update(updatePlantsSetIdGardenToNullSql, sqlParams);
    }
}
