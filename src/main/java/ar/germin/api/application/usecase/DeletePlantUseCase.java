package ar.germin.api.application.usecase;

import ar.germin.api.application.port.in.DeletePlantPortIn;
import ar.germin.api.application.port.out.DeletePlantRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeletePlantUseCase implements DeletePlantPortIn {
    private final GetPlantRepository getPlantRepository;
    private final DeletePlantRepository deletePlantRepository;

    @Autowired
    public DeletePlantUseCase(GetPlantRepository getPlantRepository,
                              DeletePlantRepository deletePlantRepository) {
        this.deletePlantRepository = deletePlantRepository;
        this.getPlantRepository = getPlantRepository;
    }

    @Override
    public void delete(Params params) {
        this.getPlantRepository.getByIdUserAndIdPlant(params.getIdUser(), params.getId());

        this.deletePlantRepository.delete(params.getId());
    }
}
