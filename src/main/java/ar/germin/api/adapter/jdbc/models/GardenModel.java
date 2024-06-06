package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.domain.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Data
public class GardenModel {
    private Integer gardenId;
    private String gardenName;
    private Integer userId;
    private String userEmail;
    private String userName;
    private Boolean gardenIsActive;
    private Integer plantId;
    private String plantName;
    private LocalDateTime plantCreationDate;
    private LocalDateTime plantModificationDate;
    private Boolean plantIsActive;

    public Garden toDomain() {
        return Garden.builder()
                .id(gardenId)
                .name(gardenName)
                .isActive(gardenIsActive)
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
        Boolean isActive = gardenModels.stream().findAny().map(GardenModel::getGardenIsActive).orElseThrow();
        User user = gardenModels.stream().findAny().map(gardenModel -> User.builder()
                .id(gardenModel.getUserId())
                .email(gardenModel.getUserEmail())
                .name(gardenModel.getUserName())
                .build()).orElseThrow();

        return Garden.builder()
                .id(id)
                .name(name)
                .user(user)
                .isActive(isActive)
                .plants(gardenModels
                        .stream()
                        .filter(gardenModel -> Optional.ofNullable(gardenModel.plantId).isPresent())
                        .map(gardenModel -> Plant.builder()
                                .id(gardenModel.getPlantId())
                                .alias(gardenModel.getPlantName())
                                .creationDate(gardenModel.getPlantCreationDate())
                                .modificationDate(gardenModel.getPlantModificationDate())
                                .isActive(gardenModel.getPlantIsActive())
                                .build())
                        .toList())
                .build();
    }

    public static List<Garden> toDomainFromModelListGardens(List<GardenModel> gardenModels) {
        return gardenModels.stream().map(gardenModel -> {
            Integer id = gardenModel.getGardenId();
            String name = gardenModel.getGardenName();
            Boolean isActive = gardenModel.getGardenIsActive();
            User user = User.builder()
                    .id(gardenModel.getUserId())
                    .email(gardenModel.getUserEmail())
                    .name(gardenModel.getUserName())
                    .build();
            List<Plant> plants = gardenModels.stream()
                    .filter(gm -> Optional.ofNullable(gm.plantId).isPresent())
                    .map(gm -> Plant.builder()
                            .id(gm.getPlantId())
                            .alias(gm.getPlantName())
                            .creationDate(gm.getPlantCreationDate())
                            .modificationDate(gm.getPlantModificationDate())
                            .isActive(gm.getPlantIsActive())
                            .build())
                    .collect(Collectors.toList());

            return Garden.builder()
                    .id(id)
                    .name(name)
                    .isActive(isActive)
                    .user(user)
                    .plants(plants)
                    .build();
        }).collect(Collectors.toList());
    }
}

@Data
class UserModel {
    Integer id;
    String name;
    String email;
}
