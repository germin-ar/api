package ar.germin.api.adapter.jdbc;

import ar.germin.api.application.domain.DiseaseCandidate;
import ar.germin.api.application.domain.PlantPhoto;
import ar.germin.api.application.exceptions.PlantPhotoDiseaseAlreadyExistException;
import ar.germin.api.application.port.out.SavePlantPhotoDiseaseRelationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class PlantPhotoDiseaseJdbcAdapter implements SavePlantPhotoDiseaseRelationRepository {
    private static final String SAVE_PLANT_PHOTO_DISEASE_PATH = "sql/savePlantPhotoDisease.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String savePlantPhotoDiseaseSql;

    public PlantPhotoDiseaseJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.savePlantPhotoDiseaseSql = sqlReader.readSql(SAVE_PLANT_PHOTO_DISEASE_PATH);
    }

    @Override
    public Integer save(DiseaseCandidate diseaseCandidate, PlantPhoto plantPhoto) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("idPlant", plantPhoto.getIdPlant())
                    .addValue("idPhoto", plantPhoto.getId())
                    .addValue("idDisease", diseaseCandidate.getId());

            log.info("Saving PlantPhotoDisease with sql [{}] with params: [{}]", savePlantPhotoDiseaseSql, sqlParams);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.namedParameterJdbcTemplate.update(savePlantPhotoDiseaseSql, sqlParams, keyHolder, new String[]{"id"});
            Integer generatedId = Optional.ofNullable(keyHolder.getKey()).map(Number::intValue).orElse(-1);

            return generatedId;
        } catch (DuplicateKeyException ex) {
            log.error("Error saving plant_photo_disease for duplicate candidate with idPhoto and idPlant", ex);
            throw new PlantPhotoDiseaseAlreadyExistException();
        }


    }
}
