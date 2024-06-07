package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.PlantRequestModel;
import ar.germin.api.adapter.controller.models.PlantResponseModel;
import ar.germin.api.application.port.in.DeletePlantPortIn;
import ar.germin.api.application.port.in.GetPlantPortIn;
import ar.germin.api.application.port.in.SavePlantPortIn;
import ar.germin.api.application.port.in.UpdatePlantPortIn;
import ar.germin.api.application.port.out.UpdatePlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/plants")
public class PlantControllerAdapter {
    private final SavePlantPortIn savePlantPortIn;
    private final DeletePlantPortIn deletePlantPortIn;
    private final UpdatePlantPortIn updatePlantPortIn;
    private final GetPlantPortIn getPlantPortIn;


    @Autowired
    public PlantControllerAdapter(SavePlantPortIn savePlantPortIn, DeletePlantPortIn deletePlantPortIn, UpdatePlantPortIn updatePlantPortIn, GetPlantPortIn getPlantPortIn) {
        this.savePlantPortIn = savePlantPortIn;
        this.deletePlantPortIn = deletePlantPortIn;
        this.updatePlantPortIn = updatePlantPortIn;
        this.getPlantPortIn = getPlantPortIn;
    }

    @PostMapping
    public Integer createPlant(@RequestBody PlantRequestModel plantRequestModel) {
        return this.savePlantPortIn.save(SavePlantPortIn.Params.builder()
                .alias(plantRequestModel.alias())
                .plantingDate(plantRequestModel.plantingDate())
                .height(plantRequestModel.height())
                .notes(plantRequestModel.notes())
                .idGarden(plantRequestModel.idGarden())
                .idUser(plantRequestModel.idUser())
                .build());
    }

    @DeleteMapping("/{id}")
    public void deletePlant(@PathVariable("id") Integer id) {
        this.deletePlantPortIn.delete(id);
    }

    @PutMapping("/update")
    public Integer updatePlant(@RequestBody PlantRequestModel plantRequestModel) {
        return this.updatePlantPortIn.update(UpdatePlantRepository.Params.builder()
                .id(plantRequestModel.id())
                .alias(plantRequestModel.alias())
                .height(plantRequestModel.height())
                .plantingDate(plantRequestModel.plantingDate())
                .notes(plantRequestModel.notes())
                .idGarden(plantRequestModel.idGarden())
                .isActive(plantRequestModel.isActive())
                .isFavorite(plantRequestModel.isFavorite())
                .build());
    }

    @GetMapping("/{idPlant}")
    public PlantResponseModel getPlant(@RequestHeader("id-user") Integer idUser, @PathVariable Integer idPlant) {

        return PlantResponseModel.fromDomain(this.getPlantPortIn.get(idUser, idPlant));
    }

}
