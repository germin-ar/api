package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.domain.PlantPhoto;
import ar.germin.api.application.port.in.GetPlantPortIn;
import ar.germin.api.application.port.out.GetPlantPhotosRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetPlantUseCase implements GetPlantPortIn {
    private final GetPlantRepository getPlantRepository;
    private final GetPlantPhotosRepository getPlantPhotosRepository;

    public GetPlantUseCase(GetPlantRepository getPlantRepository,
                           GetPlantPhotosRepository getPlantPhotosRepository) {
        this.getPlantRepository = getPlantRepository;
        this.getPlantPhotosRepository = getPlantPhotosRepository;
    }

    @Override
    public Plant get(Integer idUser, Integer idPlant) {
        Plant plant = this.getPlantRepository.getByIdUserAndIdPlant(idUser, idPlant);

        List<PlantPhoto> photos = this.getPlantPhotosRepository.getByPlantId(plant.getId());

        return plant.withPhotos(photos);
    }
}
