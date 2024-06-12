package ar.germin.api.application.usecase;

import ar.germin.api.application.port.in.UpdatePlantPortIn;
import ar.germin.api.application.port.out.GetPlantRepository;
import ar.germin.api.application.port.out.UpdatePlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdatePlantUseCase implements UpdatePlantPortIn {
    private final GetPlantRepository getPlantRepository;
    private final UpdatePlantRepository updatePlantRepository;

    @Autowired
    public UpdatePlantUseCase(GetPlantRepository getPlantRepository,
                              UpdatePlantRepository updatePlantRepository) {
        this.getPlantRepository = getPlantRepository;
        this.updatePlantRepository = updatePlantRepository;
    }

    @Override
    public Integer update(UpdatePlantPortIn.Params params) {
        this.getPlantRepository.getByIdUserAndIdPlant(params.getIdUser(), params.getId());

        return this.updatePlantRepository.update(
                UpdatePlantRepository.Params.builder()
                        .id(params.getId())
                        .idGarden(params.getIdGarden())
                        .isFavorite(params.getIsFavorite())
                        .height(params.getHeight())
                        .alias(params.getAlias())
                        .plantingDate(params.getPlantingDate())
                        .build());
    }


}
