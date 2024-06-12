package ar.germin.api.adapter.jdbc;


import ar.germin.api.adapter.jdbc.models.PlantPhotoModel;
import ar.germin.api.application.domain.PlantPhoto;
import ar.germin.api.application.exceptions.ErrorPlantSaveException;
import ar.germin.api.application.port.out.GetPlantPhotosRepository;
import ar.germin.api.application.port.out.SavePlantPhotoRepository;
import lombok.extern.slf4j.Slf4j;
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
public class PlantPhotoJdbcAdapter implements SavePlantPhotoRepository, GetPlantPhotosRepository {
    private static final String SAVE_PLANT_PHOTO_PATH = "sql/savePlantPhoto.sql";
    private static final String GET_PLANT_PHOTOS_BY_PLANT_ID_PATH = "sql/selectPlantPhotosByPlantId.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String savePlantPhotoSql;
    private final String getPlantPhotosByPlantIdSql;

    public PlantPhotoJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.savePlantPhotoSql = sqlReader.readSql(SAVE_PLANT_PHOTO_PATH);
        this.getPlantPhotosByPlantIdSql = sqlReader.readSql(GET_PLANT_PHOTOS_BY_PLANT_ID_PATH);
    }

    @Override
    public Integer save(Params params) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("idPlant", params.getIdPlant())
                    .addValue("url", params.getUrl());

            log.info("Saving plant photo with sql [{}] with params: [{}]", savePlantPhotoSql, sqlParams);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.namedParameterJdbcTemplate.update(savePlantPhotoSql, sqlParams, keyHolder, new String[]{"id"});
            Integer generatedId = Optional.ofNullable(keyHolder.getKey()).map(Number::intValue).orElse(-1);

            return generatedId;
        } catch (ErrorResponseException ex) {
            log.error("Error saving plant photo", ex);
            throw new ErrorPlantSaveException("No se pudo guardar la foto de la planta");
        }
    }

    @Override
    public List<PlantPhoto> getByPlantId(Integer plantId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("plantId", plantId);

        log.info("Querying plant photos with sql [{}] with param: [{}]", getPlantPhotosByPlantIdSql, parameters);

        return Optional
                .of(this.namedParameterJdbcTemplate.query(getPlantPhotosByPlantIdSql, parameters, BeanPropertyRowMapper.newInstance(PlantPhotoModel.class)))
                .map(PlantPhotoModel::toDomainList)
                .orElse(Collections.emptyList());
    }
}
