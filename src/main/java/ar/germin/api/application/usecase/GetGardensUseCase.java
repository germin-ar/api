package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.in.GetGardensPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class GetGardensUseCase implements GetGardensPortIn {

    private final GetPlantRepository getPlantRepository;
    private final GetGardenRepository getGardenRepository;

    public GetGardensUseCase(GetPlantRepository getPlantRepository,
                             GetGardenRepository getGardenRepository) {
        this.getPlantRepository = getPlantRepository;
        this.getGardenRepository = getGardenRepository;
    }

    @Override
    public List<Garden> getGardensByUser(Integer userId) {
        List<Garden> gardens = this.getGardenRepository
                .getByUserId(userId)
                .stream()
                .map(garden -> {
                    List<Plant> plants = this
                            .getPlantRepository
                            .getByIdGardenAndIdUser(garden.getId(), userId);

                    return garden.withPlants(plants);
                })
                .toList();

        List<Plant> plantsWithoutGarden = this.getPlantRepository
                .getByIdUser(userId)
                .stream()
                .filter(plant -> Optional.ofNullable(plant.getIdGarden()).isEmpty())
                .toList();

        if (plantsWithoutGarden.isEmpty()) {
            return gardens;
        }

        Garden fakeGarden = Garden.builder()
                .plants(plantsWithoutGarden)
                .build();

        return Stream.concat(gardens.stream(), Stream.of(fakeGarden)).toList();
    }

}
