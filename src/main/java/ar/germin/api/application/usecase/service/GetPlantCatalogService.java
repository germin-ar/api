package ar.germin.api.application.usecase.service;

import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.domain.Specie;
import ar.germin.api.application.exceptions.PlantCatalogNotFoundException;
import ar.germin.api.application.port.out.GetPlantCatalogRepository;
import ar.germin.api.application.port.out.GetPlantDetailDataRepository;
import ar.germin.api.application.port.out.SavePlantCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetPlantCatalogService {

    private final GetPlantCatalogRepository getPlantCatalogRepository;
    private final GetPlantDetailDataRepository getPlantDetailDataRepository;
    private final SavePlantCatalogRepository savePlantCatalogRepository;

    @Autowired
    public GetPlantCatalogService(GetPlantCatalogRepository getPlantCatalogRepository,
                                  GetPlantDetailDataRepository getPlantDetailDataRepository,
                                  SavePlantCatalogRepository savePlantCatalogRepository) {
        this.getPlantCatalogRepository = getPlantCatalogRepository;
        this.getPlantDetailDataRepository = getPlantDetailDataRepository;
        this.savePlantCatalogRepository = savePlantCatalogRepository;
    }

    //todo logica BDD
    public PlantCatalog getPlantCatalog(String scientificName) {
        try {
            return this.getPlantCatalogRepository.getPlantCatalog(scientificName);
        } catch (PlantCatalogNotFoundException ex) {
            PlantCatalog plantCatalog = this.getPlantDetailDataRepository.searchDetail(scientificName);
            this.savePlantCatalogRepository.save(plantCatalog);
            return plantCatalog;
        }
    }
}
