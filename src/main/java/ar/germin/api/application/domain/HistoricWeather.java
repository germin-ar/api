package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HistoricWeather {
    Integer avgHumidity;
    Double minTemperature;
    Double maxTemperature;
    Double totalprecipMm;
}
