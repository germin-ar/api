package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.NoteRequestModel;
import ar.germin.api.adapter.controller.models.NoteResponseModel;
import ar.germin.api.adapter.controller.models.PlantRequestModel;
import ar.germin.api.application.port.in.SaveNotePortIn;
import ar.germin.api.application.port.in.SavePlantPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plants")
public class PlantControllerAdapter {
    private final SavePlantPortIn savePlantPortIn;
    private final SaveNotePortIn saveNotePortIn;

    @Autowired
    public PlantControllerAdapter(SavePlantPortIn savePlantPortIn, SaveNotePortIn saveNotePortIn) {
        this.savePlantPortIn = savePlantPortIn;
        this.saveNotePortIn = saveNotePortIn;
    }

    @PostMapping
    public void createPlant(@RequestBody PlantRequestModel plantRequestModel) {
        this.savePlantPortIn.save(SavePlantPortIn.Params.builder()
                .alias(plantRequestModel.alias())
                .plantingDate(plantRequestModel.plantingDate())
                .height(plantRequestModel.height())
                .notes(plantRequestModel.notes()
                        .stream()
                        .map(note -> NoteResponseModel.builder()
                                .id(note.id())
                                .observations(note.observations())
                                .creationDate(note.creationDate())
                                .modificationDate(note.modificationDate())
                                .build())
                        .toList())
                .idGarden(plantRequestModel.idGarden())
                .build());
    }

    @PostMapping("/{id}/notes")
    public void createNotesForPlant(@PathVariable Integer id, @RequestBody List<NoteRequestModel> noteRequestModels) {
        for (NoteRequestModel noteRequestModel : noteRequestModels) {
            this.saveNotePortIn.save(SaveNotePortIn.Params.builder()
                    .idPlant(id)
                    .idUser(noteRequestModel.userId())
                    .observations(noteRequestModel.observations())
                    .creationDate(noteRequestModel.creationDate())
                    .modificationDate(noteRequestModel.modificationDate())
                    .build());
        }
    }


}
