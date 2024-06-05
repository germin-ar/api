package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.Note;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.domain.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class NoteModel {
    private Integer noteId;
    private Integer plantId;
    private Integer userId;
    private String observations;
    private LocalDateTime plantCreationDate;
    private LocalDateTime plantModificationDate;

    public static Note toDomainFromModelList(List<NoteModel> noteModels) {
        Integer id = noteModels.stream().findAny().map(NoteModel::getNoteId).orElseThrow();
        String observations = noteModels.stream().findAny().map(NoteModel::getObservations).orElseThrow();
        User user = noteModels.stream().findAny().map(noteModel -> User.builder()
                .id(noteModel.getUserId())
                .build()).orElseThrow();
        Plant plant = noteModels.stream().findAny().map(noteModel -> Plant.builder()
                .id(noteModel.getPlantId())
                .build()).orElseThrow();
        return Note.builder()
                .id(id)
                .observations(observations)
                .user(user)
                .plant(plant)
                .build();
    }

    public Note toDomain() {
        return Note.builder()
                .id(noteId)
                .user(User.builder()
                        .id(userId)
                        .build())
                .plant(Plant.builder()
                        .id(plantId)
                        .build())
                .observations(observations)
                .build();
    }
}

