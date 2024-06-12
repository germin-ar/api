package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.HistoricWeather;

public interface GetWeatherRepository {
    HistoricWeather getWeather(Float latitude, Float longitude);
}
