package ar.germin.api.application.usecase;

import ar.germin.api.adapter.controller.models.PlantRequestModel;
import ar.germin.api.adapter.controller.models.PlantResponseModel;

import ar.germin.api.application.port.in.GetPlantPortIn;
import ar.germin.api.application.port.in.UpdatePlantPortIn;

import ar.germin.api.application.port.out.UpdatePlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class UpdatePlantUseCase implements UpdatePlantPortIn, GetPlantPortIn {
    private final UpdatePlantRepository updatePlantRepository;


@Autowired
public UpdatePlantUseCase(UpdatePlantRepository updatePlantRepository) {
    this.updatePlantRepository = updatePlantRepository;

}

    @Override
    public Integer update(PlantRequestModel plantRequestModel) {
        //this.updatePlantRepository.update(plantRequestModel);
        return this.updatePlantRepository.update(plantRequestModel);
    }

    @Override
    public PlantResponseModel get(Integer id) {
        return null;
    }
}
