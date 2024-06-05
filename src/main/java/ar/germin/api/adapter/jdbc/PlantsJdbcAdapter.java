package ar.germin.api.adapter.jdbc;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.exceptions.ErrorPlantSaveException;
import ar.germin.api.application.port.out.DeletePlantRepository;
import ar.germin.api.application.port.out.SavePlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponseException;

@Slf4j
@Component
public class PlantsJdbcAdapter implements SavePlantRepository, DeletePlantRepository {
    private static final String SAVE_PLANT_PATH = "sql/savePlant.sql";
    private static final String DELETE_PLANT_PATH = "sql/deletePlant.sql";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String savePlantSql;
    private final String deletePlantSql;

    @Autowired
    public PlantsJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.savePlantSql = sqlReader.readSql(SAVE_PLANT_PATH);
        this.deletePlantSql = sqlReader.readSql(DELETE_PLANT_PATH);
    }

    @Override
    public Plant save(SavePlantRepository.Params params) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("alias", params.getAlias())
                    .addValue("idGarden", params.getIdGarden()) // TODO: setear
                    .addValue("idPlantsCatalog", 1) // TODO: setear
                    //.addValue("favorite", params.getIsFavorite())
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
    public void delete(Integer id) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("id", id );

            log.info("deleting plant with sql [{}] with params: [{}]", deletePlantSql, sqlParams);
            this.namedParameterJdbcTemplate.update(deletePlantSql, sqlParams);

        } catch (ErrorResponseException ex) {
            log.error("Error deleting plant", ex);
            throw new ErrorPlantSaveException("No se pudo borrar la planta");
        }
    }
}
