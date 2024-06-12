package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.exceptions.GardenNotFoundException;
import ar.germin.api.application.port.in.SavePlantPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.usecase.service.SavePlantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class SavePlantUseCase implements SavePlantPortIn {
    private final GetGardenRepository getGardenRepository;
    private final SavePlantService savePlantService;

    @Autowired
    public SavePlantUseCase(GetGardenRepository getGardenRepository,
                            SavePlantService savePlantService) {
        this.getGardenRepository = getGardenRepository;
        this.savePlantService = savePlantService;
    }

    @Override
    public Integer save(SavePlantPortIn.Params params) {
        if (Optional.ofNullable(params.getIdGarden()).isPresent()) {
            Garden garden = this.getGardenRepository.getById(params.getIdGarden());
            if (!garden.getIdUser().equals(params.getIdUser())) {
                log.error("Jardín con id {} no corresponde al usuario con id {}", garden.getId(), params.getIdUser());
                throw new GardenNotFoundException();
            }
        }
        return this.savePlantService.savePlant(params);

    }


}
