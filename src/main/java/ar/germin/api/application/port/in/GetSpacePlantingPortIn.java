package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.SpacePlanting;

public interface GetSpacePlantingPortIn {
    SpacePlanting get(byte[] image);
}
