package ar.germin.api.application.adapter.jdbc;

import ar.germin.api.adapter.jdbc.PlantPhotoJdbcAdapter;
import ar.germin.api.adapter.jdbc.SqlReader;
import ar.germin.api.adapter.jdbc.models.PlantPhotoModel;
import ar.germin.api.application.domain.PlantPhoto;
import ar.germin.api.application.exceptions.ErrorPlantSaveException;
import ar.germin.api.application.port.out.GetPlantPhotosRepository;
import ar.germin.api.application.port.out.SavePlantPhotoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PlantPhotoJdbcAdapterTest {

    private PlantPhotoJdbcAdapter createAdapter(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        // Mock the SqlReader to return a dummy SQL string
        SqlReader sqlReader = mock(SqlReader.class);
        when(sqlReader.readSql(anyString())).thenReturn("SQL QUERY");

        // Return a new instance of PlantPhotoJdbcAdapter with the mocked dependencies
        return new PlantPhotoJdbcAdapter(sqlReader, namedParameterJdbcTemplate);
    }

    @Test
    void save_withValidParams_shouldReturnGeneratedId() {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        PlantPhotoJdbcAdapter adapter = createAdapter(namedParameterJdbcTemplate);

        SavePlantPhotoRepository.Params params = SavePlantPhotoRepository.Params.builder()
                .idPlant(1)
                .url("http://example.com/photo.jpg")
                .build();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        doAnswer(invocation -> {
            ((KeyHolder) invocation.getArgument(2)).getKeyList().add(Collections.singletonMap("id", 1));
            return 1;
        }).when(namedParameterJdbcTemplate).update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class), any(String[].class));

        Integer generatedId = adapter.save(params);

        assertNotNull(generatedId);
        assertTrue(generatedId > 0);
    }


    @Test
    void getByPlantId_withValidId_shouldReturnPlantPhotos() {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = Mockito.mock(NamedParameterJdbcTemplate.class);
        PlantPhotoJdbcAdapter adapter = createAdapter(namedParameterJdbcTemplate);

        PlantPhotoModel plantPhotoModel = new PlantPhotoModel();
        plantPhotoModel.setId(1);
        plantPhotoModel.setIdPlant(1);
        plantPhotoModel.setUrl("http://example.com/photo.jpg");
        plantPhotoModel.setUploadedAt(LocalDateTime.now());  // Simulate uploaded timestamp

        Mockito.when(namedParameterJdbcTemplate.query(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class), Mockito.any(BeanPropertyRowMapper.class)))
                .thenReturn(Collections.singletonList(plantPhotoModel));

        List<PlantPhoto> plantPhotos = adapter.getByPlantId(1);

        assertNotNull(plantPhotos);
        assertFalse(plantPhotos.isEmpty());
        assertEquals(1, plantPhotos.size());
        assertEquals("http://example.com/photo.jpg", plantPhotos.get(0).getUrl());
        assertEquals(1, plantPhotos.get(0).getId());  // Assuming getId() maps to PlantPhoto's id
        assertEquals(1, plantPhotos.get(0).getIdPlant());  // Assuming getIdPlant() maps to PlantPhoto's idPlant
        assertNotNull(plantPhotos.get(0).getUploadedAt());  // Assuming uploadedAt is not null in PlantPhoto
    }


}

