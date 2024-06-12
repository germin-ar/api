package ar.germin.api.adapter.rest;

import ar.germin.api.adapter.rest.models.SaveFreeImageResponseModel;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.port.out.UploadFileRepository;
import ar.germin.api.configuration.GerminarConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@Slf4j
public class FreeImageRestAdapter implements UploadFileRepository {
    private final GerminarConfiguration germinarConfiguration;
    private final RestClient restClient;

    @Autowired
    public FreeImageRestAdapter(GerminarConfiguration germinarConfiguration) {
        this.germinarConfiguration = germinarConfiguration;
        this.restClient = RestClient.builder()
                .baseUrl("https://freeimage.host")
                .build();
    }

    @Override
    public FileImage upload(byte[] file) {
        try {
            log.info("Uploading image");
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("source", new ByteArrayResource(file)).filename(UUID.randomUUID().toString());

            return restClient
                    .post()
                    .uri(uriBuilder ->
                            uriBuilder
                                    .path("/api/1/upload")
                                    .queryParam("key", germinarConfiguration.integrations().freeImage().apiKey())
                                    .queryParam("action", "upload")
                                    .build()
                    )
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(builder.build())
                    .retrieve()
                    .body(SaveFreeImageResponseModel.class)
                    .toDomain(file);
        } catch (Exception e) {
            log.error("Error uploading image", e);
            throw e;
        }
    }
}
