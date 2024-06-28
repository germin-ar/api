package ar.germin.api.application.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Value
public class Garden {
    Integer id;
    String name;
    @With
    List<Plant> plants;
    Integer idUser;
}
