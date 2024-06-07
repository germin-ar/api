package ar.germin.api.adapter.rest.models;

import ar.germin.api.application.domain.FileImage;
import lombok.Data;

import java.util.UUID;

@Data
public class SaveFreeImageResponseModel {
    Long statusCode;
    SuccessModel success;
    ImageModel image;
    String statusTxt;

    public FileImage toDomain(byte[] image) {
        return FileImage.builder()
                .id(UUID.nameUUIDFromBytes(image).toString())
                .filePath(this.getImage().getUrl())
                .isPublic(true)
                .build();
    }
}

@Data
class SuccessModel {
    String message;
    Long code;
}

@Data
class ImageModel {
    String name;
    String extension;
    Long width;
    Long height;
    Long size;
    Long time;
    Long expiration;
    Long likes;
    String description;
    String originalFilename;
    Long isAnimated;
    Long nsfw;
    String idEncoded;
    String sizeFormatted;
    String filename;
    String url;
    String urlShort;
    String urlSeo;
    String urlViewer;
    String urlViewerPreview;
    String urlViewerThumb;
    ImageImageModel image;
    ThumbModel thumb;
    MediumModel medium;
    String displayUrl;
    Long displayWidth;
    Long displayHeight;
    String viewsLabel;
    String likesLabel;
    String howLongAgo;
    String dateFixedPeer;
    String title;
    String titleTruncated;
    String titleTruncatedHtml;
    Boolean isUseLoader;
}

@Data
class ImageImageModel {
    String filename;
    String name;
    String mime;
    String extension;
    String url;
    Long size;
}

@Data
class ThumbModel {
    String filename;
    String name;
    String mime;
    String extension;
    String url;

}

@Data
class MediumModel {
    String filename;
    String name;
    String mime;
    String extension;
    String url;
}


