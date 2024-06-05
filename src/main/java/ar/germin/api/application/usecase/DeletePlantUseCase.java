package ar.germin.api.application.usecase;

import ar.germin.api.application.port.in.DeletePlantPortIn;
import ar.germin.api.application.port.out.DeletePlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class DeletePlantUseCase implements DeletePlantPortIn {

    private final DeletePlantRepository deletePlantRepository;

    public DeletePlantUseCase(DeletePlantRepository deletePlantRepository) {
        this.deletePlantRepository = deletePlantRepository;
    }


    @Override
    public void delete(Integer id) {
        this.deletePlantRepository.delete(id);
    }
}
