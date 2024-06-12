package ar.germin.api.adapter.rest.models.plantid;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlantIdCheckHealthRequestModel {
    List<String> images;
    Float latitude;
    Float longitude;
    Boolean similarImages;
}
