package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.GardenModel;
import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.exceptions.ErrorPlantSaveException;
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
import org.springframework.web.ErrorResponseException;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class GardenPlantsJdbcAdapter implements GetGardenRepository, SaveGardenRepository {
    private static final String SELECT_GARDEN_BY_ID = "sql/selectGardenById.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String selectGardenByIdSql;

    public GardenPlantsJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.selectGardenByIdSql = sqlReader.readSql(SELECT_GARDEN_BY_ID);
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

    @Override
    public Boolean savePlant(Plant plant) {
        //TODO try catch
        try {


            String sql = "insert into garden.plant (name, id_garden, id_plants_catalog,creation_date, modification_date, description, favorite, height, sun_exposure, notes ) values(:name, :idGarden, :idPlantsCatalog, :creationDate, :modificationDate, :description, :favorite, :height, :sunExposure, :notes)";
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("name", plant.getAlias())
                    .addValue("idGarden", 1)
                    .addValue("idPlantsCatalog", 1)
                    .addValue("creationDate", plant.getCreationDate())
                    .addValue("modificationDate", plant.getModificationDate())
                    .addValue("description", plant.getDescription())
                    .addValue("favorite", plant.getFavorite())
                    .addValue("height", plant.getHeight())
                    .addValue("sunExposure", plant.getSunExposure())
                    .addValue("notes", plant.getNotes());

            log.info("Saving plant with sql [{}] with params: [{}]", sql, params);
            this.namedParameterJdbcTemplate.update(sql, params);
            return true;
        }catch (ErrorResponseException ex){
            log.error("Error saving plant", ex);
            throw new ErrorPlantSaveException("No se pudo guardar la planta");
        }



    }
}
