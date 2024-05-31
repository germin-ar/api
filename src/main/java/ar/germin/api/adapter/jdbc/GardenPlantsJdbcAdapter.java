package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.GardenModel;
import ar.germin.api.application.domain.Garden;
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

import java.sql.Types;
import java.util.Optional;

@Component
@Slf4j
public class GardenPlantsJdbcAdapter implements GetGardenRepository, SaveGardenRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public GardenPlantsJdbcAdapter(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public Garden getById(String id) {
        String sql = "select id, name, id_user from garden.garden where id = :id ";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id, Types.OTHER);

        log.info("Querying garden with sql [{}] with params: [{}]", sql, params);

        return Optional
                .ofNullable(this.namedParameterJdbcTemplate.queryForObject(sql, params,
                        new BeanPropertyRowMapper<>(GardenModel.class)))
                .map(GardenModel::toDomain)
                .orElseThrow(() -> {
                    log.error("Garden with id {} not found", id);
                    return new GardenNotFoundException();
                });
    }

    @Override
    public Garden save(Garden garden) {
        try {
            String sql = "insert into garden.garden (id, name) values (:id, :name)";
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", garden.getId(), Types.OTHER)
                    .addValue("name", garden.getName());
            log.info("Saving garden with sql [{}] with params: [{}]", sql, params);

            this.namedParameterJdbcTemplate.update(sql, params);
            return garden;
        } catch (DuplicateKeyException ex) {
            log.error("Error saving garden for duplicate name", ex);
            throw new GardenNameAlreadyExistsException("El nombre del jardin ya existe");
        }

    }
}