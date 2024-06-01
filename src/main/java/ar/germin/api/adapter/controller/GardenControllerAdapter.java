package ar.germin.api.adapter.controller;


import ar.germin.api.adapter.controller.models.GardenResponseModel;
import ar.germin.api.application.port.in.GetPlantsGardenPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/garden")
public class GardenControllerAdapter {
    private final GetPlantsGardenPortIn getPlantsGardenPortIn;

    @Autowired
    public GardenControllerAdapter(GetPlantsGardenPortIn getPlantsGardenPortIn) {
        this.getPlantsGardenPortIn = getPlantsGardenPortIn;
    }

    @GetMapping("/{id}")
    public GardenResponseModel getGardens(@PathVariable Integer id) {
        return GardenResponseModel.fromDomain(this.getPlantsGardenPortIn.get(id));
    }

    @GetMapping("/as")
    public String getGardens() {
        return "llegaste";
    }
}
