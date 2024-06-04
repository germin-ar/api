package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.PlantRequestModel;
import ar.germin.api.application.port.in.SavePlantPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/plants")
public class PlantControllerAdapter {
    private final SavePlantPortIn savePlantPortIn;

    @Autowired
    public PlantControllerAdapter(SavePlantPortIn savePlantPortIn) {
        this.savePlantPortIn = savePlantPortIn;
    }

    @PostMapping
    public void createPlant(@RequestBody PlantRequestModel plantRequestModel) {
        this.savePlantPortIn.save(SavePlantPortIn.Params.builder()
                .alias(plantRequestModel.alias())
                .plantingDate(plantRequestModel.plantingDate())
                .height(plantRequestModel.height())
                .notes(plantRequestModel.notes())
                .idGarden(plantRequestModel.idGarden())
                .build());
    }
}
