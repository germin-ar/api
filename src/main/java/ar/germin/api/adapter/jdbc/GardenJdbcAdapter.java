package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.GardenModel;
import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.exceptions.GardenNameAlreadyExistsException;
import ar.germin.api.application.exceptions.GardenNotFoundException;
import ar.germin.api.application.exceptions.PlantsNotFoundException;
import ar.germin.api.application.exceptions.UpdatePlantIdToNullException;
import ar.germin.api.application.port.out.DeleteGardenRepository;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.SaveGardenRepository;
import ar.germin.api.application.port.out.UpdatePlantRepository;
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
public class GardenJdbcAdapter implements GetGardenRepository, SaveGardenRepository, DeleteGardenRepository, UpdatePlantRepository {
    private static final String SELECT_GARDEN_BY_ID_PATH = "sql/selectGardenById.sql";
    private static final String SAVE_GARDEN_PATH = "sql/saveGarden.sql";
    private static final String DELETE_GARDEN_PATH = "sql/deleteGarden.sql";
    private static final String REMOVE_GARDEN_ID_FROM_PLANTS_PATH = "sql/removeGardenIdFromPlants.sql";
    private static final String UPDATE_PLANTS_SET_ID_GARDEN_TO_NULL_PATH = "sql/updatePlantsSetIdGardenToNull.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String selectGardenByIdSql;
    private final String saveGardenSql;
    private final String deleteGardenSql;
    private final String removeGardenIdFromPlantsSql;
    private final String updatePlantsSetIdGardenToNullSql;

    public GardenJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.selectGardenByIdSql = sqlReader.readSql(SELECT_GARDEN_BY_ID_PATH);
        this.saveGardenSql = sqlReader.readSql(SAVE_GARDEN_PATH);
        this.deleteGardenSql = sqlReader.readSql(DELETE_GARDEN_PATH);
        this.removeGardenIdFromPlantsSql = sqlReader.readSql(REMOVE_GARDEN_ID_FROM_PLANTS_PATH);
        this.updatePlantsSetIdGardenToNullSql = sqlReader.readSql(UPDATE_PLANTS_SET_ID_GARDEN_TO_NULL_PATH);
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
        return List.of();
    }

    @Override
    public Garden getByIdAndUserId(Integer id, Integer userId) {
        // TODO: implement me
        return null;
    }

    @Override
    public Garden save(Integer userId, String name) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("name", name)
                    .addValue("idUser", userId);
            log.info("Saving garden with sql [{}] with params: [{}]", saveGardenSql, params);

            this.namedParameterJdbcTemplate.update(saveGardenSql, params);
            return null;
        } catch (DuplicateKeyException ex) {
            log.error("Error saving garden for duplicate name", ex);
            // FIXME: esta excepción no va
            throw new GardenNameAlreadyExistsException("El nombre del jardin ya existe");
        }

    }

    @Override
    public void deleteGardenById(Integer gardenId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", gardenId);
        log.info("Deleting garden with sql [{}] with params: [{}]", deleteGardenSql, params);

        int rowsAffected = this.namedParameterJdbcTemplate.update(deleteGardenSql, params);
        if (rowsAffected == 0) {
            log.error("Garden with id {} not found", gardenId);
            throw new GardenNotFoundException();
        }

        // Remove garden_id from plants
        log.info("Removing garden id from plants with sql [{}] with params: [{}]", removeGardenIdFromPlantsSql, params);
        this.namedParameterJdbcTemplate.update(removeGardenIdFromPlantsSql, params);
    }

    @Override
    public void removeGardenIdFromPlants(Integer gardenId) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("idGarden", gardenId);
            log.info("Removing garden id from plants with sql [{}] with params: [{}]", removeGardenIdFromPlantsSql, params);
            int rowsAffected = this.namedParameterJdbcTemplate.update(removeGardenIdFromPlantsSql, params);
            if (rowsAffected == 0) {
                throw new PlantsNotFoundException("No plants were associated with garden id: " + gardenId);
            }
        } catch (Exception ex) {
            log.error("Error removing garden id from plants", ex);
            throw new UpdatePlantIdToNullException("Error removing garden id from plants: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void updatePlantsSetIdGardenToNull(Integer gardenId) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("gardenId", gardenId);
            log.info("Updating plants to set idGarden to null with sql [{}] with params: [{}]", updatePlantsSetIdGardenToNullSql, params);

            this.namedParameterJdbcTemplate.update(updatePlantsSetIdGardenToNullSql, params);
        } catch (Exception ex) {
            log.error("Error updating plants to set idGarden to null", ex);
            throw new UpdatePlantIdToNullException("Error updating plants to set idGarden to null: " + ex.getMessage(), ex);
        }
    }
}
