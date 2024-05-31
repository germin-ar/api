package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

import java.util.Date;
import java.util.List;

@Builder
@Value
public class Plant {
    Integer id;
    String name;
    Integer id_garden;
    Integer id_plants_catalog;
    Date date_created;
    Date date_modified;
    String description;
    List<FileImage> images;
    Boolean favorite;
    Double height;
    //TODO enum  ALTO BAJO MEDIO
    String sun_exposure;
    String notes;
}
