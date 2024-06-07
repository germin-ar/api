package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.domain.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;
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
                    .build();
            return Garden.builder()
                    .id(id)
                    .name(name)
                    .isActive(isActive)
                    .user(user)
                    .build();
        }).collect(Collectors.toList());
    }

    public static List<Garden> toDomainFromModelAllListGardens(List<GardenModel> gardenModels) {
        Map<Integer, Garden> processedGardens = new HashMap<>();

        for (GardenModel gardenModel : gardenModels) {
            Integer id = gardenModel.getGardenId();
            if (!processedGardens.containsKey(id)) {
                Garden garden = Garden.builder()
                        .id(gardenModel.getGardenId())
                        .name(gardenModel.getGardenName())
                        .isActive(gardenModel.getGardenIsActive())
                        .user(User.builder()
                                .id(gardenModel.getUserId())
                                .email(gardenModel.getUserEmail())
                                .name(gardenModel.getUserName())
                                .build())
                        .plants(new ArrayList<>())
                        .build();
                processedGardens.put(id, garden);
            }

            if (gardenModel.getPlantId() != null) {
                Plant plant = Plant.builder()
                        .id(gardenModel.getPlantId())
                        .alias(gardenModel.getPlantName())
                        .creationDate(gardenModel.getPlantCreationDate())
                        .modificationDate(gardenModel.getPlantModificationDate())
                        .isActive(gardenModel.getPlantIsActive())
                        .build();
                processedGardens.get(id).getPlants().add(plant);
            }
        }

        return new ArrayList<>(processedGardens.values());
    }
}


@Data
class UserModel {
    Integer id;
    String name;
    String email;
}
