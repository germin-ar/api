package ar.germin.api.application.port.out;

public interface UpdatePlantRepository {
        void removeGardenIdFromPlants(Integer gardenId);

        void updatePlantsSetIdGardenToNull(Integer gardenId);
}
