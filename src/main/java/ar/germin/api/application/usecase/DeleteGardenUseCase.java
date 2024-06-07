package ar.germin.api.application.usecase;

import ar.germin.api.application.port.in.DeleteGardenPortIn;
import ar.germin.api.application.port.out.DeleteGardenRepository;
import ar.germin.api.application.port.out.UpdatePlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteGardenUseCase implements DeleteGardenPortIn {

    private final DeleteGardenRepository deleteGardenRepository;
    private final UpdatePlantRepository updatePlantRepository;

    @Autowired
    public DeleteGardenUseCase(DeleteGardenRepository deleteGardenRepository, UpdatePlantRepository updatePlantRepository) {
        this.deleteGardenRepository = deleteGardenRepository;
        this.updatePlantRepository = updatePlantRepository;
    }

    @Override
    public void delete(Params params) {
        Integer gardenId = params.getGardenId();
        updatePlantRepository.updatePlantsSetIdGardenToNull(gardenId);
        deleteGardenRepository.deleteGardenById(gardenId);
    }
}