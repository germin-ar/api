package ar.germin.api.application.controller;

import ar.germin.api.adapter.controller.CandidateControllerAdapter;

import ar.germin.api.adapter.controller.models.CandidateResponseModel;
import ar.germin.api.application.domain.*;
import ar.germin.api.application.port.in.GetCandidatesPlantsPortIn;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class CandidateControllerAdapterTest {


    @Test
    public void testGetCandidates() {

        DiseaseCandidate diseaseCandidate = DiseaseCandidate.builder()
                .id(1)
                .name("DiseaseName")
                .build();


        List<DiseaseCandidate> diseaseCandidates = Collections.singletonList(diseaseCandidate);


        HealthAIDetection healthAIDetection = HealthAIDetection.builder()
                .candidates(diseaseCandidates)
                .isHealthy(true)
                .build();


        Specie specie = Specie.builder()
                .scientificNameWithoutAuthor("CandidateName")
                .genus(Genus.builder().scientificNameWithoutAuthor("GenusName").build())
                .family(Family.builder().scientificNameWithoutAuthor("FamilyName").build())
                .commonNames(Collections.singletonList("CommonName"))
                .build();


        PlantCatalog plantCatalog = PlantCatalog.builder()
                .id(1)
                .fertilizer("Fertilizer")
                .wateringFrequency("Watering")
                .soil("Soil")
                .sunlight("Sunlight")
                .insecticide("Insecticide")
                .temperatureMax(30.0)
                .temperatureMin(10.0)
                .description("Description")
                .maxSize(1.5)
                .tips("Tips")
                .harvestTime("HarvestTime")
                .plantingTime("PlantingTime")
                .growthSeason("GrowthSeason")
                .pruning("Pruning")
                .build();


        Candidate candidate = Candidate.builder()
                .id("1")
                .specie(specie)
                .plantCatalog(plantCatalog)
                .score(100.0f)
                .build();


        FileImage fileImage = FileImage.builder()
                .id("file-uuid")
                .filePath("/path/to/image.jpg")
                .isPublic(true)
                .checksum("checksum")
                .build();


        AIDetection aiDetection = AIDetection.builder()
                .id("1")
                .language("English")
                .candidates(Collections.singletonList(candidate))
                .fileImage(fileImage)
                .healthAIDetection(healthAIDetection)
                .build();


        GetCandidatesPlantsPortIn getCandidatesPlantsPortIn = id -> aiDetection;
        CandidateControllerAdapter candidateControllerAdapter = new CandidateControllerAdapter(getCandidatesPlantsPortIn);


        CandidateResponseModel response = candidateControllerAdapter.getCandidates("1");


        assertEquals("1", response.getId());
        assertEquals("English", response.getLanguage());
        assertEquals(1, response.getCandidates().size());


    }
}
