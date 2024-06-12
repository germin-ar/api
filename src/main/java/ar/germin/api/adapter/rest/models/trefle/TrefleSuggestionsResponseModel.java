package ar.germin.api.adapter.rest.models.trefle;

import ar.germin.api.application.domain.PlantDataSuggestion;
import lombok.Data;

import java.util.List;

@Data
public class TrefleSuggestionsResponseModel {
    List<TrefleDataModel> data;
    TrefleLinksModel links;
    TrefleMetaModel meta;

    public List<PlantDataSuggestion> toDomain() {
        return this.getData()
                .stream()
                .map(dataModel ->
                        PlantDataSuggestion.builder()
                                .scientificName(dataModel.getScientificName())
                                .commonName(dataModel.getCommonName())
                                .image(dataModel.getImageUrl())
                                .build()
                )
                .toList();
    }
}



