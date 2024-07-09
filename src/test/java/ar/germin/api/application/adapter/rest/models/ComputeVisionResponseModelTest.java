package ar.germin.api.application.adapter.rest.models;

import ar.germin.api.adapter.rest.models.computevision.CaptionsModel;
import ar.germin.api.adapter.rest.models.computevision.ComputeVisionResponseModel;
import ar.germin.api.adapter.rest.models.computevision.DescriptionModel;
import ar.germin.api.application.domain.SpacePlanting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class ComputeVisionResponseModelTest {

    @Test
    void computeVisionResponseModeltoDomain() {
        ComputeVisionResponseModel computeVisionResponseModel = ComputeVisionResponseModel.builder()
                .description(DescriptionModel.builder()
                        .captions(Collections.emptyList())
                        .build())
                .build();

        SpacePlanting spacePlanting = computeVisionResponseModel.toDomain();

        Assertions.assertEquals(0, spacePlanting.getNames().size());
    }

    @Test
    void computeVisionResponseModeltoDomainWithCaptions() {
        ComputeVisionResponseModel computeVisionResponseModel = ComputeVisionResponseModel.builder()
                .description(DescriptionModel.builder()
                        .captions(List.of(
                                CaptionsModel.builder()
                                        .text("text1")
                                        .build()))
                        .build())
                .build();

        SpacePlanting spacePlanting = computeVisionResponseModel.toDomain();

        Assertions.assertEquals(1, spacePlanting.getNames().size());
        Assertions.assertTrue(spacePlanting.getNames().contains("text1"));
    }
}
