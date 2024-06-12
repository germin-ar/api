package ar.germin.api.adapter.rest.models.trefle;

import lombok.Data;

import java.util.List;

@Data
public class TreflePlantSearchResponseModel {
    private List<TrefleDataModel> data;
}
