package ar.germin.api.application.usecase;

import ar.germin.api.application.port.out.GetPlantDetailDataRepository;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GetPlantDetailUseCaseTest {

    @Test
    void testSearchDetail() {
        // Arrange
        GetPlantDetailDataRepository mockGetPlantDetailDataRepository = mock(GetPlantDetailDataRepository.class);
        GetPlantDetailDataUseCase getPlantDetailDataUseCase = new GetPlantDetailDataUseCase(mockGetPlantDetailDataRepository);

        String scientificNameWithoutAuthor = "Rosa gallica";

        // Act
        getPlantDetailDataUseCase.searchDetail(scientificNameWithoutAuthor);

        // Assert
        verify(mockGetPlantDetailDataRepository).searchDetail(scientificNameWithoutAuthor);
    }
}
