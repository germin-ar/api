package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.exceptions.PlantNotFoundException;
import ar.germin.api.application.port.in.UpdatePlantPortIn;
import ar.germin.api.application.port.out.GetPlantRepository;
import ar.germin.api.application.port.out.UpdatePlantRepository;
import ar.germin.api.application.usecase.service.UpdatePlantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class UpdatePlantUseCase implements UpdatePlantPortIn {
    private final GetPlantRepository getPlantRepository;
    private final UpdatePlantRepository updatePlantRepository;
    private final UpdatePlantService updatePlantService;

    @Autowired
    public UpdatePlantUseCase(GetPlantRepository getPlantRepository,
                              UpdatePlantRepository updatePlantRepository,
                              UpdatePlantService updatePlantService) {
        this.getPlantRepository = getPlantRepository;
        this.updatePlantRepository = updatePlantRepository;
        this.updatePlantService = updatePlantService;
    }

    @Override
    public Integer update(UpdatePlantPortIn.Params params) {
        if (Optional.ofNullable(params.getIdUser()).isPresent())
        {
            Plant plant =  this.getPlantRepository.getByIdUserAndIdPlant(params.getIdUser(), params.getId());
            if (!plant.getId().equals(params.getId())){
                log.error("Planta con id {} no corresponde al usuario con id {}", plant.getId(), params.getIdUser());
                throw new PlantNotFoundException();
            }
        }

        return this.updatePlantService.updatePlant(params);


    }


}
