package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.SpacePlantingResponseModel;
import ar.germin.api.application.port.in.GetSpacePlantingPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/space-planting")
public class SpacePlantingControllerAdapter {
    private final GetSpacePlantingPortIn getSpacePlantingPortIn;

    @Autowired
    public SpacePlantingControllerAdapter(GetSpacePlantingPortIn getSpacePlantingPortIn) {
        this.getSpacePlantingPortIn = getSpacePlantingPortIn;
    }

    @PostMapping
    public Mono<SpacePlantingResponseModel> getSpacePlanting(@RequestHeader("id-user") Integer idUser,
                                                             @RequestPart("image") FilePart filePart) {
        return DataBufferUtils.join(filePart.content())
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return this.getSpacePlantingPortIn.get(bytes);
                })
                .map(SpacePlantingResponseModel::fromDomain);
    }
}
