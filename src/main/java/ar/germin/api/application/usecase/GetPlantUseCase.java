package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.domain.PlantHistory;
import ar.germin.api.application.domain.PlantPhoto;
import ar.germin.api.application.port.in.GetPlantPortIn;
import ar.germin.api.application.port.out.GetPlantHistoryRepository;
import ar.germin.api.application.port.out.GetPlantPhotosRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetPlantUseCase implements GetPlantPortIn {
    private final GetPlantRepository getPlantRepository;
    private final GetPlantPhotosRepository getPlantPhotosRepository;
    private final GetPlantHistoryRepository getPlantHistoryRepository;

    public GetPlantUseCase(GetPlantRepository getPlantRepository,
                           GetPlantPhotosRepository getPlantPhotosRepository,
                           GetPlantHistoryRepository getPlantHistoryRepository) {
        this.getPlantRepository = getPlantRepository;
        this.getPlantPhotosRepository = getPlantPhotosRepository;
        this.getPlantHistoryRepository = getPlantHistoryRepository;
    }

    @Override
    public Plant get(Integer idUser, Integer idPlant) {
        Plant plant = this.getPlantRepository.getByIdUserAndIdPlant(idUser, idPlant);

        List<PlantPhoto> photos = this.getPlantPhotosRepository.getByPlantId(plant.getId());

        List<PlantHistory> history = this.getPlantHistoryRepository.getByPlantId(plant.getId());

        return plant.withPhotos(photos).withHistory(history);
    }
}
