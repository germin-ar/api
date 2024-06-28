package ar.germin.api.application.adapter.jdbc;

import ar.germin.api.adapter.jdbc.AIDetectionJdbcAdapter;
import ar.germin.api.adapter.jdbc.SqlReader;
import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.FileImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;


class AIDetectionJdbcAdapterTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private SqlReader sqlReader;

    @InjectMocks
    private AIDetectionJdbcAdapter aiDetectionJdbcAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByFileImage() {
        // Arrange
        FileImage fileImage = FileImage.builder()
                .id("1")
                .filePath("/path/to/file")
                .isPublic(true)
                .checksum("checksum")
                .build();


        AIDetection actualDetection = aiDetectionJdbcAdapter.getByFileImage(fileImage);


        assertNull(actualDetection);

    }

    @Test
    void save() {

        AIDetection aiDetection = AIDetection.builder()
                .id("1")
                .language("EN")
                .fileImage(FileImage.builder()
                        .id("1")
                        .filePath("/path/to/file")
                        .isPublic(true)
                        .checksum("checksum")
                        .build())
                .build();




        Integer actualId = aiDetectionJdbcAdapter.save(aiDetection);


        assertEquals(0, actualId);

    }
}
