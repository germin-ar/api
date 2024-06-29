package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.DiseaseCandidateModel;
import ar.germin.api.application.domain.DiseaseCandidate;
import ar.germin.api.application.exceptions.ErrorPlantSaveException;
import ar.germin.api.application.port.out.GetCandidateDiseasePlantsRepository;
import ar.germin.api.application.port.out.SaveCandidateDiseasePlantsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponseException;

import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class PlantDiseaseJdbcAdapter implements SaveCandidateDiseasePlantsRepository, GetCandidateDiseasePlantsRepository {
    private static final String SAVE_PLANT_DISEASE_PATH = "sql/savePlantDisease.sql";
    private static final String SELECT_PLANT_DISEASE_PATH = "sql/selectDiseaseCandidateBySlug.sql";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String savePlantDiseaseSql;
    private final String selectPlantDiseaseSql;

    public PlantDiseaseJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.savePlantDiseaseSql = sqlReader.readSql(SAVE_PLANT_DISEASE_PATH);
        this.selectPlantDiseaseSql = sqlReader.readSql(SELECT_PLANT_DISEASE_PATH);
    }

    @Override
    public Integer save(DiseaseCandidate diseaseCandidate) {
        try {
            MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue("name", diseaseCandidate.getName())
                    .addValue("scientificNameDisease", diseaseCandidate.getScientificNameDisease())
                    .addValue("entityId", diseaseCandidate.getEntityId())
                    .addValue("language", diseaseCandidate.getLanguage())
                    .addValue("eppoCode", diseaseCandidate.getEppoCode())
                    .addValue("type", diseaseCandidate.getType())
                    .addValue("commonName", diseaseCandidate.getCommonNames())
                    .addValue("kingdomTaxonomy", diseaseCandidate.getKingdomTaxonomy())
                    .addValue("wikiUrls", diseaseCandidate.getWikiUrls())
                    .addValue("classTaxonomy", diseaseCandidate.getClassTaxonomy())
                    .addValue("orderTaxonomy", diseaseCandidate.getOrderTaxonomy())
                    .addValue("familyTaxonomy", diseaseCandidate.getFamilyTaxonomy())
                    .addValue("phylumTaxonomy", diseaseCandidate.getPhylumTaxonomy())
                    .addValue("genusTaxonomy", diseaseCandidate.getGenusTaxonomy())
                    .addValue("slug", diseaseCandidate.toSlugFormat());

            log.info("Saving diseasePlant with sql [{}] with params: [{}]",savePlantDiseaseSql, sqlParams);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.namedParameterJdbcTemplate.update(savePlantDiseaseSql, sqlParams, keyHolder, new String[]{"id"});
            Integer generatedId = Optional.ofNullable(keyHolder.getKey()).map(Number::intValue).orElse(-1);

            return generatedId;
        } catch (ErrorResponseException ex) {
            log.error("Error saving plant", ex);
            throw new ErrorPlantSaveException("No se pudo guardar la planta");
        }

    }

    @Override
    public DiseaseCandidate get(String slugFormat) {
        try {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                    .addValue("slug", slugFormat);

            DiseaseCandidateModel diseaseCandidateModel = this.namedParameterJdbcTemplate.queryForObject(
                    selectPlantDiseaseSql,
                    parameterSource,
                    new BeanPropertyRowMapper<>(DiseaseCandidateModel.class));
            return diseaseCandidateModel.toDomain();
        } catch (Exception ex){
            log.error("Error querying disease candidate with scientific name [{}]", slugFormat);
            throw ex;
        }
    }
}
