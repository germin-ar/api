package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.HealthPlantResponseModel;
import ar.germin.api.adapter.controller.models.PlantResponseModel;
import ar.germin.api.adapter.controller.models.SaveImageResponseModel;
import ar.germin.api.adapter.controller.models.SavePlantRequestModel;
import ar.germin.api.adapter.controller.models.UpdatePlantRequestModel;
import ar.germin.api.application.domain.HealthAIDetection;
import ar.germin.api.application.port.in.DeletePlantPortIn;
import ar.germin.api.application.port.in.GetCandidatesDiseasePlantsPortIn;
import ar.germin.api.application.port.in.GetPlantPortIn;
import ar.germin.api.application.port.in.SaveImagePortIn;
import ar.germin.api.application.port.in.SavePlantPortIn;
import ar.germin.api.application.port.in.UpdatePlantPortIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/plants")
public class PlantControllerAdapter {
    private final SavePlantPortIn savePlantPortIn;
    private final DeletePlantPortIn deletePlantPortIn;
    private final UpdatePlantPortIn updatePlantPortIn;
    private final GetPlantPortIn getPlantPortIn;
    private final GetCandidatesDiseasePlantsPortIn getCandidatesDiseasePlantsPortIn;
    private final SaveImagePortIn saveImagePortIn;

    @Autowired
    public PlantControllerAdapter(SavePlantPortIn savePlantPortIn,
                                  DeletePlantPortIn deletePlantPortIn,
                                  UpdatePlantPortIn updatePlantPortIn,
                                  GetPlantPortIn getPlantPortIn,
                                  GetCandidatesDiseasePlantsPortIn getCandidatesDiseasePlantsPortIn,
                                  SaveImagePortIn saveImagePortIn) {
        this.savePlantPortIn = savePlantPortIn;
        this.deletePlantPortIn = deletePlantPortIn;
        this.updatePlantPortIn = updatePlantPortIn;
        this.getPlantPortIn = getPlantPortIn;
        this.getCandidatesDiseasePlantsPortIn = getCandidatesDiseasePlantsPortIn;
        this.saveImagePortIn = saveImagePortIn;
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
                        .notes(savePlantRequestModel.notes())
                        .idUser(idUser)
                        .idPlantCatalog(savePlantRequestModel.idPlantCatalog())
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
                        //.plantingDate(updatePlantRequestModel.plantingDate())
                        .notes(updatePlantRequestModel.notes())
                        .idGarden(updatePlantRequestModel.idGarden())
                        .idUser(idUser)
                        .isFavorite(updatePlantRequestModel.isFavorite())
                        .build());
    }

    @GetMapping("/{id}")
    public PlantResponseModel getPlant(@PathVariable Integer id,
                                       @RequestHeader("id-user") Integer idUser) {
        return PlantResponseModel.fromDomain(this.getPlantPortIn.get(idUser, id));
    }

    @GetMapping("/{id}/health-status")
    public HealthPlantResponseModel getHealthPlantStatus(@PathVariable Integer id,
                                                         @RequestHeader("id-user") Integer idUser) {
        HealthAIDetection healthAIDetection = this.getCandidatesDiseasePlantsPortIn.get(id);

        return HealthPlantResponseModel.fromDomain(healthAIDetection);
    }

    @PostMapping(value = "/{id}/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Object> uploadPhoto(@RequestHeader("id-user") Integer idUser,
                                    @PathVariable("id") Integer idPlant,
                                    @RequestPart("image") FilePart filePart) {

        return DataBufferUtils.join(filePart.content())
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return this.saveImagePortIn.saveHistory(bytes, idPlant);
                })
                .map(SaveImageResponseModel::fromDomain);
    }

}
