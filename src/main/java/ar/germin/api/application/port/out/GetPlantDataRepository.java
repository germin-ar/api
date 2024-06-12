package ar.germin.api.application.port.out;

public interface GetPlantDataRepository {

    void search(String scientificName);
}
