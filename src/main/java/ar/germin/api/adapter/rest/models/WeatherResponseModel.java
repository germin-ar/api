package ar.germin.api.adapter.rest.models;

import ar.germin.api.application.domain.Weather;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponseModel {
    LocationModel location;
    ForecastModel forecast;

    public Weather toDomain() {
        return Weather.builder().build();
    }
}

@Data
class AstroModel {
    String sunrise;
    String sunset;
    String moonrise;
    String moonset;
    String moonPhase;
    Integer moonIllumination;
}

@Data
class Day {
    Double maxtempC;
    Double maxtempF;
    Double mIntegerempC;
    Double mIntegerempF;
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
class ForecastModel {
    List<ForecastdayModel> forecastday;
}

@Data
class ForecastdayModel {
    String date;
    Integer dateEpoch;
    Day day;
    AstroModel astro;
    List<Hour> hour;
}

@Data
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
