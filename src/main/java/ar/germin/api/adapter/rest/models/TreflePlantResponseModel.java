package ar.germin.api.adapter.rest.models;

import ar.germin.api.application.domain.PlantCatalog;
import lombok.Data;

import java.util.List;

@Data
public class TreflePlantResponseModel {
    private DataModel1 data;
}

@Data
class DataModel1 {
    public int id;
    public String common_name;
    public String slug;
    public String scientific_name;
    public int main_species_id;
    public String image_url;
    public int year;
}







