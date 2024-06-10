package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.port.in.SavePlantCatalogPortIn;
import ar.germin.api.application.port.out.SavePlantCatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SavePlantCatalogUseCase implements SavePlantCatalogPortIn {
  private final SavePlantCatalogRepository savePlantCatalogRepository;

  @Autowired
  public SavePlantCatalogUseCase(SavePlantCatalogRepository savePlantCatalogRepository) {
    this.savePlantCatalogRepository = savePlantCatalogRepository;
  }

  @Override
  public Integer save(PlantCatalog plantCatalog) {
    return this.savePlantCatalogRepository.save( plantCatalog );
  }
}
