package ar.germin.api.application.adapter.jdbc;




import ar.germin.api.adapter.jdbc.PlantHistoryJdbcAdapter;
import ar.germin.api.adapter.jdbc.SqlReader;
import ar.germin.api.application.port.out.SavePlantHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.HashMap;

import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class PlantHistoryJdbcAdapterTest {


    @Test
    void save_withValidParams_shouldReturnGeneratedId() {
        // Arrange
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        PlantHistoryJdbcAdapter plantHistoryJdbcAdapter = new PlantHistoryJdbcAdapter(new SqlReader(), namedParameterJdbcTemplate);

        SavePlantHistoryRepository.Params params = SavePlantHistoryRepository.Params.builder()
                .alias("Alias")
                .idPlant(123)
                .notes("Notes")
                .height(10.5)
                .urlImage("http://example.com/image.jpg")
                .idDiseases(456)
                .build();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("id", 1); // Simular que se generó el ID 1
        keyHolder.getKeyList().add(keyMap);

        when(namedParameterJdbcTemplate.update(any(String.class), any(SqlParameterSource.class), any(KeyHolder.class), any(String[].class)))
                .thenAnswer(invocation -> {
                    KeyHolder kh = invocation.getArgument(2);
                    kh.getKeyList().add(keyMap);
                    return 1; // Devolver el número de filas afectadas
                });

        // Act
        Integer generatedId = plantHistoryJdbcAdapter.save(params);

        // Assert
        assertEquals(1, generatedId.intValue(), "Expected generated ID should be 1");
    }



    static class TestDataSource {
        static javax.sql.DataSource getDataSource() {
            // Return your test DataSource configuration
            return null;  // Replace with your test DataSource setup
        }
    }


}
