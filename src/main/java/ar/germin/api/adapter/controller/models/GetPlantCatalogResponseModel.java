package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.PlantCatalog;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetPlantCatalogResponseModel {


    public static GetPlantCatalogResponseModel fromDomain(PlantCatalog plantCatalog){
        return GetPlantCatalogResponseModel.builder().build();
    }


}
