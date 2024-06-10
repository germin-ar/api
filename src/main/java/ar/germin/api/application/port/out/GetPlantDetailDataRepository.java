package ar.germin.api.application.port.out;

public interface GetPlantDetailDataRepository {
  void searchDetail(String scientificNameWithoutAuthor);
}
