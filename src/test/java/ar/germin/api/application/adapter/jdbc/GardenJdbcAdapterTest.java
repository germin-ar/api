package ar.germin.api.application.adapter.jdbc;

import ar.germin.api.adapter.jdbc.GardenJdbcAdapter;
import ar.germin.api.adapter.jdbc.SqlReader;
import ar.germin.api.adapter.jdbc.models.GardenModel;
import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.port.out.GetGardenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GardenJdbcAdapterTest {
    private static final SqlReader sqlReader = mock(SqlReader.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);

    @BeforeAll
    static void setSqlReader() {
        when(sqlReader.readSql(anyString())).thenReturn("");
    }

    @Test
    void testPrueba() {
        GardenModel gardenModel1 = GardenModel.builder()
                .build();

        GardenModel gardenModel2 = GardenModel.builder()
                .build();

        when(namedParameterJdbcTemplate.query(
                anyString(),
                any(MapSqlParameterSource.class),
                ArgumentMatchers.<RowMapper<GardenModel>>any()))
                .thenReturn(List.of(gardenModel1, gardenModel2));

        GetGardenRepository getGardenRepository = new GardenJdbcAdapter(sqlReader, namedParameterJdbcTemplate);

        List<Garden> gardens = getGardenRepository.getByUserId(1);

        Assertions.assertEquals(2, gardens.size());
    }
}
