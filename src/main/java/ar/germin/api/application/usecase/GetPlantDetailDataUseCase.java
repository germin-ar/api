package ar.germin.api.application.usecase;

import ar.germin.api.application.port.in.GetPlantDetailDataPortIn;
import ar.germin.api.application.port.out.GetPlantDetailDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetPlantDetailDataUseCase implements GetPlantDetailDataPortIn {
    private final GetPlantDetailDataRepository getPlantDetailDataRepository;

    @Autowired
    public GetPlantDetailDataUseCase(GetPlantDetailDataRepository getPlantDetailDataRepository) {
        this.getPlantDetailDataRepository = getPlantDetailDataRepository;
    }


    @Override
    public void searchDetail(String scientificNameWithoutAuthor) {
        this.getPlantDetailDataRepository.searchDetail(scientificNameWithoutAuthor);
    }
}
