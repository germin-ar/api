package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.SaveImageResponseModel;
import ar.germin.api.application.port.in.SaveImagePortIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/images")
public class ImageControllerAdapter {
    private final SaveImagePortIn saveImagePortIn;

    @Autowired
    public ImageControllerAdapter(SaveImagePortIn saveImagePortIn) {
        this.saveImagePortIn = saveImagePortIn;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SaveImageResponseModel> saveImage(@RequestPart("image") FilePart filePart) {
        return DataBufferUtils.join(filePart.content())
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return this.saveImagePortIn.save(bytes);
                })
                .map(SaveImageResponseModel::fromDomain);
    }

}
