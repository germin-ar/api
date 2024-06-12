package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.domain.PlantPhoto;
import ar.germin.api.application.exceptions.GardenNotFoundException;
import ar.germin.api.application.port.in.GetGardenPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.GetPlantPhotosRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class GetGardenUseCase implements GetGardenPortIn {
    private final GetGardenRepository getGardenRepository;
    private final GetPlantRepository getPlantRepository;
    private final GetPlantPhotosRepository getPlantPhotosRepository;

    @Autowired
    public GetGardenUseCase(GetGardenRepository getGardenRepository,
                            GetPlantRepository getPlantRepository,
                            GetPlantPhotosRepository getPlantPhotosRepository) {
        this.getGardenRepository = getGardenRepository;
        this.getPlantRepository = getPlantRepository;
        this.getPlantPhotosRepository = getPlantPhotosRepository;
    }

    @Override
    public Garden get(Integer id, Integer userId) {
        Garden garden = this.getGardenRepository.getById(id);
        if (!garden.getIdUser().equals(userId)) {
            log.error("the garden with id {} is not for the user with id {}", id, userId);
            throw new GardenNotFoundException();
        }

        List<Plant> plants = this.getPlantRepository
                .getByIdGardenAndIdUser(garden.getId(), userId)
                .stream()
                .map(p -> p.withPhotos(getPlantPhotosRepository.getByPlantId(p.getId())))
                .toList();

        return garden.withPlants(plants);
    }
}
