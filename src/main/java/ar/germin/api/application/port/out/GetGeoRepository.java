package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.Address;

public interface GetGeoRepository {
    Address getAddress(Float latitude, Float longitude);
}
