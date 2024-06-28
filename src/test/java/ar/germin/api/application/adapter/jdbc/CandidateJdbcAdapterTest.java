package ar.germin.api.application.adapter.jdbc;

import ar.germin.api.adapter.jdbc.CandidateJdbcAdapter;
import ar.germin.api.adapter.jdbc.SqlReader;
import ar.germin.api.adapter.jdbc.models.CandidateModel;
import ar.germin.api.application.domain.*;
import ar.germin.api.application.port.out.GetCandidateRepository;
import ar.germin.api.application.port.out.SaveCandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CandidateJdbcAdapterTest {

    @Mock
     NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
     SqlReader sqlReader;

    @Mock
     CandidateModel candidateModelMock;

    @Mock
     SaveCandidateRepository saveCandidateRepositoryMock;

    @InjectMocks
     CandidateJdbcAdapter candidateJdbcAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_withNullValues() {
        setUp();

        Candidate candidate = Candidate.builder()
                .id("1")
                .score(0.8f)
                .specie(null)
                .selectedByUser(true)
                .plantCatalog(null)
                .build();

        // Configuramos el comportamiento del mock
        when(saveCandidateRepositoryMock.save(any(Candidate.class))).thenReturn(1);

        // Ejecutamos el método bajo prueba
        CandidateJdbcAdapter candidateJdbcAdapter = new CandidateJdbcAdapter(saveCandidateRepositoryMock,namedParameterJdbcTemplate);
        Integer result = candidateJdbcAdapter.save(candidate);

        // Afirmaciones y verificaciones
        assertEquals(1, result);
    }



    @Test
    void getByFileImage() {

        FileImage fileImage = FileImage.builder()
                .id("1")
                .filePath("/path/to/file")
                .isPublic(true)
                .checksum("checksum")
                .build();


        List<Candidate> result = candidateJdbcAdapter.getByFileImage(fileImage);


        assertTrue(result.isEmpty()); // Verifica que la lista devuelta esté vacía
    }
}
