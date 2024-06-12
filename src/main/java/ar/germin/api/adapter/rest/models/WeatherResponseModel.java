package ar.germin.api.adapter.rest.models;

import ar.germin.api.application.domain.HistoricWeather;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WeatherResponseModel {
    @JsonProperty("location")
    LocationModel location;

    @JsonProperty("forecast")
    ForecastModel forecast;

    public HistoricWeather toDomain() {
        Double avgMinTempC = this.getForecast().getForecastday().stream().mapToDouble(f -> f.getDay().getMintempC()).average().orElse(0);
        Double avgMaxTempC = this.getForecast().getForecastday().stream().mapToDouble(f -> f.getDay().getMaxtempC()).average().orElse(0);
        Double avgHumidity = this.getForecast().getForecastday().stream().mapToInt(f -> f.getDay().getAvghumidity()).average().orElse(0);
        Double avgTotalPrecMm = this.getForecast().getForecastday().stream().mapToDouble(f -> f.getDay().getTotalprecipMm()).average().orElse(0);

        return HistoricWeather.builder()
                .minTemperature(avgMinTempC)
                .maxTemperature(avgMaxTempC)
                .avgHumidity(avgHumidity.intValue())
                .totalprecipMm(avgTotalPrecMm)
                .build();
    }
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class AstroModel {
    String sunrise;
    String sunset;
    String moonrise;
    String moonset;
    String moonPhase;
    Integer moonIllumination;
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
class Day {
    Double maxtempC;
    Double maxtempF;
    Double mintempC;
    Double mintempF;
    Double avgtempC;
    Double avgtempF;
    Double maxwindMph;
    Double maxwindKph;
    Double totalprecipMm;
    Double totalprecipIn;
    Double totalsnowCm;
    Double avgvisKm;
    Double avgvisMiles;
    Integer avghumidity;
    Integer dailyWillItRain;
    Integer dailyChanceOfRain;
    Integer dailyWillItSnow;
    Integer dailyChanceOfSnow;
    Double uv;
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
class ForecastModel {
    List<ForecastdayModel> forecastday;
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class ForecastdayModel {
    String date;
    Integer dateEpoch;
    Day day;
    AstroModel astro;
    List<Hour> hour;
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
class Hour {
    Integer timeEpoch;
    String time;
    Double tempC;
    Double tempF;
    Integer isDay;
    Double windMph;
    Double windKph;
    Integer windDegree;
    String windDir;
    Double pressureMb;
    Double pressureIn;
    Double precipMm;
    Double precipIn;
    Double snowCm;
    Integer humidity;
    Integer cloud;
    Double feelslikeC;
    Double feelslikeF;
    Double windchillC;
    Double windchillF;
    Double heatindexC;
    Double heatindexF;
    Double dewpoIntegerC;
    Double dewpoIntegerF;
    Integer willItRain;
    Integer chanceOfRain;
    Integer willItSnow;
    Integer chanceOfSnow;
    Double visKm;
    Double visMiles;
    Double gustMph;
    Double gustKph;
    Double uv;
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class LocationModel {
    String name;
    String region;
    String country;
    Double lat;
    Double lon;
    String tzId;
    Integer localtimeEpoch;
    String localtime;
}
