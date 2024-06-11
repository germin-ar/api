package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.List;

@Builder
@Value
public class AIDetection {
    String id;
    String language;
    @With
    List<Candidate> candidates;
    @With
    FileImage fileImage;
}
