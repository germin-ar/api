package ar.germin.api.application.adapter.jdbc;

import ar.germin.api.adapter.jdbc.CandidateJdbcAdapter;
import ar.germin.api.adapter.jdbc.SqlReader;
import ar.germin.api.application.domain.*;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class CandidateJdbcAdapterTest {

    @Test
    void save_withValidCandidate() {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        SqlReader sqlReader = mock(SqlReader.class);
        CandidateJdbcAdapter candidateJdbcAdapter = new CandidateJdbcAdapter(sqlReader, namedParameterJdbcTemplate);


        Genus genus = Genus.builder()
                .scientificName("genusName")
                .scientificNameWithoutAuthor("genusWithout")
                .scientificNameAuthorship("genusAuthor")
                .build();

        Family family = Family.builder()
                .scientificName("familyName")
                .scientificNameWithoutAuthor("familyWithout")
                .scientificNameAuthorship("familyAuthor")
                .build();


        Specie specie = Specie.builder()
                .scientificName("Test Scientific Name")
                .commonNames(List.of("Common Name 1", "Common Name 2"))
                .genus(genus) // Asignar el Genus
                .family(family)
                .build();


        Candidate candidate = Candidate.builder()
                .id("1")
                .score(0.8f)
                .selectedByUser(true)
                .specie(specie) // Inicializa el campo specie
                .build();


        Integer result = candidateJdbcAdapter.save(candidate);


        assertEquals(1, result); // Asumiendo que el método save siempre devuelve 1 (éxito)
    }

    @Test
    void getByFileImage_withValidFileImage() {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        SqlReader sqlReader = mock(SqlReader.class);
        CandidateJdbcAdapter candidateJdbcAdapter = new CandidateJdbcAdapter(sqlReader, namedParameterJdbcTemplate);

        FileImage fileImage = FileImage.builder()
                .id("1")
                .filePath("/path/to/file")
                .isPublic(true)
                .checksum("checksum")
                .build();


        List<Candidate> result = candidateJdbcAdapter.getByFileImage(fileImage);


        assertEquals(0, result.size()); // Asumiendo que siempre devuelve una lista vacía
    }
}
