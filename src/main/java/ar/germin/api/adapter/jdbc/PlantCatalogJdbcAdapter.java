package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.PlantCatalogModel;
import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.exceptions.ErrorPlantSaveException;
import ar.germin.api.application.port.out.GetPlantCatalogRepository;
import ar.germin.api.application.port.out.SavePlantCatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponseException;

import java.util.Optional;


@Component
@Slf4j
public class PlantCatalogJdbcAdapter implements GetPlantCatalogRepository, SavePlantCatalogRepository {
    private static final String SELECT_PLANT_CATALOG_BY_SCIENTIFIC_NAME_PATH = "sql/selectPlantCatalogByScientificName.sql";
    private static final String SAVE_PLANT_CATALOG_PATH = "sql/savePlantCatalog.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String selectPlantCatalogByScientificNameSql;
    private final String savePlantCatalogSql;

    public PlantCatalogJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.selectPlantCatalogByScientificNameSql = sqlReader.readSql(SELECT_PLANT_CATALOG_BY_SCIENTIFIC_NAME_PATH);
        this.savePlantCatalogSql =sqlReader.readSql(SAVE_PLANT_CATALOG_PATH);
    }

    @Override
    public PlantCatalog getPlantCatalog(String slugScientificName) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("slug_scientific_name", slugScientificName);
        log.info("Querying plant_catalog with SQL [{}] with params: [{}]", selectPlantCatalogByScientificNameSql, params);

        RowMapper<PlantCatalogModel> rowMapper = (rs, rowNum) -> {
            PlantCatalogModel model = new PlantCatalogModel();
            model.setId(rs.getInt("id"));
            model.setScientificName(rs.getString("scientific_name"));
            model.setDescription(rs.getString("description"));
            model.setSlug(rs.getString("slug_scientific_name"));


            return model;
        };

        try {
            PlantCatalogModel plantCatalogModel = this.namedParameterJdbcTemplate.queryForObject(
                    selectPlantCatalogByScientificNameSql,
                    params,
                    rowMapper
            );
            return plantCatalogModel.toDomain();
        } catch (EmptyResultDataAccessException e) {
            log.warn("PlantCatalog with scientific name [{}] not found", slugScientificName);
            return PlantCatalog.builder().build();
        } catch (Exception e) {
            log.error("Error querying plant_catalog with scientific name [{}]", slugScientificName, e);
            throw e;
        }
    }

    @Override
    public Integer save(PlantCatalog plantCatalog) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("description", plantCatalog.getDescription())
                    .addValue("scientific_name",plantCatalog.getScientificName())
                    .addValue("slug_scientific_name",plantCatalog.getSlug());

            log.info("Saving plant with sql [{}] with params: [{}]", savePlantCatalogSql, sqlParams);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.namedParameterJdbcTemplate.update(savePlantCatalogSql, sqlParams, keyHolder, new String[]{"id"});

          return Optional.ofNullable(keyHolder.getKey()).map(Number::intValue).orElse(-1);
        } catch (ErrorResponseException ex) {
            log.error("Error saving plantCatalog", ex);
            throw new ErrorPlantSaveException("No se pudo guardar el plantCatalog");
        }
    }
}
