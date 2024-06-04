package ar.germin.api.adapter.controller;


import ar.germin.api.adapter.controller.models.CreateGardenRequestModel;
import ar.germin.api.adapter.controller.models.GardenResponseModel;
import ar.germin.api.application.port.in.GetPlantsGardenPortIn;
import ar.germin.api.application.port.in.SaveGardenPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/gardens")
public class GardenControllerAdapter {
    private final GetPlantsGardenPortIn getPlantsGardenPortIn;
    private final SaveGardenPortIn saveGardenPortIn;

    @Autowired
    public GardenControllerAdapter(GetPlantsGardenPortIn getPlantsGardenPortIn, SaveGardenPortIn saveGardenPortIn) {
        this.getPlantsGardenPortIn = getPlantsGardenPortIn;
        this.saveGardenPortIn = saveGardenPortIn;
    }

    @GetMapping
    public List<GardenResponseModel> getGardensByUser(@RequestHeader("id-user") Integer userId) {
        return List.of();
    }

    @GetMapping("/{id}")
    public GardenResponseModel getGardens(@PathVariable Integer id) {
        return GardenResponseModel.fromDomain(this.getPlantsGardenPortIn.get(id));
    }

    @PostMapping
    public void saveGarden(@RequestBody CreateGardenRequestModel createGardenRequestModel) {
        this.saveGardenPortIn.save(createGardenRequestModel.getUserId(), createGardenRequestModel.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteGarden(@PathVariable("id") Integer id) {
        // TODO: borrar jardín
    }
}
