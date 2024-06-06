package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.PlantRequestModel;

import ar.germin.api.application.port.in.DeletePlantPortIn;
import ar.germin.api.application.port.in.SavePlantPortIn;
import ar.germin.api.application.port.in.UpdatePlantPortIn;
import ar.germin.api.application.port.out.UpdatePlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/plants")
public class PlantControllerAdapter {
    private final SavePlantPortIn savePlantPortIn;
    private final DeletePlantPortIn deletePlantPortIn;
    private final UpdatePlantPortIn updatePlantPortIn;

    @Autowired
    public PlantControllerAdapter(SavePlantPortIn savePlantPortIn,DeletePlantPortIn deletePlantPortIn, UpdatePlantPortIn updatePlantPortIn) {
        this.savePlantPortIn = savePlantPortIn;
        this.deletePlantPortIn = deletePlantPortIn;
        this.updatePlantPortIn = updatePlantPortIn;
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
    @DeleteMapping("/{id}")
    public void deletePlant(@PathVariable ("id")Integer id){
        this.deletePlantPortIn.delete(id);
    }

    @PutMapping("/update")
    public Integer updatePlant(@RequestBody UpdatePlantRepository.Params params){
        return this.updatePlantPortIn.update(params);
            }
}
