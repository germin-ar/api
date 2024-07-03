package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.domain.SpacePlanting;
import ar.germin.api.application.port.in.GetSpacePlantingPortIn;
import ar.germin.api.application.port.out.GetSpacePlantingRepository;
import ar.germin.api.application.port.out.UploadFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetSpacePlantingUseCase implements GetSpacePlantingPortIn {
    private final UploadFileRepository uploadFileRepository;
    private final GetSpacePlantingRepository getSpacePlantingRepository;
    private final List<String> possiblePlaces = List.of(
            "comedor",
            "dormitorio",
            "habitacion",
            "habitación",
            "sala",
            "bano",
            "baño",
            "jardin",
            "jardín",
            "cocina",
            "balcon",
            "terraza",
            "living",
            "cuarto");


    @Autowired
    public GetSpacePlantingUseCase(UploadFileRepository uploadFileRepository,
                                   GetSpacePlantingRepository getSpacePlantingRepository) {
        this.uploadFileRepository = uploadFileRepository;
        this.getSpacePlantingRepository = getSpacePlantingRepository;
    }

    @Override
    public SpacePlanting get(byte[] image) {
        FileImage fileImage = this.uploadFileRepository.upload(image);

        SpacePlanting spacePlanting = this.getSpacePlantingRepository.getSpacePlanting(fileImage);
        List<String> names = possiblePlaces.stream().filter(placeName ->
                !spacePlanting.getNames().stream().filter(a -> a.contains(placeName))
                        .toList()
                        .isEmpty())
                .toList();
        // List<String> names = spacePlanting.getNames().stream().filter(possiblePlaces::contains).toList();

        return spacePlanting.withNames(names);
    }
}
