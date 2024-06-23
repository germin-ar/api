package ar.germin.api.adapter.rest.models.cropkindwise;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.util.List;


@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CropKindwiseCheckHealthRequestModel {
    Float latitude;
    Float longitude;
    Boolean similarImages;
    List<String> images;
}

