package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.GardenModel;
import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.User;
import ar.germin.api.application.exceptions.GardenNameAlreadyExistsException;
import ar.germin.api.application.exceptions.GardenNotFoundException;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.SaveGardenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class GardenJdbcAdapter implements GetGardenRepository, SaveGardenRepository {
    private static final String SELECT_GARDEN_BY_ID_PATH = "sql/selectGardenById.sql";
    private static final String SAVE_GARDEN_PATH = "sql/saveGarden.sql";
    private static final String SELECT_GARDENS_BY_ID_USER = "sql/selectGardensByIdUser.sql";
    private static final String SELECT_ALL_GARDENS_BY_USER = "sql/selectAllGardensByIdUser.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String selectGardenByIdSql;
    private final String saveGardenSql;
    private final String selectGardensByIdUserSql;
    private final String selectAllGardensByUserSql;

    public GardenJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.selectGardenByIdSql = sqlReader.readSql(SELECT_GARDEN_BY_ID_PATH);
        this.saveGardenSql = sqlReader.readSql(SAVE_GARDEN_PATH);
        this.selectGardensByIdUserSql = sqlReader.readSql(SELECT_GARDENS_BY_ID_USER);
        this.selectAllGardensByUserSql = sqlReader.readSql(SELECT_ALL_GARDENS_BY_USER);
    }

    @Override
    public Garden getById(Integer id) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        log.info("Querying garden with sql [{}] with params: [{}]", selectGardenByIdSql, params);

        return Optional
                .of(this.namedParameterJdbcTemplate.query(selectGardenByIdSql, params, BeanPropertyRowMapper.newInstance(GardenModel.class)))
                .map(GardenModel::toDomainFromModelList)
                .orElseThrow(() -> {
                    log.error("Garden with id {} not found", id);
                    return new GardenNotFoundException();
                });

    }

    @Override
    public List<Garden> getByUserId(Integer userId) {
        // TODO: implement me
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("idUser", userId);

        log.info("Querying gardens with sql [{}] with param: [{}]", selectGardensByIdUserSql, parameters);

        return Optional
                .of(this.namedParameterJdbcTemplate.query(selectGardensByIdUserSql, parameters, BeanPropertyRowMapper.newInstance(GardenModel.class)))
                .map(GardenModel::toDomainFromModelListGardens)
                .orElseThrow(() -> {
                    log.error("Gardens with id {} not found", userId);
                    return new GardenNotFoundException();
                });

    }
    // asssssssssssssss
    @Override
    public List<Garden> getAllGardensByUserId(Integer userId) {

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("idUser", userId);

        log.info("Querying gardens with sql [{}] with param: [{}]", selectAllGardensByUserSql, parameters);

        return Optional
                .of(this.namedParameterJdbcTemplate.query(selectAllGardensByUserSql, parameters, BeanPropertyRowMapper.newInstance(GardenModel.class)))
                .map(GardenModel::toDomainFromModelAllListGardens)
                .orElseThrow(() -> {
                    log.error("Gardens with id {} not found", userId);
                    return new GardenNotFoundException();
                });
    }

    @Override
    public Garden getByIdAndUserId(Integer id, Integer userId) {
        // TODO: implement me
        return null;
    }



    @Override
    public Boolean save(Integer userId, String name) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("name", name)
                    .addValue("idUser", userId);
            log.info("Saving garden with sql [{}] with params: [{}]", saveGardenSql, params);

            this.namedParameterJdbcTemplate.update(saveGardenSql, params);

            return true;
        } catch (DuplicateKeyException ex) {
            log.error("Error saving garden for duplicate name", ex);
            // FIXME: esta excepción no va
            return false;
            //throw new GardenNameAlreadyExistsException("El nombre del jardin ya existe");
        }

    }


}
