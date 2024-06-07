package ar.germin.api.application.usecase;

import ar.germin.api.adapter.controller.models.PlantResponseModel;
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
    public PlantResponseModel get(Integer idUser, Integer idPlant) {
        return this.getPlantRepository.get(idUser, idPlant);
    }
}
