package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.GardenModel;
import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.exceptions.GardenNotFoundException;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.SaveGardenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class GardenJdbcAdapter implements GetGardenRepository, SaveGardenRepository {
    private static final String SELECT_GARDEN_BY_ID_PATH = "sql/selectGardenById.sql";
    private static final String SAVE_GARDEN_PATH = "sql/saveGarden.sql";
    private static final String SELECT_GARDENS_BY_ID_USER_PATH = "sql/selectGardensByIdUser.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String selectGardenByIdSql;
    private final String saveGardenSql;
    private final String selectGardensByIdUserSql;

    public GardenJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.selectGardenByIdSql = sqlReader.readSql(SELECT_GARDEN_BY_ID_PATH);
        this.saveGardenSql = sqlReader.readSql(SAVE_GARDEN_PATH);
        this.selectGardensByIdUserSql = sqlReader.readSql(SELECT_GARDENS_BY_ID_USER_PATH);
    }

    @Override
    public Garden getById(Integer id) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", id);

            log.info("Querying garden with sql [{}] with params: [{}]", selectGardenByIdSql, params);

            return Optional
                    .ofNullable(this.namedParameterJdbcTemplate.queryForObject(selectGardenByIdSql, params, new BeanPropertyRowMapper<>(GardenModel.class)))
                    .map(GardenModel::toDomain)
                    .orElseThrow(() -> {
                        log.error("Garden with id {} not found", id);
                        return new GardenNotFoundException();
                    });
        } catch (EmptyResultDataAccessException ex){
            throw new GardenNotFoundException();
        }


    }


    @Override
    public List<Garden> getByUserId(Integer userId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("idUser", userId);

        log.info("Querying gardens with sql [{}] with param: [{}]", selectGardensByIdUserSql, parameters);

        List<GardenModel> gardenModels = this.namedParameterJdbcTemplate.query(selectGardensByIdUserSql, parameters, BeanPropertyRowMapper.newInstance(GardenModel.class));

        if (gardenModels.isEmpty()) {
            log.error("Gardens with user id {} not found", userId);
            throw new GardenNotFoundException();
        }

        return GardenModel.toDomainFromModelListGardens(gardenModels);
    }


    @Override
    public Integer save(Integer userId, String name) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("name", name)
                    .addValue("idUser", userId);
            log.info("Saving garden with sql [{}] with params: [{}]", saveGardenSql, params);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.namedParameterJdbcTemplate.update(saveGardenSql, params, keyHolder, new String[]{"id"});
            Integer generatedId = Optional.ofNullable(keyHolder.getKey()).map(Number::intValue).orElse(-1);

            return generatedId;
        } catch (DuplicateKeyException ex) {
            log.error("Error saving garden for duplicate name", ex);
            throw new GardenNotFoundException();
        }

    }


}
