package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.domain.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Data
public class GardenModel {
    private Integer gardenId;
    private String gardenName;
    private Integer userId;
    private String userEmail;
    private String userName;
    private Integer plantId;
    private String plantName;
    private LocalDateTime plantCreationDate;
    private LocalDateTime plantModificationDate;

    public Garden toDomain() {
        return Garden.builder()
                .id(gardenId)
                .name(gardenName)
                .user(User.builder()
                        .id(userId)
                        .email(userEmail)
                        .name(userName)
                        .build())
                .build();
    }

    public static Garden toDomainFromModelList(List<GardenModel> gardenModels) {
        Integer id = gardenModels.stream().findAny().map(GardenModel::getGardenId).orElseThrow();
        String name = gardenModels.stream().findAny().map(GardenModel::getGardenName).orElseThrow();
        User user = gardenModels.stream().findAny().map(gardenModel -> User.builder()
                .id(gardenModel.getUserId())
                .email(gardenModel.getUserEmail())
                .name(gardenModel.getUserName())
                .build()).orElseThrow();

        return Garden.builder()
                .id(id)
                .name(name)
                .user(user)
                .plants(gardenModels
                        .stream()
                        .filter(gardenModel -> Optional.ofNullable(gardenModel.plantId).isPresent())
                        .map(gardenModel -> Plant.builder()
                                .id(gardenModel.getPlantId())
                                .alias(gardenModel.getPlantName())
                                .creationDate(gardenModel.getPlantCreationDate())
                                .modificationDate(gardenModel.getPlantModificationDate())
                                .build())
                        .toList())
                .build();
    }
}

@Data
class UserModel {
    Integer id;
    String name;
    String email;
}
