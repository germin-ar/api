package ar.germin.api.application.adapter.jdbc;

import ar.germin.api.adapter.jdbc.AIDetectionJdbcAdapter;
import ar.germin.api.adapter.jdbc.SqlReader;
import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.FileImage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AIDetectionJdbcAdapterTest {

    @Test
    void getByFileImage() {

        DataSource dataSource = Mockito.mock(DataSource.class);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        SqlReader sqlReader = Mockito.mock(SqlReader.class);
        AIDetectionJdbcAdapter aiDetectionJdbcAdapter = new AIDetectionJdbcAdapter(sqlReader, namedParameterJdbcTemplate);

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

        DataSource dataSource = Mockito.mock(DataSource.class);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        SqlReader sqlReader = Mockito.mock(SqlReader.class);
        AIDetectionJdbcAdapter aiDetectionJdbcAdapter = new AIDetectionJdbcAdapter(sqlReader, namedParameterJdbcTemplate);

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
