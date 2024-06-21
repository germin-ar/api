package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.GetPlantCatalogResponseModel;
import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.port.in.GetPlantCatalogPortIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/plant-catalog")
public class PlantCatalogControllerAdapter {
    private final GetPlantCatalogPortIn getPlantCatalogPortIn;

    @Autowired
    public PlantCatalogControllerAdapter(GetPlantCatalogPortIn getPlantCatalogPortIn) {
        this.getPlantCatalogPortIn = getPlantCatalogPortIn;
    }


    @GetMapping("/{scientific-name}")
    public GetPlantCatalogResponseModel getPlantCatalog(@PathVariable("scientific-name") String scientificName){
        PlantCatalog plantCatalog = this.getPlantCatalogPortIn.getPlantCatalog(scientificName);
        return GetPlantCatalogResponseModel.fromDomain(plantCatalog);
    }
}
