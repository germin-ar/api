package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.in.GetGardenPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetGardenUseCase implements GetGardenPortIn {
    private final GetGardenRepository getGardenRepository;
    private final GetPlantRepository getPlantRepository;

    @Autowired
    public GetGardenUseCase(GetGardenRepository getGardenRepository,
                            GetPlantRepository getPlantRepository) {
        this.getGardenRepository = getGardenRepository;
        this.getPlantRepository = getPlantRepository;
    }

    @Override
    public Garden get(Integer id, Integer userId) {
        Garden garden = this.getGardenRepository.getById(id);

        List<Plant> plants = this.getPlantRepository.getByIdGardenAndIdUser(garden.getId(), userId);
        return garden.withPlants(plants);
    }
}
