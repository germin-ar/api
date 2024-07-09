package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.CreateGardenRequestModel;
import ar.germin.api.adapter.controller.models.GardenResponseModel;
import ar.germin.api.application.port.in.DeleteGardenPortIn;
import ar.germin.api.application.port.in.GetGardenPortIn;
import ar.germin.api.application.port.in.GetGardensPortIn;
import ar.germin.api.application.port.in.SaveGardenPortIn;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/gardens")
public class GardenControllerAdapter {
    private final GetGardensPortIn getGardensPortIn;
    private final SaveGardenPortIn saveGardenPortIn;
    private final GetGardenPortIn getGardenPortIn;
    private final DeleteGardenPortIn deleteGardenPortIn;


    @Autowired
    public GardenControllerAdapter(GetGardensPortIn getGardensPortIn,
                                   SaveGardenPortIn saveGardenPortIn,
                                   GetGardenPortIn getGardenPortIn,
                                   DeleteGardenPortIn deleteGardenPortIn) {
        this.getGardensPortIn = getGardensPortIn;
        this.saveGardenPortIn = saveGardenPortIn;
        this.getGardenPortIn = getGardenPortIn;
        this.deleteGardenPortIn = deleteGardenPortIn;
    }

    @GetMapping
    public List<GardenResponseModel> getGardensByUser(@RequestHeader("id-user") Integer idUser) {
        return GardenResponseModel.fromDomainList(this.getGardensPortIn.getGardensByUser(idUser));
    }

    @GetMapping("/{id}")
    public GardenResponseModel getGarden(@PathVariable Integer id, @RequestHeader("id-user") Integer idUser) {
        return GardenResponseModel.fromDomain(this.getGardenPortIn.get(id, idUser));
    }

    @PostMapping
    public Integer saveGarden(@RequestHeader("id-user") Integer idUser, @RequestBody CreateGardenRequestModel createGardenRequestModel) {
        return this.saveGardenPortIn.save(idUser, createGardenRequestModel.name());
    }


    @DeleteMapping("/{id}")
    public void deleteGarden(@PathVariable("id") Integer id, @RequestHeader("id-user") Integer idUser) {
        this.deleteGardenPortIn.delete(
                DeleteGardenPortIn.Params.builder()
                        .id(id)
                        .idUser(idUser)
                        .build());
    }

}
