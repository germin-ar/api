package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.port.in.GetPlantsGardenPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetGardenPlantsUseCase implements GetPlantsGardenPortIn {

    private final GetGardenRepository getGardenRepository;

    public GetGardenPlantsUseCase(GetGardenRepository getGardenRepository) {
        this.getGardenRepository = getGardenRepository;
    }


    @Override
    public Garden get(Integer id) {
        return this.getGardenRepository.getById(id);
    }

    @Override
    public List<Garden> getGardensByUser(Integer id) {
        return this.getGardenRepository.getByUserId(id);
    }

    @Override
    public List<Garden> getAllGardensByUser(Integer id) {
        return this.getGardenRepository.getAllGardensByUserId(id);
    }


}
