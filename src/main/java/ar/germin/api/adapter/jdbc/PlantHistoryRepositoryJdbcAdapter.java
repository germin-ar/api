package ar.germin.api.adapter.jdbc;

import ar.germin.api.application.exceptions.ErrorPlantSaveException;
import ar.germin.api.application.port.out.SavePlantHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponseException;

import java.util.Optional;

@Slf4j
@Component
public class PlantHistoryRepositoryJdbcAdapter implements SavePlantHistoryRepository {

    private static final String SAVE_PLANT_HISTORY_PATH = "sql/savePlantHistory.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String savePlantHistorySql;

    @Autowired
    public PlantHistoryRepositoryJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.savePlantHistorySql = sqlReader.readSql(SAVE_PLANT_HISTORY_PATH);
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
}
