package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.List;

@Builder
@Value
public class Garden {
    Integer id;
    String name;
    @With
    List<Plant> plants;
}
