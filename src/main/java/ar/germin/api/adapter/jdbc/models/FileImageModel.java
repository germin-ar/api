package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.FileImage;
import lombok.Data;

@Data
public class FileImageModel {
    String id;
    String filePath;
    Boolean isPublic;

    public FileImage toDomain() {
        return FileImage.builder()
                .id(this.id)
                .filePath(this.filePath)
                .isPublic(this.isPublic)
                .build();
    }
}
