package ar.germin.api.application.adapter.jdbc;

import ar.germin.api.adapter.jdbc.PlantCatalogJdbcAdapter;
import ar.germin.api.adapter.jdbc.SqlReader;
import ar.germin.api.adapter.jdbc.models.PlantCatalogModelAlternative;
import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.exceptions.PlantCatalogNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class PlantCatalogJdbcAdapterTest {

    @Test
    void getPlantCatalog_withExistingScientificName_shouldReturnPlantCatalog() {
        // Arrange
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        PlantCatalogJdbcAdapter plantCatalogJdbcAdapter = new PlantCatalogJdbcAdapter(new SqlReader(), namedParameterJdbcTemplate);

        String scientificName = "testScientificName";
        PlantCatalogModelAlternative plantCatalogModel = new PlantCatalogModelAlternative();
        plantCatalogModel.setScientificName(scientificName);

        when(namedParameterJdbcTemplate.queryForObject((String) any(String.class), (SqlParameterSource) any(), any(BeanPropertyRowMapper.class)))
                .thenReturn(plantCatalogModel);

        // Act
        PlantCatalog result = plantCatalogJdbcAdapter.getPlantCatalog(scientificName);

        // Assert
        assertEquals(scientificName, result.getScientificName());
    }

    @Test
    void getPlantCatalog_withNonExistingScientificName_shouldThrowPlantCatalogNotFoundException() {
        // Arrange
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        PlantCatalogJdbcAdapter plantCatalogJdbcAdapter = new PlantCatalogJdbcAdapter(new SqlReader(), namedParameterJdbcTemplate);

        String scientificName = "nonExistingScientificName";

        when(namedParameterJdbcTemplate.queryForObject((String) any(String.class), (SqlParameterSource) any(), any(BeanPropertyRowMapper.class)))
                .thenThrow(EmptyResultDataAccessException.class);

        // Act & Assert
        assertThrows(PlantCatalogNotFoundException.class, () -> plantCatalogJdbcAdapter.getPlantCatalog(scientificName));
    }

    @Test
    void getPlantsCatalogs_withValidParameters_shouldReturnListOfPlantCatalogs() {
        // Arrange
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        PlantCatalogJdbcAdapter plantCatalogJdbcAdapter = new PlantCatalogJdbcAdapter(new SqlReader(), namedParameterJdbcTemplate);

        Double minTemperature = 10.0;
        Double maxTemperature = 20.0;
        String luz = "sunlight";
        String temporada = "summer";
        Integer squareCentimeters = 100;

        List<PlantCatalogModelAlternative> plantCatalogModels = Collections.singletonList(new PlantCatalogModelAlternative());
        when(namedParameterJdbcTemplate.query((String) any(String.class), (SqlParameterSource) any(), any(BeanPropertyRowMapper.class)))
                .thenReturn(plantCatalogModels);

        // Act
        List<PlantCatalog> result = plantCatalogJdbcAdapter.getPlantsCatalogs(minTemperature, maxTemperature, luz, temporada, squareCentimeters);

        // Assert
        assertEquals(plantCatalogModels.size(), result.size());
    }

    @Test
    void getPlantsCatalogsPlace_withValidParameters_shouldReturnListOfPlantCatalogs() {
        // Arrange
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        PlantCatalogJdbcAdapter plantCatalogJdbcAdapter = new PlantCatalogJdbcAdapter(new SqlReader(), namedParameterJdbcTemplate);

        String light = "sunlight";
        Integer place = 1;
        String temporada = "summer";

        List<PlantCatalogModelAlternative> plantCatalogModels = Collections.singletonList(new PlantCatalogModelAlternative());
        when(namedParameterJdbcTemplate.query((String) any(String.class), (SqlParameterSource) any(), any(BeanPropertyRowMapper.class)))
                .thenReturn(plantCatalogModels);

        // Act
        List<PlantCatalog> result = plantCatalogJdbcAdapter.getPlantsCatalogsPlace(light, place, temporada);

        // Assert
        assertEquals(plantCatalogModels.size(), result.size());
    }
}
