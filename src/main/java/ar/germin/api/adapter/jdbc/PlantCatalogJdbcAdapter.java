package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.PlantCatalogModel;
import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.exceptions.ErrorPlantSaveException;
import ar.germin.api.application.exceptions.PlantCatalogNotFoundException;
import ar.germin.api.application.port.out.GetPlantCatalogRepository;
import ar.germin.api.application.port.out.SavePlantCatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponseException;



@Component
@Slf4j
public class PlantCatalogJdbcAdapter implements GetPlantCatalogRepository, SavePlantCatalogRepository {
    public static final String SELECT_PLANT_CATALOG_BY_SCIENTIFIC_NAME_PATH = "sql/selectPlantCatalogByScientificName.sql";
    public static final String SAVE_PLANT_CATALOG_PATH = "sql/savePlantCatalog.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String selectPlantCatalogByScientificNameSql;
    private final String savePlantCatalogSql;

    public PlantCatalogJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.selectPlantCatalogByScientificNameSql = sqlReader.readSql(SELECT_PLANT_CATALOG_BY_SCIENTIFIC_NAME_PATH);
        this.savePlantCatalogSql = sqlReader.readSql(SAVE_PLANT_CATALOG_PATH);
    }


    @Override
    public PlantCatalog getPlantCatalog(String slugScientificName) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("slugScientificName", slugScientificName);

            PlantCatalogModel plantCatalogModel = this.namedParameterJdbcTemplate.queryForObject(
                    selectPlantCatalogByScientificNameSql,
                    params,
                    new BeanPropertyRowMapper<>(PlantCatalogModel.class));

            // Assuming PlantCatalogModel has toDomain() method to convert to PlantCatalog
            return plantCatalogModel.toDomain();
        } catch (EmptyResultDataAccessException e) {
            log.warn("PlantCatalog with scientific name [{}] not found", slugScientificName);
            throw new PlantCatalogNotFoundException();
        } catch (Exception e) {
            log.error("Error querying plant_catalog with scientific name [{}]", slugScientificName, e);
            throw e;
        }
    }



    @Override
    public void save(PlantCatalog plantCatalog) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("description", plantCatalog.getDescription())
                    .addValue("scientific_name", plantCatalog.getScientificName())
                    .addValue("slug_scientific_name", plantCatalog.getSlug())
                    .addValue("genus", plantCatalog.getGenus())
                    .addValue("family_name", plantCatalog.getFamilyName())
                    .addValue("max_size", plantCatalog.getMaxSize())
                    .addValue("fertilizer", plantCatalog.getFertilizer())
                    .addValue("watering_frecuency", plantCatalog.getWateringFrequency())
                    .addValue("pruning", plantCatalog.getPruning())
                    .addValue("soil", plantCatalog.getSoil())
                    .addValue("insecticide", plantCatalog.getInsecticide())
                    .addValue("tips", plantCatalog.getInsecticide());

            log.info("Saving plant with sql [{}] with params: [{}]", savePlantCatalogSql, sqlParams);

            this.namedParameterJdbcTemplate.update(savePlantCatalogSql, sqlParams);
        } catch (ErrorResponseException ex) {
            log.error("Error saving plantCatalog", ex);
            throw new ErrorPlantSaveException("No se pudo guardar el plantCatalog");
        }
    }
}
