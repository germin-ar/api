package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.List;


@Value
@Builder
public class SpacePlanting {
    @With
    List<String> names;
}
