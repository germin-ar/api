package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.FileImage;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SaveImageResponseModel {
    String id;
    String filePath;
    Boolean isPublic;

    public static SaveImageResponseModel fromDomain(FileImage fileImage) {
        return SaveImageResponseModel.builder()
                .id(fileImage.getId())
                .filePath(fileImage.getFilePath())
                .isPublic(fileImage.getIsPublic())
                .build();
    }
}
