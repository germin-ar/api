package ar.germin.api.application.adapter.jdbc;

import ar.germin.api.adapter.jdbc.SqlReader;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SqlReaderTest {

    @Test
    public void testReadSql_success() {
        SqlReader sqlReader = new SqlReader() {
            @Override
            public String readSql(final String sqlPath) {
                String sqlContent = "SELECT * FROM users;";
                try (Reader reader = new StringReader(sqlContent)) {
                    StringBuilder textBuilder = new StringBuilder();
                    char[] buffer = new char[1024];
                    int numCharsRead;
                    while ((numCharsRead = reader.read(buffer, 0, buffer.length)) != -1) {
                        textBuilder.append(buffer, 0, numCharsRead);
                    }
                    return textBuilder.toString();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        String sqlPath = "classpath:sql/test.sql";
        String expectedContent = "SELECT * FROM users;";

        String actualContent = sqlReader.readSql(sqlPath);

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void testReadSql_fileNotFound() {
        SqlReader sqlReader = new SqlReader();
        String sqlPath = "classpath:sql/non_existent.sql";

        assertThrows(RuntimeException.class, () -> {
            sqlReader.readSql(sqlPath);
        });
    }
}
