package ar.germin.api.application.adapter.jdbc;

import ar.germin.api.adapter.jdbc.FileImageJdbcAdapter;
import ar.germin.api.adapter.jdbc.models.FileImageModel;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.exceptions.FileImageAlreadyExistsException;
import ar.germin.api.application.exceptions.FileImageNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileImageJdbcAdapterTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectMocks
    private FileImageJdbcAdapter fileImageJdbcAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        FileImage fileImage = FileImage.builder()
                .id(UUID.randomUUID().toString())
                .filePath("/path/to/file")
                .isPublic(true)
                .checksum("checksum_value")
                .build();

        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class))).thenReturn(1);

        FileImage savedFileImage = fileImageJdbcAdapter.save(fileImage);

        assertEquals(fileImage, savedFileImage);
        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class));
    }

    @Test
    void testSave_DuplicateKeyException() {
        FileImage fileImage = FileImage.builder()
                .id(UUID.randomUUID().toString())
                .filePath("/path/to/file")
                .isPublic(true)
                .checksum("checksum_value")
                .build();

        doThrow(DuplicateKeyException.class).when(namedParameterJdbcTemplate).update(anyString(), any(MapSqlParameterSource.class));

        assertThrows(FileImageAlreadyExistsException.class, () -> fileImageJdbcAdapter.save(fileImage));
        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class));
    }

    @Test
    void testSaveHistory() {
        FileImage fileImage = FileImage.builder()
                .id(UUID.randomUUID().toString())
                .filePath("/path/to/file")
                .isPublic(true)
                .checksum("checksum_value")
                .build();
        Integer idPlant = 1;

       when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class))).thenReturn(1);

        FileImage savedFileImage = fileImageJdbcAdapter.saveHistory(fileImage, idPlant);

        assertEquals(fileImage, savedFileImage);
        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class));
    }

    @Test
    void testGetById() {
        String id = UUID.randomUUID().toString();
        FileImageModel fileImageModel = new FileImageModel();
        fileImageModel.setId(id);
        fileImageModel.setFilePath("/path/to/file");
        fileImageModel.setIsPublic(true);

        when(namedParameterJdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class)))
                .thenReturn(fileImageModel);

        FileImage fileImage = fileImageJdbcAdapter.getById(id);

        assertNotNull(fileImage);
        assertEquals(id, fileImage.getId());
        assertEquals("/path/to/file", fileImage.getFilePath());
        assertTrue(fileImage.getIsPublic());

        verify(namedParameterJdbcTemplate, times(1)).queryForObject(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class));
    }

    @Test
    void testGetById_NotFound() {
        String id = UUID.randomUUID().toString();

        when(namedParameterJdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class)))
                .thenReturn(null);

        assertThrows(FileImageNotFoundException.class, () -> fileImageJdbcAdapter.getById(id));
        verify(namedParameterJdbcTemplate, times(1)).queryForObject(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class));
    }
}
