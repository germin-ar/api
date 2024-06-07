package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.in.SavePlantPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.SavePlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SavePlantUseCase implements SavePlantPortIn {
    private final SavePlantRepository savePlantRepository;
    private final GetGardenRepository getGardenRepository;

    @Autowired
    public SavePlantUseCase(SavePlantRepository savePlantRepository, GetGardenRepository getGardenRepository) {
        this.savePlantRepository = savePlantRepository;
        this.getGardenRepository = getGardenRepository;
    }

    @Override
    public Integer save(SavePlantPortIn.Params params) {
        // TODO: validar si el jardín existe y que corresponda al usuario que llama
        return this.savePlantRepository.save(SavePlantRepository.Params.builder()
                .idGarden(params.getIdGarden())
                .alias(params.getAlias())
                .notes(params.getNotes())
                .height(params.getHeight())
                .plantingDate(params.getPlantingDate())
                .build());
    }
}
