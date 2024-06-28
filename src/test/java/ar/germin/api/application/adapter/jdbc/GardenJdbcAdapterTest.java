package ar.germin.api.application.adapter.jdbc;

import ar.germin.api.adapter.jdbc.GardenJdbcAdapter;
import ar.germin.api.adapter.jdbc.SqlReader;
import ar.germin.api.adapter.jdbc.models.GardenModel;
import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.exceptions.GardenNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GardenJdbcAdapterTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private SqlReader sqlReader;

    @Mock
    private GeneratedKeyHolder keyHolder;

    @InjectMocks
    private GardenJdbcAdapter gardenJdbcAdapter;

    @BeforeEach
    void setUp() {
        // Inicialización de los mocks
        MockitoAnnotations.initMocks(this);

        // Configuración de los SQLs y el adaptador JDBC
        when(sqlReader.readSql(anyString())).thenReturn("dummy sql");

        // Configuración del adaptador con el jdbcTemplate mockeado
        gardenJdbcAdapter = new GardenJdbcAdapter(sqlReader, jdbcTemplate);
    }

    @Test
    void testGetById_WhenFound() {
        // Configuración del mock para cuando se encuentra un jardín
        GardenModel gardenModel = new GardenModel(1, "Test Garden", 1);
        when(jdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class)))
                .thenReturn(gardenModel);

        // Lógica de la prueba
        Garden garden = gardenJdbcAdapter.getById(1);

        // Verificación de resultados
        assertEquals(garden.getId(), gardenModel.getId());
        assertEquals(garden.getName(), gardenModel.getName());
        assertEquals(garden.getIdUser(), gardenModel.getIdUser());
    }


    @Test
    public void testGetById_WhenNotFound() {
        // Configuración del mock para simular EmptyResultDataAccessException
        when(jdbcTemplate.queryForObject(any(String.class), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class)))
                .thenThrow(EmptyResultDataAccessException.class);

        // Verificar que se lanza GardenNotFoundException cuando no se encuentra el jardín
        assertThrows(GardenNotFoundException.class, () -> gardenJdbcAdapter.getById(1));
    }



    @Test
    public void testGetByUserId_WhenFound() {
        // Mock SQL query result
        GardenModel gardenModel = new GardenModel();
        gardenModel.setId(1);
        gardenModel.setName("Test Garden");
        gardenModel.setIdUser(1);

        when(jdbcTemplate.query(any(String.class), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class)))
                .thenReturn(Collections.singletonList(gardenModel));

        List<Garden> gardens = gardenJdbcAdapter.getByUserId(1);

        assertEquals(1, gardens.size());
        assertEquals("Test Garden", gardens.get(0).getName());
    }

    @Test
    public void testGetByUserId_WhenNotFound() {
        // Mocking SQL query result when no gardens are found for given userId
        when(jdbcTemplate.query(any(String.class), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class)))
                .thenReturn(Collections.emptyList());

        assertThrows(GardenNotFoundException.class, () -> gardenJdbcAdapter.getByUserId(1));
    }


}
