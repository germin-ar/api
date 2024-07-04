package ar.germin.api.application.adapter.jdbc;

import ar.germin.api.adapter.jdbc.GardenJdbcAdapter;
import ar.germin.api.adapter.jdbc.SqlReader;
import ar.germin.api.adapter.jdbc.models.GardenModel;
import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.exceptions.GardenNotFoundException;

import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class GardenJdbcAdapterTest {


    @Test
    void getById_withValidId_shouldReturnGarden() {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        SqlReader sqlReader = mock(SqlReader.class);
        when(sqlReader.readSql(anyString())).thenReturn("SELECT * FROM garden WHERE id = :id");

        GardenJdbcAdapter gardenJdbcAdapter = new GardenJdbcAdapter(sqlReader, namedParameterJdbcTemplate);

        Integer id = 1;
        GardenModel gardenModel = GardenModel.builder()
                .id(id)
                .name("Test Garden")
                .idUser(123)
                .build();

        // Mock para simular que se encontró el jardín por ID
        when(namedParameterJdbcTemplate.queryForObject(
                eq("SELECT * FROM garden WHERE id = :id"),
                any(MapSqlParameterSource.class),
                any(BeanPropertyRowMapper.class)
        )).thenReturn(gardenModel);


        Garden result = gardenJdbcAdapter.getById(id);


        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Test Garden", result.getName());
        assertEquals(123, result.getIdUser());


        ArgumentCaptor<MapSqlParameterSource> paramsCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(namedParameterJdbcTemplate, times(1)).queryForObject(
                eq("SELECT * FROM garden WHERE id = :id"),
                paramsCaptor.capture(),
                any(BeanPropertyRowMapper.class)
        );

        assertEquals(id, paramsCaptor.getValue().getValue("id"));
    }


    @Test
    void getById_withInvalidId_shouldThrowNotFoundException() {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        SqlReader sqlReader = mock(SqlReader.class);
        when(sqlReader.readSql(anyString())).thenReturn("SELECT * FROM garden WHERE id = :id");

        GardenJdbcAdapter gardenJdbcAdapter = new GardenJdbcAdapter(sqlReader, namedParameterJdbcTemplate);

        Integer id = 1;

        // Mock para simular que no se encontró el jardín por ID
        when(namedParameterJdbcTemplate.queryForObject(
                eq("SELECT * FROM garden WHERE id = :id"),
                any(MapSqlParameterSource.class),
                any(BeanPropertyRowMapper.class)
        )).thenThrow(new GardenNotFoundException());


        assertThrows(GardenNotFoundException.class, () -> {
            gardenJdbcAdapter.getById(id);
        });

        // Verificar que se llamó a queryForObject con los parámetros correctos
        ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MapSqlParameterSource> paramsCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        ArgumentCaptor<BeanPropertyRowMapper> mapperCaptor = ArgumentCaptor.forClass(BeanPropertyRowMapper.class);
        verify(namedParameterJdbcTemplate, times(1)).queryForObject(
                queryCaptor.capture(),
                paramsCaptor.capture(),
                mapperCaptor.capture()
        );

        assertEquals("SELECT * FROM garden WHERE id = :id", queryCaptor.getValue());
        assertTrue(paramsCaptor.getValue().getValues().containsKey("id"));
        assertEquals(id, paramsCaptor.getValue().getValue("id"));
        assertNotNull(mapperCaptor.getValue());
    }

    @Test
    void getByUserId_withValidUserId_shouldReturnListOfGardens() {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        SqlReader sqlReader = mock(SqlReader.class);
        when(sqlReader.readSql(any(String.class))).thenReturn("SELECT * FROM gardens WHERE idUser = :idUser");
        GardenJdbcAdapter gardenJdbcAdapter = new GardenJdbcAdapter(sqlReader, namedParameterJdbcTemplate);

        Integer userId = 1;
        GardenModel gardenModel = new GardenModel();
        gardenModel.setId(1);
        gardenModel.setName("Test Garden");

        List<GardenModel> gardenModels = Collections.singletonList(gardenModel);

        when(namedParameterJdbcTemplate.query(any(String.class), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class)))
                .thenReturn(gardenModels);


        List<Garden> result = gardenJdbcAdapter.getByUserId(userId);


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Test Garden", result.get(0).getName());
        verify(namedParameterJdbcTemplate, times(1))
                .query(any(String.class), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class));
    }



    @Test
    void save_withValidUserIdAndName_shouldReturnGeneratedId() {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        SqlReader sqlReader = mock(SqlReader.class);
        when(sqlReader.readSql(any(String.class))).thenReturn("INSERT INTO gardens (idUser, name) VALUES (:idUser, :name)");
        GardenJdbcAdapter gardenJdbcAdapter = new GardenJdbcAdapter(sqlReader, namedParameterJdbcTemplate);

        Integer userId = 1;
        String name = "Test Garden";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("idUser", userId)
                .addValue("name", name);

        when(namedParameterJdbcTemplate.update(any(String.class), any(MapSqlParameterSource.class), any(KeyHolder.class), any(String[].class)))
                .thenAnswer(invocation -> {
                    KeyHolder kh = invocation.getArgument(2);
                    kh.getKeyList().add(Collections.singletonMap("id", 1)); // Simulate the generated key
                    return 1;
                });


        Integer result = gardenJdbcAdapter.save(userId, name);


        assertEquals(1, result);
        verify(namedParameterJdbcTemplate, times(1)).update(any(String.class), any(MapSqlParameterSource.class), any(KeyHolder.class), any(String[].class));
    }


}