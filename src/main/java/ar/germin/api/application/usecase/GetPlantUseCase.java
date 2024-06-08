package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.in.GetPlantPortIn;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.springframework.stereotype.Component;

@Component
public class GetPlantUseCase implements GetPlantPortIn {
    private final GetPlantRepository getPlantRepository;

    public GetPlantUseCase(GetPlantRepository getPlantRepository) {
        this.getPlantRepository = getPlantRepository;
    }


    @Override
    public Plant get(Integer idUser, Integer idPlant) {
        return this.getPlantRepository.getByIdUserAndIdPlant(idUser, idPlant);
    }
}
