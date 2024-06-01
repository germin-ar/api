package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.in.SavePlantsGardenPortIn;
import ar.germin.api.application.port.out.SaveGardenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SaveGardenPlantsUseCase implements SavePlantsGardenPortIn {

    private final SaveGardenRepository saveGardenRepository;

    @Autowired
    public SaveGardenPlantsUseCase(SaveGardenRepository saveGardenRepository) {
        this.saveGardenRepository = saveGardenRepository;
    }

    @Override
    public Garden save(Garden garden) {


        return null;
    }

    @Override
    public Boolean savePlant(Plant plant) {
        return this.saveGardenRepository.savePlant(plant);
    }


}
