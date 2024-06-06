package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class Garden {
    Integer id;
    String name;
    User user;
    Boolean isActive;
    List<Plant> plants;
}
