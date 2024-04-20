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
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Slf4j
public class FreeImageRestAdapter implements UploadFileRepository {
    private final GerminarConfiguration germinarConfiguration;
    private final WebClient webClient;

    @Autowired
    public FreeImageRestAdapter(GerminarConfiguration germinarConfiguration,
                                WebClient.Builder webClientBuilder) {
        this.germinarConfiguration = germinarConfiguration;
        this.webClient = webClientBuilder
                .baseUrl("https://freeimage.host")
                .build();
    }

    @Override
    public Mono<FileImage> upload(byte[] file) {
        try {
            log.info("Uploading image");
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("source", new ByteArrayResource(file)).filename(UUID.randomUUID().toString());

            return webClient
                    .post()
                    .uri(uriBuilder ->
                            uriBuilder
                                    .path("/api/1/upload")
                                    .queryParam("key", germinarConfiguration.integrations().freeImage().apiKey())
                                    .queryParam("action", "upload")
                                    .build()
                    )
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .bodyValue(builder.build())
                    .retrieve()
                    .bodyToMono(SaveFreeImageResponseModel.class)
                    .map(saveFreeImageResponseModel -> saveFreeImageResponseModel.toDomain(file));
        } catch (Exception e) {
            log.error("Error uploading image", e);
            throw e;
        }
    }
}
