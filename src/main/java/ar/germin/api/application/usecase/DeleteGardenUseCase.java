package ar.germin.api.application.usecase;

import ar.germin.api.application.exceptions.GardenNotFoundException;
import ar.germin.api.application.port.in.DeleteGardenPortIn;
import ar.germin.api.application.port.out.DeleteGardenRepository;
import ar.germin.api.application.port.out.GetGardenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeleteGardenUseCase implements DeleteGardenPortIn {
    private final DeleteGardenRepository deleteGardenRepository;
    private final GetGardenRepository getGardenRepository;

    @Autowired
    public DeleteGardenUseCase(DeleteGardenRepository deleteGardenRepository, GetGardenRepository getGardenRepository) {
        this.deleteGardenRepository = deleteGardenRepository;
        this.getGardenRepository = getGardenRepository;
    }

    @Override
    public void delete(DeleteGardenPortIn.Params params) {
        Integer gardenId = params.getGardenId();

        // Verificar si el jardín existe
        if (getGardenRepository.getById(gardenId) == null) {
            log.error("Garden with id {} not found", gardenId);
            throw new GardenNotFoundException();
        }

        // Eliminar el jardín
        deleteGardenRepository.deleteById(gardenId);
    }
}