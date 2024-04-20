package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class FileImage {
    String id;
    String filePath;
    Boolean isPublic;
    String checksum;
}
