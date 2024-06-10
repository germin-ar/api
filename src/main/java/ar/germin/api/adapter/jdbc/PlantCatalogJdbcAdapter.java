package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.PlantCatalogModel;
import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.port.out.GetPlantCatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class PlantCatalogJdbcAdapter implements GetPlantCatalogRepository {
    private static final String SELECT_PLANT_CATALOG_BY_SCIENTIFIC_NAME_PATH = "sql/selectPlantCatalogByScientificName.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String selectPlantCatalogByScientificNameSql;

    public PlantCatalogJdbcAdapter(SqlReader sqlReader,NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.selectPlantCatalogByScientificNameSql = sqlReader.readSql(SELECT_PLANT_CATALOG_BY_SCIENTIFIC_NAME_PATH);
    }
    @Override
    public PlantCatalog getPlantCatalog(String scientificName) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("scientificName", scientificName);
        log.info("Querying plant_catalog with SQL [{}] with params: [{}]", selectPlantCatalogByScientificNameSql, params);

        RowMapper<PlantCatalogModel> rowMapper = (rs, rowNum) -> {
            PlantCatalogModel model = new PlantCatalogModel();
            model.setId(rs.getInt("id"));
            model.setScientificName(rs.getString("scientific_name"));
            model.setDescription(rs.getString("description"));
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
            log.warn("PlantCatalog with scientific name [{}] not found", scientificName);
            return PlantCatalog.builder().build();
        } catch (Exception e) {
            log.error("Error querying plant_catalog with scientific name [{}]", scientificName, e);
            throw e;
        }
    }

}
