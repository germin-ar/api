package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.GetPlantCatalogResponseModel;
import ar.germin.api.adapter.controller.models.GetPlantSuggestionResponseModel;
import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.domain.PlantDataSuggestion;
import ar.germin.api.application.port.in.GetPlantCatalogPortIn;
import ar.germin.api.application.port.in.GetPlantsSuggestionPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plant-suggestion")
public class PlantSuggestionControllerAdapter {

    private final GetPlantsSuggestionPortIn getPlantsSuggestionPortIn;
    private final GetPlantCatalogPortIn getPlantCatalogPortIn;

    @Autowired
    public PlantSuggestionControllerAdapter(GetPlantsSuggestionPortIn getPlantsSuggestionPortIn,
                                            GetPlantCatalogPortIn getPlantCatalogPortIn) {
        this.getPlantsSuggestionPortIn = getPlantsSuggestionPortIn;
        this.getPlantCatalogPortIn = getPlantCatalogPortIn;
    }


    @GetMapping
    public List<GetPlantCatalogResponseModel> getPlantsSuggestion(@Param("latitude") Float latitude,
                                                                  @Param("longitude") Float longitude,
                                                                  @Param("sunExposure") Integer sunExposure,
                                                                  @Param("squareCentimeters") Integer squareCentimeters) {

        return GetPlantCatalogResponseModel.fromDomainList(this.getPlantCatalogPortIn.getPlantsCatalog(latitude, longitude, sunExposure, squareCentimeters));
    }

    @GetMapping("/espacio")
    public List<GetPlantCatalogResponseModel> getPLantsSuggestionPlace(@Param("luz") String luz,
                                                                       @Param("temporada") String temporada,
                                                                       @Param("espacio") String espacio){
        return GetPlantCatalogResponseModel.fromDomainList(this.getPlantCatalogPortIn.getPlantsCatalogPlace(luz, temporada, espacio));
    }
}
