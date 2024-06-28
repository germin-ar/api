package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.PlantCatalogModel;
import ar.germin.api.adapter.jdbc.models.PlantCatalogModelAlternative;
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

import java.util.List;


@Component
@Slf4j
public class PlantCatalogJdbcAdapter implements GetPlantCatalogRepository, SavePlantCatalogRepository {
    private static final String SELECT_PLANT_CATALOG_BY_SCIENTIFIC_NAME_PATH = "sql/selectPlantCatalogByScientificName.sql";
    private static final String SAVE_PLANT_CATALOG_PATH = "sql/savePlantCatalog.sql";
    private static final String GET_PLANTS_CATALOG_PATH = "sql/getPlantsCatalog.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String selectPlantCatalogByScientificNameSql;
    private final String savePlantCatalogSql;
    private final String selectPlantsCatalog;

    public PlantCatalogJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.selectPlantCatalogByScientificNameSql = sqlReader.readSql(SELECT_PLANT_CATALOG_BY_SCIENTIFIC_NAME_PATH);
        this.savePlantCatalogSql = sqlReader.readSql(SAVE_PLANT_CATALOG_PATH);
        this.selectPlantsCatalog = sqlReader.readSql(GET_PLANTS_CATALOG_PATH);
    }

    @Override
    public PlantCatalog getPlantCatalog(String slugScientificName) {


        try {

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("slugScientificName", slugScientificName);

            PlantCatalogModelAlternative plantCatalogModelAlternative = this.namedParameterJdbcTemplate.queryForObject(
                    selectPlantCatalogByScientificNameSql,
                    params,
                    new BeanPropertyRowMapper<>(PlantCatalogModelAlternative.class));
            return plantCatalogModelAlternative.toDomain();
        } catch (EmptyResultDataAccessException e) {
            log.warn("PlantCatalog with scientific name [{}] not found", slugScientificName);
            throw new PlantCatalogNotFoundException();
        } catch (Exception e) {
            log.error("Error querying plant_catalog with scientific name [{}]", slugScientificName, e);
            throw e;
        }
    }

    @Override
    public List<PlantCatalog> getPlantsCatalogs(Double minTemperature, Double maxTemperature, String luz, String temporada, Integer squareCentimeters) {

        try {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("minTemperature", minTemperature)
                    .addValue("maxTemperature", maxTemperature)
                    .addValue("luz", luz)
                    .addValue("temporada", temporada)
                    .addValue("squareCentimeters", squareCentimeters);

            log.info("querying plant_catalog with sql [{}] with params: [{}]", selectPlantsCatalog , params );
            List<PlantCatalogModelAlternative> plantCatalogModel =
                    this.namedParameterJdbcTemplate.query(selectPlantsCatalog, params, BeanPropertyRowMapper.newInstance(PlantCatalogModelAlternative.class));

            return PlantCatalogModelAlternative.toDomainFromList(plantCatalogModel);
        } catch (EmptyResultDataAccessException e){
            log.warn("Plant not found with min_temperature {}, max_temperature {}, luz {}, temporada {}", minTemperature, maxTemperature, luz, temporada);
            throw new PlantCatalogNotFoundException();
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

            //KeyHolder keyHolder = new GeneratedKeyHolder();
            //this.namedParameterJdbcTemplate.update(savePlantCatalogSql, sqlParams, keyHolder, new String[]{"id"});
            this.namedParameterJdbcTemplate.update(savePlantCatalogSql, sqlParams);
            //return Optional.ofNullable(keyHolder.getKey()).map(Number::intValue).orElse(-1);
        } catch (ErrorResponseException ex) {
            log.error("Error saving plantCatalog", ex);
            throw new ErrorPlantSaveException("No se pudo guardar el plantCatalog");
        }
    }
}
