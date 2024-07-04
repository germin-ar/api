package ar.germin.api.application.adapter.jdbc;

import ar.germin.api.adapter.jdbc.PlantDiseaseJdbcAdapter;
import ar.germin.api.adapter.jdbc.SqlReader;
import ar.germin.api.adapter.jdbc.models.DiseaseCandidateModel;
import ar.germin.api.application.domain.DiseaseCandidate;
import ar.germin.api.application.exceptions.DiseaseCandidateNotFoundException;
import ar.germin.api.application.exceptions.ErrorPlantSaveException;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlantDiseaseJdbcAdapterTest {


    @Test
    void save_withValidDiseaseCandidate_shouldReturnGeneratedId() {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        PlantDiseaseJdbcAdapter plantDiseaseJdbcAdapter = new PlantDiseaseJdbcAdapter(new SqlReader(), namedParameterJdbcTemplate);

        DiseaseCandidate diseaseCandidate = DiseaseCandidate.builder()
                .name("Test Disease")
                .scientificNameDisease("Test Scientific Name")
                .entityId("Entity123")
                .language("English")
                .eppoCode("EPPO123")
                .type("Type1")
                .commonNames("Test Common Names")
                .kingdomTaxonomy("Kingdom1")
                .classTaxonomy("Class1")
                .genusTaxonomy("Genus1")
                .orderTaxonomy("Order1")
                .familyTaxonomy("Family1")
                .phylumTaxonomy("Phylum1")
                .wikiUrls("http://testwiki.com")
                .build();


        KeyHolder keyHolder = new GeneratedKeyHolder();
        HashMap<String, Object> keyMap = new HashMap<>();
        keyMap.put("id", 1);
        keyHolder.getKeyList().add(keyMap);


        when(namedParameterJdbcTemplate.update(
                any(String.class),
                any(MapSqlParameterSource.class),
                any(KeyHolder.class),
                any(String[].class)))
                .thenAnswer(invocation -> {
                    KeyHolder kh = invocation.getArgument(2);
                    kh.getKeyList().add(keyMap);
                    return 1;
                });


        Integer result = plantDiseaseJdbcAdapter.save(diseaseCandidate);


        assertEquals(1, result);
    }


    @Test
    void get_withExistingScientificName_shouldReturnDiseaseCandidate() {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        PlantDiseaseJdbcAdapter plantDiseaseJdbcAdapter = new PlantDiseaseJdbcAdapter(new SqlReader(), namedParameterJdbcTemplate);

        String scientificName = "Test Scientific Name";
        DiseaseCandidateModel diseaseCandidateModel = new DiseaseCandidateModel();
        diseaseCandidateModel.setScientificNameDisease(scientificName);
        diseaseCandidateModel.setId(1);
        diseaseCandidateModel.setName("Test Disease");
        diseaseCandidateModel.setEntityId("Entity123");
        diseaseCandidateModel.setLanguage("English");
        diseaseCandidateModel.setEppoCode("EPPO123");
        diseaseCandidateModel.setType("Type1");
        diseaseCandidateModel.setCommonNames("Test Common Names");
        diseaseCandidateModel.setKingdomTaxonomy("Kingdom1");
        diseaseCandidateModel.setClassTaxonomy("Class1");
        diseaseCandidateModel.setGenusTaxonomy("Genus1");
        diseaseCandidateModel.setOrderTaxonomy("Order1");
        diseaseCandidateModel.setFamilyTaxonomy("Family1");
        diseaseCandidateModel.setPhylumTaxonomy("Phylum1");
        diseaseCandidateModel.setWikiUrls("http://testwiki.com");

        when(namedParameterJdbcTemplate.queryForObject((String) any(String.class), (SqlParameterSource) any(), any(BeanPropertyRowMapper.class)))
                .thenReturn(diseaseCandidateModel);


        DiseaseCandidate result = plantDiseaseJdbcAdapter.get(scientificName);


        assertEquals(scientificName, result.getScientificNameDisease());
        assertEquals(diseaseCandidateModel.getId(), result.getId());
        assertEquals(diseaseCandidateModel.getName(), result.getName());
        assertEquals(diseaseCandidateModel.getEntityId(), result.getEntityId());
        assertEquals(diseaseCandidateModel.getLanguage(), result.getLanguage());
        assertEquals(diseaseCandidateModel.getEppoCode(), result.getEppoCode());
        assertEquals(diseaseCandidateModel.getType(), result.getType());
        assertEquals(diseaseCandidateModel.getCommonNames(), result.getCommonNames());
        assertEquals(diseaseCandidateModel.getKingdomTaxonomy(), result.getKingdomTaxonomy());
        assertEquals(diseaseCandidateModel.getClassTaxonomy(), result.getClassTaxonomy());
        assertEquals(diseaseCandidateModel.getGenusTaxonomy(), result.getGenusTaxonomy());
        assertEquals(diseaseCandidateModel.getOrderTaxonomy(), result.getOrderTaxonomy());
        assertEquals(diseaseCandidateModel.getFamilyTaxonomy(), result.getFamilyTaxonomy());
        assertEquals(diseaseCandidateModel.getPhylumTaxonomy(), result.getPhylumTaxonomy());
        assertEquals(diseaseCandidateModel.getWikiUrls(), result.getWikiUrls());
    }

    @Test
    void get_withNonExistingScientificName_shouldThrowDiseaseCandidateNotFoundException() {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        PlantDiseaseJdbcAdapter plantDiseaseJdbcAdapter = new PlantDiseaseJdbcAdapter(new SqlReader(), namedParameterJdbcTemplate);

        String nonExistingScientificName = "NonExistingScientificName";

        when(namedParameterJdbcTemplate.queryForObject((String) any(String.class), (SqlParameterSource) any(), any(BeanPropertyRowMapper.class)))
                .thenThrow(EmptyResultDataAccessException.class);


        assertThrows(DiseaseCandidateNotFoundException.class, () -> plantDiseaseJdbcAdapter.get(nonExistingScientificName));
    }
}
