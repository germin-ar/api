package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.port.in.GetPlantCatalogPortIn;
import ar.germin.api.application.port.out.GetPlantCatalogRepository;
import org.springframework.stereotype.Component;

@Component
public class GetPlantCatalogUseCase implements GetPlantCatalogPortIn {
    private final GetPlantCatalogRepository getPlantCatalogRepository;


    public GetPlantCatalogUseCase(GetPlantCatalogRepository getPlantCatalogRepository) {
        this.getPlantCatalogRepository = getPlantCatalogRepository;
    }

    @Override
    public PlantCatalog getPlant(String scientificName) {
        return this.getPlantCatalogRepository.getPlant(scientificName);
    }
}
