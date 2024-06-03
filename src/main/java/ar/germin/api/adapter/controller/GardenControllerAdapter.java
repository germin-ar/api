package ar.germin.api.adapter.controller;


import ar.germin.api.adapter.controller.models.GardenResponseModel;
import ar.germin.api.adapter.controller.models.PlantResponseModel;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.in.GetPlantsGardenPortIn;
import ar.germin.api.application.port.in.SavePlantsGardenPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/garden")
public class GardenControllerAdapter {
    private final GetPlantsGardenPortIn getPlantsGardenPortIn;
    private final SavePlantsGardenPortIn savePlantPorIn;

    @Autowired
    public GardenControllerAdapter(GetPlantsGardenPortIn getPlantsGardenPortIn, SavePlantsGardenPortIn savePlantsGardenPortIn) {
        this.getPlantsGardenPortIn = getPlantsGardenPortIn;
        this.savePlantPorIn = savePlantsGardenPortIn;
    }

    @GetMapping("/{id}")
    public GardenResponseModel getGardens(@PathVariable Integer id) {
        return GardenResponseModel.fromDomain(this.getPlantsGardenPortIn.get(id));
    }

    @PostMapping("/addPlant")
    public ResponseEntity<PlantResponseModel> addPlantToGarden(@RequestBody Plant newPlant){
        PlantResponseModel plant =  PlantResponseModel.fromDomain(newPlant);
        return ResponseEntity.status(HttpStatus.CREATED).body(plant);
        //return ResponseEntity.status(HttpStatus.CREATED).body(this.savePlantPorIn.savePlant(plant));
    }

    @GetMapping("/a")
    public String hola(){
        return "Hola";
    }

    @GetMapping("/gardens")
    public List<GardenResponseModel> getGardensByUser(@RequestHeader("id-user") Integer userId) {
        return List.of();
    }

    @DeleteMapping("/{id}")
    public void deleteGarden(@PathVariable("id") Integer id) {
        // TODO: borrar jardín
    }
}
