package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.Plant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlantResponseModel {
    Integer id;
    String alias;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime creationDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime modificationDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime plantingDate;
    String description;
    // TODO ver list -> List<FileImage> images;
    Boolean favorite;
    Double height;
    //TODO enum  ALTO BAJO MEDIO
    String sunExposure;
    String notes;
    String nameGarden;
    String expo;
    Integer idGarden;

    String plantCatalogFamilyName;
    String plantCatalogGenus;
    String plantCatalogWateringFrecuency;
    String plantCatalogDescription;

    String plantCatalogCommonName;
    String plantCatalogScientificName;

    List<PhotoModel> images;

    String plantCatalogSunExposure;


    public static PlantResponseModel fromDomain(Plant plant) {
        return PlantResponseModel.builder()
                .id(plant.getId())
                .alias(plant.getAlias())
                .idGarden(plant.getIdGarden())
                .creationDate(plant.getCreationDate())
                .modificationDate(plant.getModificationDate())
                .plantingDate(plant.getPlantingDate())
                .favorite(plant.getIsFavorite())
                .height(plant.getHeight())
                .notes(plant.getNotes())
                .plantCatalogCommonName(plant.getPcCommonName())
                .plantCatalogScientificName(plant.getPcScientificName())
                .plantCatalogFamilyName(plant.getPcFamilyname())
                .plantCatalogGenus(plant.getPcGenus())
                .plantCatalogWateringFrecuency(plant.getPcWateringFrecuency())
                .plantCatalogSunExposure(plant.getPcSunExposure())
                .plantCatalogDescription(plant.getPcDescription())
                .images(plant
                        .getPhotos()
                        .stream()
                        .map(plantPhoto -> PhotoModel.builder()
                                .url(plantPhoto.getUrl())
                                .build())
                        .toList())
                .build();
    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record PhotoModel(String url) {

    }

}


