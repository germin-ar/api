package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.exceptions.ErrorGardenSaveException;
import ar.germin.api.application.port.in.DeleteGardenPortIn;
import ar.germin.api.application.port.out.DeleteGardenRepository;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DeleteGardenUseCase implements DeleteGardenPortIn {
    private final DeleteGardenRepository deleteGardenRepository;
    private final GetPlantRepository getPlantRepository;

    @Autowired
    public DeleteGardenUseCase(DeleteGardenRepository deleteGardenRepository,
                               GetPlantRepository getPlantRepository) {
        this.deleteGardenRepository = deleteGardenRepository;
        this.getPlantRepository = getPlantRepository;
    }

    @Override
    public void delete(Params params) {

        List<Plant> plants = this.getPlantRepository
                .getByIdGardenAndIdUser(params.getId(), params.getIdUser());

        if (!plants.isEmpty()) {
            throw new ErrorGardenSaveException("Garden with ID " + params.getId() + " tiene plantas.");
        }
        this.deleteGardenRepository.delete(params.getId());


    }
}
