package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.port.in.GetPlantCatalogPortIn;
import ar.germin.api.application.port.out.GetPlantCatalogRepository;
import ar.germin.api.application.usecase.service.GetPlantCatalogService;
import org.springframework.stereotype.Component;

@Component
public class GetPlantCatalogUseCase implements GetPlantCatalogPortIn {
    private final GetPlantCatalogService getPlantCatalogService;


    public GetPlantCatalogUseCase(GetPlantCatalogService getPlantCatalogService) {
        this.getPlantCatalogService = getPlantCatalogService;
    }
    //nuevo endpoint
    @Override
    public PlantCatalog getPlantCatalog(String scientificName) {
        return this.getPlantCatalogService.getPlantCatalog(scientificName);
    }
}
