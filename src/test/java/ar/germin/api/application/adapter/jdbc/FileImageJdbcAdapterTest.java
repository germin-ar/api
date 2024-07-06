package ar.germin.api.application.adapter.jdbc;

import ar.germin.api.adapter.jdbc.FileImageJdbcAdapter;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.exceptions.FileImageAlreadyExistsException;
import ar.germin.api.application.exceptions.FileImageNotFoundException;
import ar.germin.api.adapter.jdbc.models.FileImageModel;
import org.junit.jupiter.api.Test;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FileImageJdbcAdapterTest {

    @Test
    void save_withValidFileImage_shouldSaveSuccessfully() {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        FileImageJdbcAdapter fileImageJdbcAdapter = new FileImageJdbcAdapter(namedParameterJdbcTemplate);

        FileImage fileImage = FileImage.builder()
                .id("1")
                .filePath("/path/to/image")
                .isPublic(true)
                .build();

        when(namedParameterJdbcTemplate.update(any(String.class), any(MapSqlParameterSource.class)))
                .thenReturn(1);


        FileImage result = fileImageJdbcAdapter.save(fileImage);


        assertEquals(fileImage, result);
        verify(namedParameterJdbcTemplate, times(1)).update(any(String.class), any(MapSqlParameterSource.class));
    }

    @Test
    void save_withDuplicateKey_shouldThrowFileImageAlreadyExistsException() {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        FileImageJdbcAdapter fileImageJdbcAdapter = new FileImageJdbcAdapter(namedParameterJdbcTemplate);

        FileImage fileImage = FileImage.builder()
                .id("1")
                .filePath("/path/to/image")
                .isPublic(true)
                .build();

        when(namedParameterJdbcTemplate.update(any(String.class), any(MapSqlParameterSource.class)))
                .thenThrow(DuplicateKeyException.class);


        assertThrows(FileImageAlreadyExistsException.class, () -> fileImageJdbcAdapter.save(fileImage));
        verify(namedParameterJdbcTemplate, times(1)).update(any(String.class), any(MapSqlParameterSource.class));
    }

    @Test
    void getById_withValidId_shouldReturnFileImage() {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        FileImageJdbcAdapter fileImageJdbcAdapter = new FileImageJdbcAdapter(namedParameterJdbcTemplate);

        String id = "1";
        FileImageModel fileImageModel = new FileImageModel();
        fileImageModel.setId(id);
        fileImageModel.setFilePath("/path/to/image");
        fileImageModel.setIsPublic(true);

        when(namedParameterJdbcTemplate.queryForObject(any(String.class), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class)))
                .thenReturn(fileImageModel);


        FileImage result = fileImageJdbcAdapter.getById(id);


        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("/path/to/image", result.getFilePath());
        assertTrue(result.getIsPublic());
        verify(namedParameterJdbcTemplate, times(1)).queryForObject(any(String.class), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class));
    }

    @Test
    void getById_withInvalidId_shouldThrowFileImageNotFoundException() {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        FileImageJdbcAdapter fileImageJdbcAdapter = new FileImageJdbcAdapter(namedParameterJdbcTemplate);

        String id = "1";

        when(namedParameterJdbcTemplate.queryForObject(any(String.class), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class)))
                .thenReturn(null);


        assertThrows(FileImageNotFoundException.class, () -> fileImageJdbcAdapter.getById(id));
        verify(namedParameterJdbcTemplate, times(1)).queryForObject(any(String.class), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class));
    }

}
