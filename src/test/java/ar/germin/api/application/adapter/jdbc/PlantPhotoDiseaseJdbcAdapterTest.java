package ar.germin.api.application.adapter.jdbc;

import ar.germin.api.adapter.jdbc.PlantPhotoDiseaseJdbcAdapter;
import ar.germin.api.adapter.jdbc.SqlReader;
import ar.germin.api.application.domain.DiseaseCandidate;
import ar.germin.api.application.domain.PlantPhoto;
import ar.germin.api.application.exceptions.PlantPhotoDiseaseAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Collections;

public class PlantPhotoDiseaseJdbcAdapterTest {

    @Test
    public void testSave_Success() {
        // Arrange
        SqlReader sqlReader = Mockito.mock(SqlReader.class);
        NamedParameterJdbcTemplate jdbcTemplate = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(sqlReader.readSql(Mockito.anyString())).thenReturn("INSERT INTO plant_photo_disease ...");
        Mockito.doAnswer(invocation -> {
            KeyHolder keyHolder = invocation.getArgument(2);
            keyHolder.getKeyList().add(Collections.singletonMap("id", 1));
            return 1;
        }).when(jdbcTemplate).update(Mockito.anyString(), Mockito.any(), Mockito.any(KeyHolder.class), Mockito.any());

        PlantPhotoDiseaseJdbcAdapter adapter = new PlantPhotoDiseaseJdbcAdapter(sqlReader, jdbcTemplate);

        DiseaseCandidate diseaseCandidate = DiseaseCandidate.builder()
                .id(1)
                .build();


        PlantPhoto plantPhoto = PlantPhoto.builder()
                .id(1)
                .idPlant(1)
                .build();

        // Act
        Integer result = adapter.save(diseaseCandidate, plantPhoto);

        // Assert
        Assertions.assertEquals(1, result);
    }

    @Test
    public void testSave_DuplicateKeyException() {
        // Arrange
        SqlReader sqlReader = Mockito.mock(SqlReader.class);
        NamedParameterJdbcTemplate jdbcTemplate = Mockito.mock(NamedParameterJdbcTemplate.class);
        Mockito.when(sqlReader.readSql(Mockito.anyString())).thenReturn("INSERT INTO plant_photo_disease ...");
        Mockito.doThrow(new DuplicateKeyException("Duplicate key")).when(jdbcTemplate).update(Mockito.anyString(), Mockito.any(), Mockito.any(KeyHolder.class), Mockito.any());

        PlantPhotoDiseaseJdbcAdapter adapter = new PlantPhotoDiseaseJdbcAdapter(sqlReader, jdbcTemplate);

        DiseaseCandidate diseaseCandidate = DiseaseCandidate.builder()
                .id(1)
                .build();


        PlantPhoto plantPhoto = PlantPhoto.builder()
                .id(1)
                .idPlant(1)
                .build();

        // Act & Assert
        Assertions.assertThrows(PlantPhotoDiseaseAlreadyExistException.class, () -> {
            adapter.save(diseaseCandidate, plantPhoto);
        });
    }

}
