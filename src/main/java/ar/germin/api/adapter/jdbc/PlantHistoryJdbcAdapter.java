package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.PlantHistoryModel;
import ar.germin.api.application.domain.PlantHistory;
import ar.germin.api.application.exceptions.ErrorPlantHistoryGetException;
import ar.germin.api.application.exceptions.ErrorPlantSaveException;
import ar.germin.api.application.port.out.GetPlantHistoryRepository;
import ar.germin.api.application.port.out.SavePlantHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponseException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class PlantHistoryJdbcAdapter implements SavePlantHistoryRepository, GetPlantHistoryRepository {

    private static final String SAVE_PLANT_HISTORY_PATH = "sql/savePlantHistory.sql";
    private static final String GET_PLANT_HISTORY_PATH = "sql/getPlantHistory.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String savePlantHistorySql;
    private final String getPlantHistory;

    @Autowired
    public PlantHistoryJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.savePlantHistorySql = sqlReader.readSql(SAVE_PLANT_HISTORY_PATH);
        this.getPlantHistory = sqlReader.readSql(GET_PLANT_HISTORY_PATH);
    }

    @Override
    public Integer save(Params params) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("alias", params.getAlias())
                    .addValue("idPlant", params.getIdPlant())
                    .addValue("notes", params.getNotes())
                    .addValue("height", params.getHeight())
                    .addValue("urlImage", params.getUrlImage())
                    .addValue("idDiseases", params.getIdDiseases());

            log.info("Saving history plant with sql [{}] with params: [{}]", savePlantHistorySql, sqlParams);
                    // todo diseases param

            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.namedParameterJdbcTemplate.update(savePlantHistorySql, sqlParams, keyHolder, new String[]{"id"});
            Integer generatedIdHistory = Optional.ofNullable(keyHolder.getKey()).map(Number::intValue).orElse(-1);

            return generatedIdHistory;
        } catch (ErrorResponseException ex){
            log.error("Error saving history plant", ex);
            throw new ErrorPlantSaveException("No se pudo guardar el historial de la planta");
        }
    }

    @Override
    public List<PlantHistory> getByPlantId(Integer id) {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("idPlant", id);

            log.info("Querying plant photos with sql [{}] with param: [{}]", getPlantHistory, parameters);

            return Optional
                    .of(this.namedParameterJdbcTemplate.query(getPlantHistory, parameters, BeanPropertyRowMapper.newInstance(PlantHistoryModel.class)))
                    .map(PlantHistoryModel::toDomainList)
                    .orElse(Collections.emptyList());
        } catch (ErrorResponseException ex){
            log.error("Error getting plant history by id", ex);
            throw new ErrorPlantHistoryGetException("No se pudo mostrar el historial de la planta");
        }

    }
}
