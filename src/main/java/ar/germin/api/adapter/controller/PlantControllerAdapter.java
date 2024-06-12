package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.PlantResponseModel;
import ar.germin.api.adapter.controller.models.SavePlantRequestModel;
import ar.germin.api.adapter.controller.models.UpdatePlantRequestModel;
import ar.germin.api.application.port.in.DeletePlantPortIn;
import ar.germin.api.application.port.in.GetPlantPortIn;
import ar.germin.api.application.port.in.SavePlantPortIn;
import ar.germin.api.application.port.in.UpdatePlantPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public Integer createPlant(@RequestHeader("id-user") Integer idUser,
                               @RequestBody SavePlantRequestModel savePlantRequestModel) {
        return this.savePlantPortIn.save(
                SavePlantPortIn.Params.builder()
                        .alias(savePlantRequestModel.alias())
                        .plantingDate(savePlantRequestModel.plantingDate())
                        .height(savePlantRequestModel.height())
                        .idGarden(savePlantRequestModel.idGarden())
                        .idUser(idUser)
                        .imageUrl(savePlantRequestModel.imageUrl())
                        .build());
    }

    @DeleteMapping("/{id}")
    public void deletePlant(@PathVariable("id") Integer id,
                            @RequestHeader("id-user") Integer idUser) {
        this.deletePlantPortIn.delete(
                DeletePlantPortIn.Params.builder()
                        .id(id)
                        .idUser(idUser)
                        .build());
    }

    @PostMapping("/{id}")
    public Integer update(@PathVariable("id") Integer id,
                          @RequestHeader("id-user") Integer idUser,
                          @RequestBody UpdatePlantRequestModel updatePlantRequestModel) {
        return this.updatePlantPortIn.update(
                UpdatePlantPortIn.Params.builder()
                        .id(id)
                        .alias(updatePlantRequestModel.alias())
                        .height(updatePlantRequestModel.height())
                        .plantingDate(updatePlantRequestModel.plantingDate())
                        .idGarden(updatePlantRequestModel.idGarden())
                        .idUser(idUser)
                        .isFavorite(updatePlantRequestModel.isFavorite())
                        .build());
    }

    @GetMapping("/{id}")
    public PlantResponseModel getPlant(@PathVariable Integer id, @RequestHeader("id-user") Integer idUser) {
        return PlantResponseModel.fromDomain(this.getPlantPortIn.get(idUser, id));
    }

}
