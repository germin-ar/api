package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.GetPlantSuggestionResponseModel;
import ar.germin.api.application.domain.PlantDataSuggestion;
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

    @Autowired
    public PlantSuggestionControllerAdapter(GetPlantsSuggestionPortIn getPlantsSuggestionPortIn) {
        this.getPlantsSuggestionPortIn = getPlantsSuggestionPortIn;
    }

    @GetMapping
    public List<GetPlantSuggestionResponseModel> getPlantsSuggestion(@Param("latitude") Float latitude,
                                                                     @Param("longitude") Float longitude,
                                                                     @Param("sun_exposure") Integer sunExposure,
                                                                     @Param("square_meters") Integer squareMeters,
                                                                     @Param("page") Integer page) {
        List<PlantDataSuggestion> response = this.getPlantsSuggestionPortIn.getPlantsSuggestions(GetPlantsSuggestionPortIn.Query.builder()
                .page(page)
                .squareMeters(squareMeters)
                .latitude(latitude)
                .longitude(longitude)
                .sunExposure(sunExposure)
                .build());

        return GetPlantSuggestionResponseModel.fromDomain(response);
    }
}
