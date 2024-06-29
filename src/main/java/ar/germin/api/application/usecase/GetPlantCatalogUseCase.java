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

    @Override
    public List<PlantCatalog> getPlantsCatalogPlace(String luz, String temporada, String espacio) {
        String light = this.getLightConvert(luz);
        Integer place = this.getPlaceConvertInt(espacio);
        return this.getPlantCatalogRepository.getPlantsCatalogsPlace(light, place, temporada);
    }

    private String getLightConvert(String luz) {
        return switch (luz) {
            case "mas-4hs-sol" -> "sol parcial";
            case "sol-pleno" -> "sol pleno";
            case "sin-luz" -> "sombra";
            default -> "Desconocida";
        };
    }

    private Integer getPlaceConvertInt(String espacio){
        return switch (espacio){
            case "patio" -> 33;
            case "baño" -> 30;
            default -> 20;
        };
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


}
