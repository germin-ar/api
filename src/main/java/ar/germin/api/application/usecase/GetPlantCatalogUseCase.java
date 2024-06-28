package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.HistoricWeather;
import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.port.in.GetPlantCatalogPortIn;
import ar.germin.api.application.port.out.GetPlantCatalogRepository;
import ar.germin.api.application.port.out.GetWeatherRepository;
import ar.germin.api.application.usecase.service.GetPlantCatalogService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Component
public class GetPlantCatalogUseCase implements GetPlantCatalogPortIn {
    private final GetPlantCatalogService getPlantCatalogService;
    private final GetWeatherRepository getWeatherRepository;
    private final GetPlantCatalogRepository getPlantCatalogRepository;


    public GetPlantCatalogUseCase(GetPlantCatalogService getPlantCatalogService,
                                  GetWeatherRepository getWeatherRepository,
                                  GetPlantCatalogRepository getPlantCatalogRepository) {
        this.getPlantCatalogService = getPlantCatalogService;
        this.getWeatherRepository = getWeatherRepository;
        this.getPlantCatalogRepository = getPlantCatalogRepository;
    }
    //nuevo endpoint
    @Override
    public PlantCatalog getPlantCatalog(String scientificName) {
        return this.getPlantCatalogService.getPlantCatalog(scientificName);
    }

    @Override
    public List<PlantCatalog> getPlantsCatalog(Float latitude, Float longitude, Integer sunExposure, Integer squareMeters) {
        HistoricWeather weather = this.getWeatherRepository.getWeather(latitude, longitude);
        String luz = this.getSunLight(sunExposure);
        String temporada = this.getTemporada(latitude);

        return this.getPlantCatalogRepository
                .getPlantsCatalogs(weather.getMinTemperature(),
                        weather.getMaxTemperature(),
                        luz,
                        temporada,
                        squareMeters);

    }

    private String getTemporada(Float latitude) {
        LocalDate fecha = LocalDate.now();
        Month mes = fecha.getMonth();
        if (latitude < 0) {
            return switch (mes) {
                case DECEMBER, JANUARY, FEBRUARY -> "Verano";
                case MARCH, APRIL, MAY -> "Otoño";
                case JUNE, JULY, AUGUST -> "Invierno";
                case SEPTEMBER, OCTOBER, NOVEMBER -> "Primavera";
                default -> "Desconocida";
            };
        } else {
            return "Desconocida";
        }
    }



    private String getSunLight(Integer sunExposure){
        if (sunExposure >= 1 && sunExposure <= 3) {
            return "sombra";
        } else if (sunExposure >= 4 && sunExposure <= 6) {
            return "sol parcial";
        } else if (sunExposure >= 7 && sunExposure <= 10) {
            return "sol pleno";
        } else {
            return "valor fuera de rango";
        }
    }
    // temperatura min y max
    // altura ?
    // campo: sun light
    // sombra
    // sol parcial
    // sol pleno
    // 1 3     4 6     7 10

    // campo: width   centimetros
    // 30         zapallo

    // campo: planting time
    // las 4 temporadas

}
