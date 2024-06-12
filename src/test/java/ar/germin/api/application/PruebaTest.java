package ar.germin.api.application;

import ar.germin.api.adapter.rest.models.WeatherResponseModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Base64;

class PruebaTest {

    @Test
    void testSarasa() throws IOException {

        /*URL url = new URL("https://iili.io/Jp8Xfku.png");
        File file = new File();
        FileUtils.copyURLToFile(url, File);*/

        String imageUrl = "https://iili.io/Jp8Xfku.png";

        URL url = new URL(imageUrl);
        BufferedImage img = ImageIO.read(url);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        byte[] bytes = baos.toByteArray();

        String base64 = Base64.getEncoder().encodeToString(bytes);

        System.out.println(base64);

    }

    @Test
    void pruebaInterpolation() {

        System.out.println(LocalDate.now().minusDays(300));

    }

    @Test
    void testObjectMapper() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        WeatherResponseModel response = objectMapper.readValue("{\n" +
                "    \"location\": {\n" +
                "        \"name\": \"Pontevedra\",\n" +
                "        \"region\": \"Buenos Aires\",\n" +
                "        \"country\": \"Argentina\",\n" +
                "        \"lat\": -34.83,\n" +
                "        \"lon\": -58.62,\n" +
                "        \"tz_id\": \"America/Argentina/Buenos_Aires\",\n" +
                "        \"localtime_epoch\": 1718161477,\n" +
                "        \"localtime\": \"2024-06-12 0:04\"\n" +
                "    },\n" +
                "    \"forecast\": {\n" +
                "        \"forecastday\": [\n" +
                "            {\n" +
                "                \"date\": \"2023-07-10\",\n" +
                "                \"date_epoch\": 1688947200,\n" +
                "                \"day\": {\n" +
                "                    \"maxtemp_c\": 15.6,\n" +
                "                    \"maxtemp_f\": 60.1,\n" +
                "                    \"mintemp_c\": 9.1,\n" +
                "                    \"mintemp_f\": 48.4,\n" +
                "                    \"avgtemp_c\": 11.8,\n" +
                "                    \"avgtemp_f\": 53.3,\n" +
                "                    \"maxwind_mph\": 8.3,\n" +
                "                    \"maxwind_kph\": 13.3,\n" +
                "                    \"totalprecip_mm\": 0.0,\n" +
                "                    \"totalprecip_in\": 0.0,\n" +
                "                    \"totalsnow_cm\": 0.0,\n" +
                "                    \"avgvis_km\": 9.8,\n" +
                "                    \"avgvis_miles\": 6.0,\n" +
                "                    \"avghumidity\": 79,\n" +
                "                    \"daily_will_it_rain\": 0,\n" +
                "                    \"daily_chance_of_rain\": 0,\n" +
                "                    \"daily_will_it_snow\": 0,\n" +
                "                    \"daily_chance_of_snow\": 0,\n" +
                "                    \"condition\": {\n" +
                "                        \"text\": \"Overcast\",\n" +
                "                        \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/122.png\",\n" +
                "                        \"code\": 1009\n" +
                "                    },\n" +
                "                    \"uv\": 5.0\n" +
                "                },\n" +
                "                \"astro\": {\n" +
                "                    \"sunrise\": \"08:01 AM\",\n" +
                "                    \"sunset\": \"05:59 PM\",\n" +
                "                    \"moonrise\": \"01:14 AM\",\n" +
                "                    \"moonset\": \"12:49 PM\",\n" +
                "                    \"moon_phase\": \"Last Quarter\",\n" +
                "                    \"moon_illumination\": 51\n" +
                "                },\n" +
                "                \"hour\": [\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1688958000,\n" +
                "                        \"time\": \"2023-07-10 00:00\",\n" +
                "                        \"temp_c\": 11.7,\n" +
                "                        \"temp_f\": 53.0,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Partly cloudy\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/116.png\",\n" +
                "                            \"code\": 1003\n" +
                "                        },\n" +
                "                        \"wind_mph\": 4.0,\n" +
                "                        \"wind_kph\": 6.5,\n" +
                "                        \"wind_degree\": 148,\n" +
                "                        \"wind_dir\": \"SSE\",\n" +
                "                        \"pressure_mb\": 1018.0,\n" +
                "                        \"pressure_in\": 30.06,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 87,\n" +
                "                        \"cloud\": 49,\n" +
                "                        \"feelslike_c\": 11.3,\n" +
                "                        \"feelslike_f\": 52.3,\n" +
                "                        \"windchill_c\": 11.3,\n" +
                "                        \"windchill_f\": 52.3,\n" +
                "                        \"heatindex_c\": 11.7,\n" +
                "                        \"heatindex_f\": 53.0,\n" +
                "                        \"dewpoint_c\": 10.5,\n" +
                "                        \"dewpoint_f\": 50.9,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 6.7,\n" +
                "                        \"gust_kph\": 10.8,\n" +
                "                        \"uv\": 1.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1688961600,\n" +
                "                        \"time\": \"2023-07-10 01:00\",\n" +
                "                        \"temp_c\": 11.4,\n" +
                "                        \"temp_f\": 52.5,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Clear\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/113.png\",\n" +
                "                            \"code\": 1000\n" +
                "                        },\n" +
                "                        \"wind_mph\": 2.7,\n" +
                "                        \"wind_kph\": 4.3,\n" +
                "                        \"wind_degree\": 174,\n" +
                "                        \"wind_dir\": \"S\",\n" +
                "                        \"pressure_mb\": 1018.0,\n" +
                "                        \"pressure_in\": 30.06,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 88,\n" +
                "                        \"cloud\": 21,\n" +
                "                        \"feelslike_c\": 11.6,\n" +
                "                        \"feelslike_f\": 52.8,\n" +
                "                        \"windchill_c\": 11.6,\n" +
                "                        \"windchill_f\": 52.8,\n" +
                "                        \"heatindex_c\": 11.4,\n" +
                "                        \"heatindex_f\": 52.5,\n" +
                "                        \"dewpoint_c\": 10.3,\n" +
                "                        \"dewpoint_f\": 50.5,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 4.5,\n" +
                "                        \"gust_kph\": 7.2,\n" +
                "                        \"uv\": 1.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1688965200,\n" +
                "                        \"time\": \"2023-07-10 02:00\",\n" +
                "                        \"temp_c\": 11.0,\n" +
                "                        \"temp_f\": 51.7,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Partly cloudy\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/116.png\",\n" +
                "                            \"code\": 1003\n" +
                "                        },\n" +
                "                        \"wind_mph\": 2.9,\n" +
                "                        \"wind_kph\": 4.7,\n" +
                "                        \"wind_degree\": 156,\n" +
                "                        \"wind_dir\": \"SSE\",\n" +
                "                        \"pressure_mb\": 1018.0,\n" +
                "                        \"pressure_in\": 30.05,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 88,\n" +
                "                        \"cloud\": 53,\n" +
                "                        \"feelslike_c\": 10.9,\n" +
                "                        \"feelslike_f\": 51.7,\n" +
                "                        \"windchill_c\": 10.9,\n" +
                "                        \"windchill_f\": 51.7,\n" +
                "                        \"heatindex_c\": 11.0,\n" +
                "                        \"heatindex_f\": 51.7,\n" +
                "                        \"dewpoint_c\": 9.9,\n" +
                "                        \"dewpoint_f\": 49.7,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 4.9,\n" +
                "                        \"gust_kph\": 7.9,\n" +
                "                        \"uv\": 1.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1688968800,\n" +
                "                        \"time\": \"2023-07-10 03:00\",\n" +
                "                        \"temp_c\": 10.7,\n" +
                "                        \"temp_f\": 51.2,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Clear\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/113.png\",\n" +
                "                            \"code\": 1000\n" +
                "                        },\n" +
                "                        \"wind_mph\": 2.9,\n" +
                "                        \"wind_kph\": 4.7,\n" +
                "                        \"wind_degree\": 180,\n" +
                "                        \"wind_dir\": \"S\",\n" +
                "                        \"pressure_mb\": 1018.0,\n" +
                "                        \"pressure_in\": 30.05,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 88,\n" +
                "                        \"cloud\": 17,\n" +
                "                        \"feelslike_c\": 10.6,\n" +
                "                        \"feelslike_f\": 51.1,\n" +
                "                        \"windchill_c\": 10.6,\n" +
                "                        \"windchill_f\": 51.1,\n" +
                "                        \"heatindex_c\": 10.7,\n" +
                "                        \"heatindex_f\": 51.2,\n" +
                "                        \"dewpoint_c\": 9.6,\n" +
                "                        \"dewpoint_f\": 49.3,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 4.7,\n" +
                "                        \"gust_kph\": 7.6,\n" +
                "                        \"uv\": 1.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1688972400,\n" +
                "                        \"time\": \"2023-07-10 04:00\",\n" +
                "                        \"temp_c\": 10.2,\n" +
                "                        \"temp_f\": 50.3,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Clear\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/113.png\",\n" +
                "                            \"code\": 1000\n" +
                "                        },\n" +
                "                        \"wind_mph\": 3.6,\n" +
                "                        \"wind_kph\": 5.8,\n" +
                "                        \"wind_degree\": 178,\n" +
                "                        \"wind_dir\": \"S\",\n" +
                "                        \"pressure_mb\": 1018.0,\n" +
                "                        \"pressure_in\": 30.05,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 90,\n" +
                "                        \"cloud\": 19,\n" +
                "                        \"feelslike_c\": 9.7,\n" +
                "                        \"feelslike_f\": 49.5,\n" +
                "                        \"windchill_c\": 9.7,\n" +
                "                        \"windchill_f\": 49.5,\n" +
                "                        \"heatindex_c\": 10.2,\n" +
                "                        \"heatindex_f\": 50.3,\n" +
                "                        \"dewpoint_c\": 9.3,\n" +
                "                        \"dewpoint_f\": 48.7,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 6.3,\n" +
                "                        \"gust_kph\": 10.1,\n" +
                "                        \"uv\": 1.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1688976000,\n" +
                "                        \"time\": \"2023-07-10 05:00\",\n" +
                "                        \"temp_c\": 10.1,\n" +
                "                        \"temp_f\": 50.1,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Clear\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/113.png\",\n" +
                "                            \"code\": 1000\n" +
                "                        },\n" +
                "                        \"wind_mph\": 4.3,\n" +
                "                        \"wind_kph\": 6.8,\n" +
                "                        \"wind_degree\": 157,\n" +
                "                        \"wind_dir\": \"SSE\",\n" +
                "                        \"pressure_mb\": 1017.0,\n" +
                "                        \"pressure_in\": 30.03,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 89,\n" +
                "                        \"cloud\": 16,\n" +
                "                        \"feelslike_c\": 9.3,\n" +
                "                        \"feelslike_f\": 48.8,\n" +
                "                        \"windchill_c\": 9.3,\n" +
                "                        \"windchill_f\": 48.8,\n" +
                "                        \"heatindex_c\": 10.1,\n" +
                "                        \"heatindex_f\": 50.1,\n" +
                "                        \"dewpoint_c\": 9.1,\n" +
                "                        \"dewpoint_f\": 48.4,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 7.4,\n" +
                "                        \"gust_kph\": 11.9,\n" +
                "                        \"uv\": 1.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1688979600,\n" +
                "                        \"time\": \"2023-07-10 06:00\",\n" +
                "                        \"temp_c\": 9.7,\n" +
                "                        \"temp_f\": 49.5,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Partly cloudy\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/116.png\",\n" +
                "                            \"code\": 1003\n" +
                "                        },\n" +
                "                        \"wind_mph\": 4.5,\n" +
                "                        \"wind_kph\": 7.2,\n" +
                "                        \"wind_degree\": 144,\n" +
                "                        \"wind_dir\": \"SE\",\n" +
                "                        \"pressure_mb\": 1017.0,\n" +
                "                        \"pressure_in\": 30.03,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 89,\n" +
                "                        \"cloud\": 45,\n" +
                "                        \"feelslike_c\": 8.8,\n" +
                "                        \"feelslike_f\": 47.9,\n" +
                "                        \"windchill_c\": 8.8,\n" +
                "                        \"windchill_f\": 47.9,\n" +
                "                        \"heatindex_c\": 9.7,\n" +
                "                        \"heatindex_f\": 49.5,\n" +
                "                        \"dewpoint_c\": 8.8,\n" +
                "                        \"dewpoint_f\": 47.8,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 7.8,\n" +
                "                        \"gust_kph\": 12.6,\n" +
                "                        \"uv\": 1.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1688983200,\n" +
                "                        \"time\": \"2023-07-10 07:00\",\n" +
                "                        \"temp_c\": 9.4,\n" +
                "                        \"temp_f\": 48.8,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Clear\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/113.png\",\n" +
                "                            \"code\": 1000\n" +
                "                        },\n" +
                "                        \"wind_mph\": 4.3,\n" +
                "                        \"wind_kph\": 6.8,\n" +
                "                        \"wind_degree\": 137,\n" +
                "                        \"wind_dir\": \"SE\",\n" +
                "                        \"pressure_mb\": 1017.0,\n" +
                "                        \"pressure_in\": 30.04,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 90,\n" +
                "                        \"cloud\": 17,\n" +
                "                        \"feelslike_c\": 8.5,\n" +
                "                        \"feelslike_f\": 47.3,\n" +
                "                        \"windchill_c\": 8.5,\n" +
                "                        \"windchill_f\": 47.3,\n" +
                "                        \"heatindex_c\": 9.4,\n" +
                "                        \"heatindex_f\": 48.8,\n" +
                "                        \"dewpoint_c\": 8.5,\n" +
                "                        \"dewpoint_f\": 47.2,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 7.4,\n" +
                "                        \"gust_kph\": 11.9,\n" +
                "                        \"uv\": 1.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1688986800,\n" +
                "                        \"time\": \"2023-07-10 08:00\",\n" +
                "                        \"temp_c\": 9.1,\n" +
                "                        \"temp_f\": 48.4,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Clear\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/113.png\",\n" +
                "                            \"code\": 1000\n" +
                "                        },\n" +
                "                        \"wind_mph\": 3.6,\n" +
                "                        \"wind_kph\": 5.8,\n" +
                "                        \"wind_degree\": 133,\n" +
                "                        \"wind_dir\": \"SE\",\n" +
                "                        \"pressure_mb\": 1018.0,\n" +
                "                        \"pressure_in\": 30.05,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 90,\n" +
                "                        \"cloud\": 16,\n" +
                "                        \"feelslike_c\": 8.5,\n" +
                "                        \"feelslike_f\": 47.3,\n" +
                "                        \"windchill_c\": 8.5,\n" +
                "                        \"windchill_f\": 47.3,\n" +
                "                        \"heatindex_c\": 9.1,\n" +
                "                        \"heatindex_f\": 48.4,\n" +
                "                        \"dewpoint_c\": 8.2,\n" +
                "                        \"dewpoint_f\": 46.8,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 6.3,\n" +
                "                        \"gust_kph\": 10.1,\n" +
                "                        \"uv\": 1.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1688990400,\n" +
                "                        \"time\": \"2023-07-10 09:00\",\n" +
                "                        \"temp_c\": 9.3,\n" +
                "                        \"temp_f\": 48.7,\n" +
                "                        \"is_day\": 1,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Sunny\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/113.png\",\n" +
                "                            \"code\": 1000\n" +
                "                        },\n" +
                "                        \"wind_mph\": 4.3,\n" +
                "                        \"wind_kph\": 6.8,\n" +
                "                        \"wind_degree\": 141,\n" +
                "                        \"wind_dir\": \"SE\",\n" +
                "                        \"pressure_mb\": 1018.0,\n" +
                "                        \"pressure_in\": 30.06,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 89,\n" +
                "                        \"cloud\": 20,\n" +
                "                        \"feelslike_c\": 8.5,\n" +
                "                        \"feelslike_f\": 47.2,\n" +
                "                        \"windchill_c\": 8.5,\n" +
                "                        \"windchill_f\": 47.2,\n" +
                "                        \"heatindex_c\": 9.3,\n" +
                "                        \"heatindex_f\": 48.7,\n" +
                "                        \"dewpoint_c\": 8.3,\n" +
                "                        \"dewpoint_f\": 46.9,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 9.5,\n" +
                "                        \"gust_kph\": 15.3,\n" +
                "                        \"uv\": 4.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1688994000,\n" +
                "                        \"time\": \"2023-07-10 10:00\",\n" +
                "                        \"temp_c\": 10.6,\n" +
                "                        \"temp_f\": 51.1,\n" +
                "                        \"is_day\": 1,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Sunny\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/113.png\",\n" +
                "                            \"code\": 1000\n" +
                "                        },\n" +
                "                        \"wind_mph\": 6.7,\n" +
                "                        \"wind_kph\": 10.8,\n" +
                "                        \"wind_degree\": 130,\n" +
                "                        \"wind_dir\": \"SE\",\n" +
                "                        \"pressure_mb\": 1018.0,\n" +
                "                        \"pressure_in\": 30.07,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 81,\n" +
                "                        \"cloud\": 16,\n" +
                "                        \"feelslike_c\": 9.2,\n" +
                "                        \"feelslike_f\": 48.6,\n" +
                "                        \"windchill_c\": 9.2,\n" +
                "                        \"windchill_f\": 48.6,\n" +
                "                        \"heatindex_c\": 10.6,\n" +
                "                        \"heatindex_f\": 51.1,\n" +
                "                        \"dewpoint_c\": 8.3,\n" +
                "                        \"dewpoint_f\": 46.9,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 11.0,\n" +
                "                        \"gust_kph\": 17.6,\n" +
                "                        \"uv\": 5.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1688997600,\n" +
                "                        \"time\": \"2023-07-10 11:00\",\n" +
                "                        \"temp_c\": 12.2,\n" +
                "                        \"temp_f\": 53.9,\n" +
                "                        \"is_day\": 1,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Partly cloudy\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/116.png\",\n" +
                "                            \"code\": 1003\n" +
                "                        },\n" +
                "                        \"wind_mph\": 7.2,\n" +
                "                        \"wind_kph\": 11.5,\n" +
                "                        \"wind_degree\": 125,\n" +
                "                        \"wind_dir\": \"SE\",\n" +
                "                        \"pressure_mb\": 1019.0,\n" +
                "                        \"pressure_in\": 30.08,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 74,\n" +
                "                        \"cloud\": 29,\n" +
                "                        \"feelslike_c\": 11.0,\n" +
                "                        \"feelslike_f\": 51.8,\n" +
                "                        \"windchill_c\": 11.0,\n" +
                "                        \"windchill_f\": 51.8,\n" +
                "                        \"heatindex_c\": 12.2,\n" +
                "                        \"heatindex_f\": 53.9,\n" +
                "                        \"dewpoint_c\": 8.5,\n" +
                "                        \"dewpoint_f\": 47.2,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 11.2,\n" +
                "                        \"gust_kph\": 18.0,\n" +
                "                        \"uv\": 5.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1689001200,\n" +
                "                        \"time\": \"2023-07-10 12:00\",\n" +
                "                        \"temp_c\": 13.4,\n" +
                "                        \"temp_f\": 56.1,\n" +
                "                        \"is_day\": 1,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Partly cloudy\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/116.png\",\n" +
                "                            \"code\": 1003\n" +
                "                        },\n" +
                "                        \"wind_mph\": 6.9,\n" +
                "                        \"wind_kph\": 11.2,\n" +
                "                        \"wind_degree\": 125,\n" +
                "                        \"wind_dir\": \"SE\",\n" +
                "                        \"pressure_mb\": 1019.0,\n" +
                "                        \"pressure_in\": 30.08,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 69,\n" +
                "                        \"cloud\": 55,\n" +
                "                        \"feelslike_c\": 12.5,\n" +
                "                        \"feelslike_f\": 54.6,\n" +
                "                        \"windchill_c\": 12.5,\n" +
                "                        \"windchill_f\": 54.6,\n" +
                "                        \"heatindex_c\": 13.4,\n" +
                "                        \"heatindex_f\": 56.1,\n" +
                "                        \"dewpoint_c\": 8.1,\n" +
                "                        \"dewpoint_f\": 46.6,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 11.2,\n" +
                "                        \"gust_kph\": 18.0,\n" +
                "                        \"uv\": 5.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1689004800,\n" +
                "                        \"time\": \"2023-07-10 13:00\",\n" +
                "                        \"temp_c\": 14.4,\n" +
                "                        \"temp_f\": 57.9,\n" +
                "                        \"is_day\": 1,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Cloudy\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/119.png\",\n" +
                "                            \"code\": 1006\n" +
                "                        },\n" +
                "                        \"wind_mph\": 7.4,\n" +
                "                        \"wind_kph\": 11.9,\n" +
                "                        \"wind_degree\": 114,\n" +
                "                        \"wind_dir\": \"ESE\",\n" +
                "                        \"pressure_mb\": 1018.0,\n" +
                "                        \"pressure_in\": 30.06,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 65,\n" +
                "                        \"cloud\": 76,\n" +
                "                        \"feelslike_c\": 13.7,\n" +
                "                        \"feelslike_f\": 56.6,\n" +
                "                        \"windchill_c\": 13.7,\n" +
                "                        \"windchill_f\": 56.6,\n" +
                "                        \"heatindex_c\": 14.4,\n" +
                "                        \"heatindex_f\": 57.9,\n" +
                "                        \"dewpoint_c\": 7.8,\n" +
                "                        \"dewpoint_f\": 46.0,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 11.5,\n" +
                "                        \"gust_kph\": 18.5,\n" +
                "                        \"uv\": 4.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1689008400,\n" +
                "                        \"time\": \"2023-07-10 14:00\",\n" +
                "                        \"temp_c\": 15.6,\n" +
                "                        \"temp_f\": 60.1,\n" +
                "                        \"is_day\": 1,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Cloudy\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/119.png\",\n" +
                "                            \"code\": 1006\n" +
                "                        },\n" +
                "                        \"wind_mph\": 7.2,\n" +
                "                        \"wind_kph\": 11.5,\n" +
                "                        \"wind_degree\": 109,\n" +
                "                        \"wind_dir\": \"ESE\",\n" +
                "                        \"pressure_mb\": 1017.0,\n" +
                "                        \"pressure_in\": 30.03,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 66,\n" +
                "                        \"cloud\": 74,\n" +
                "                        \"feelslike_c\": 15.6,\n" +
                "                        \"feelslike_f\": 60.1,\n" +
                "                        \"windchill_c\": 15.6,\n" +
                "                        \"windchill_f\": 60.1,\n" +
                "                        \"heatindex_c\": 15.6,\n" +
                "                        \"heatindex_f\": 60.1,\n" +
                "                        \"dewpoint_c\": 9.3,\n" +
                "                        \"dewpoint_f\": 48.7,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 11.3,\n" +
                "                        \"gust_kph\": 18.2,\n" +
                "                        \"uv\": 5.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1689012000,\n" +
                "                        \"time\": \"2023-07-10 15:00\",\n" +
                "                        \"temp_c\": 15.2,\n" +
                "                        \"temp_f\": 59.4,\n" +
                "                        \"is_day\": 1,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Overcast\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/122.png\",\n" +
                "                            \"code\": 1009\n" +
                "                        },\n" +
                "                        \"wind_mph\": 5.4,\n" +
                "                        \"wind_kph\": 8.6,\n" +
                "                        \"wind_degree\": 102,\n" +
                "                        \"wind_dir\": \"ESE\",\n" +
                "                        \"pressure_mb\": 1017.0,\n" +
                "                        \"pressure_in\": 30.03,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 68,\n" +
                "                        \"cloud\": 91,\n" +
                "                        \"feelslike_c\": 15.2,\n" +
                "                        \"feelslike_f\": 59.4,\n" +
                "                        \"windchill_c\": 15.2,\n" +
                "                        \"windchill_f\": 59.4,\n" +
                "                        \"heatindex_c\": 15.2,\n" +
                "                        \"heatindex_f\": 59.4,\n" +
                "                        \"dewpoint_c\": 9.4,\n" +
                "                        \"dewpoint_f\": 48.9,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 9.8,\n" +
                "                        \"gust_kph\": 15.8,\n" +
                "                        \"uv\": 5.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1689015600,\n" +
                "                        \"time\": \"2023-07-10 16:00\",\n" +
                "                        \"temp_c\": 14.4,\n" +
                "                        \"temp_f\": 57.9,\n" +
                "                        \"is_day\": 1,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Overcast\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/122.png\",\n" +
                "                            \"code\": 1009\n" +
                "                        },\n" +
                "                        \"wind_mph\": 5.6,\n" +
                "                        \"wind_kph\": 9.0,\n" +
                "                        \"wind_degree\": 101,\n" +
                "                        \"wind_dir\": \"ESE\",\n" +
                "                        \"pressure_mb\": 1017.0,\n" +
                "                        \"pressure_in\": 30.02,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 72,\n" +
                "                        \"cloud\": 94,\n" +
                "                        \"feelslike_c\": 14.0,\n" +
                "                        \"feelslike_f\": 57.2,\n" +
                "                        \"windchill_c\": 14.0,\n" +
                "                        \"windchill_f\": 57.2,\n" +
                "                        \"heatindex_c\": 14.4,\n" +
                "                        \"heatindex_f\": 57.9,\n" +
                "                        \"dewpoint_c\": 9.5,\n" +
                "                        \"dewpoint_f\": 49.1,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 7.4,\n" +
                "                        \"gust_kph\": 11.9,\n" +
                "                        \"uv\": 4.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1689019200,\n" +
                "                        \"time\": \"2023-07-10 17:00\",\n" +
                "                        \"temp_c\": 13.9,\n" +
                "                        \"temp_f\": 57.0,\n" +
                "                        \"is_day\": 1,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Overcast\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/day/122.png\",\n" +
                "                            \"code\": 1009\n" +
                "                        },\n" +
                "                        \"wind_mph\": 5.4,\n" +
                "                        \"wind_kph\": 8.6,\n" +
                "                        \"wind_degree\": 94,\n" +
                "                        \"wind_dir\": \"E\",\n" +
                "                        \"pressure_mb\": 1017.0,\n" +
                "                        \"pressure_in\": 30.03,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 73,\n" +
                "                        \"cloud\": 100,\n" +
                "                        \"feelslike_c\": 13.5,\n" +
                "                        \"feelslike_f\": 56.3,\n" +
                "                        \"windchill_c\": 13.5,\n" +
                "                        \"windchill_f\": 56.3,\n" +
                "                        \"heatindex_c\": 13.9,\n" +
                "                        \"heatindex_f\": 57.0,\n" +
                "                        \"dewpoint_c\": 9.2,\n" +
                "                        \"dewpoint_f\": 48.6,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 7.2,\n" +
                "                        \"gust_kph\": 11.5,\n" +
                "                        \"uv\": 4.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1689022800,\n" +
                "                        \"time\": \"2023-07-10 18:00\",\n" +
                "                        \"temp_c\": 13.4,\n" +
                "                        \"temp_f\": 56.1,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Partly cloudy\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/116.png\",\n" +
                "                            \"code\": 1003\n" +
                "                        },\n" +
                "                        \"wind_mph\": 6.7,\n" +
                "                        \"wind_kph\": 10.8,\n" +
                "                        \"wind_degree\": 91,\n" +
                "                        \"wind_dir\": \"E\",\n" +
                "                        \"pressure_mb\": 1017.0,\n" +
                "                        \"pressure_in\": 30.03,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 73,\n" +
                "                        \"cloud\": 60,\n" +
                "                        \"feelslike_c\": 12.6,\n" +
                "                        \"feelslike_f\": 54.6,\n" +
                "                        \"windchill_c\": 12.6,\n" +
                "                        \"windchill_f\": 54.6,\n" +
                "                        \"heatindex_c\": 13.4,\n" +
                "                        \"heatindex_f\": 56.1,\n" +
                "                        \"dewpoint_c\": 8.7,\n" +
                "                        \"dewpoint_f\": 47.7,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 8.9,\n" +
                "                        \"gust_kph\": 14.4,\n" +
                "                        \"uv\": 1.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1689026400,\n" +
                "                        \"time\": \"2023-07-10 19:00\",\n" +
                "                        \"temp_c\": 13.0,\n" +
                "                        \"temp_f\": 55.4,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Overcast\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/122.png\",\n" +
                "                            \"code\": 1009\n" +
                "                        },\n" +
                "                        \"wind_mph\": 6.3,\n" +
                "                        \"wind_kph\": 10.1,\n" +
                "                        \"wind_degree\": 105,\n" +
                "                        \"wind_dir\": \"ESE\",\n" +
                "                        \"pressure_mb\": 1018.0,\n" +
                "                        \"pressure_in\": 30.05,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 70,\n" +
                "                        \"cloud\": 100,\n" +
                "                        \"feelslike_c\": 12.2,\n" +
                "                        \"feelslike_f\": 54.0,\n" +
                "                        \"windchill_c\": 12.2,\n" +
                "                        \"windchill_f\": 54.0,\n" +
                "                        \"heatindex_c\": 13.0,\n" +
                "                        \"heatindex_f\": 55.4,\n" +
                "                        \"dewpoint_c\": 7.6,\n" +
                "                        \"dewpoint_f\": 45.7,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 8.5,\n" +
                "                        \"gust_kph\": 13.7,\n" +
                "                        \"uv\": 1.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1689030000,\n" +
                "                        \"time\": \"2023-07-10 20:00\",\n" +
                "                        \"temp_c\": 12.0,\n" +
                "                        \"temp_f\": 53.6,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Overcast\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/122.png\",\n" +
                "                            \"code\": 1009\n" +
                "                        },\n" +
                "                        \"wind_mph\": 6.9,\n" +
                "                        \"wind_kph\": 11.2,\n" +
                "                        \"wind_degree\": 105,\n" +
                "                        \"wind_dir\": \"ESE\",\n" +
                "                        \"pressure_mb\": 1019.0,\n" +
                "                        \"pressure_in\": 30.08,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 70,\n" +
                "                        \"cloud\": 100,\n" +
                "                        \"feelslike_c\": 10.9,\n" +
                "                        \"feelslike_f\": 51.5,\n" +
                "                        \"windchill_c\": 10.9,\n" +
                "                        \"windchill_f\": 51.5,\n" +
                "                        \"heatindex_c\": 12.0,\n" +
                "                        \"heatindex_f\": 53.6,\n" +
                "                        \"dewpoint_c\": 6.8,\n" +
                "                        \"dewpoint_f\": 44.2,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 9.8,\n" +
                "                        \"gust_kph\": 15.8,\n" +
                "                        \"uv\": 1.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1689033600,\n" +
                "                        \"time\": \"2023-07-10 21:00\",\n" +
                "                        \"temp_c\": 11.3,\n" +
                "                        \"temp_f\": 52.3,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Cloudy\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/119.png\",\n" +
                "                            \"code\": 1006\n" +
                "                        },\n" +
                "                        \"wind_mph\": 8.3,\n" +
                "                        \"wind_kph\": 13.3,\n" +
                "                        \"wind_degree\": 108,\n" +
                "                        \"wind_dir\": \"ESE\",\n" +
                "                        \"pressure_mb\": 1019.0,\n" +
                "                        \"pressure_in\": 30.08,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 71,\n" +
                "                        \"cloud\": 74,\n" +
                "                        \"feelslike_c\": 9.7,\n" +
                "                        \"feelslike_f\": 49.5,\n" +
                "                        \"windchill_c\": 9.7,\n" +
                "                        \"windchill_f\": 49.5,\n" +
                "                        \"heatindex_c\": 11.3,\n" +
                "                        \"heatindex_f\": 52.3,\n" +
                "                        \"dewpoint_c\": 6.3,\n" +
                "                        \"dewpoint_f\": 43.3,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 5.0,\n" +
                "                        \"vis_miles\": 3.0,\n" +
                "                        \"gust_mph\": 9.4,\n" +
                "                        \"gust_kph\": 15.1,\n" +
                "                        \"uv\": 1.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1689037200,\n" +
                "                        \"time\": \"2023-07-10 22:00\",\n" +
                "                        \"temp_c\": 11.1,\n" +
                "                        \"temp_f\": 52.0,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Cloudy\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/119.png\",\n" +
                "                            \"code\": 1006\n" +
                "                        },\n" +
                "                        \"wind_mph\": 7.4,\n" +
                "                        \"wind_kph\": 11.9,\n" +
                "                        \"wind_degree\": 118,\n" +
                "                        \"wind_dir\": \"ESE\",\n" +
                "                        \"pressure_mb\": 1019.0,\n" +
                "                        \"pressure_in\": 30.09,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 72,\n" +
                "                        \"cloud\": 71,\n" +
                "                        \"feelslike_c\": 9.7,\n" +
                "                        \"feelslike_f\": 49.4,\n" +
                "                        \"windchill_c\": 9.7,\n" +
                "                        \"windchill_f\": 49.4,\n" +
                "                        \"heatindex_c\": 11.1,\n" +
                "                        \"heatindex_f\": 52.0,\n" +
                "                        \"dewpoint_c\": 6.2,\n" +
                "                        \"dewpoint_f\": 43.2,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 10.3,\n" +
                "                        \"gust_kph\": 16.6,\n" +
                "                        \"uv\": 1.0\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"time_epoch\": 1689040800,\n" +
                "                        \"time\": \"2023-07-10 23:00\",\n" +
                "                        \"temp_c\": 11.0,\n" +
                "                        \"temp_f\": 51.8,\n" +
                "                        \"is_day\": 0,\n" +
                "                        \"condition\": {\n" +
                "                            \"text\": \"Cloudy\",\n" +
                "                            \"icon\": \"//cdn.weatherapi.com/weather/64x64/night/119.png\",\n" +
                "                            \"code\": 1006\n" +
                "                        },\n" +
                "                        \"wind_mph\": 7.2,\n" +
                "                        \"wind_kph\": 11.5,\n" +
                "                        \"wind_degree\": 119,\n" +
                "                        \"wind_dir\": \"ESE\",\n" +
                "                        \"pressure_mb\": 1019.0,\n" +
                "                        \"pressure_in\": 30.09,\n" +
                "                        \"precip_mm\": 0.0,\n" +
                "                        \"precip_in\": 0.0,\n" +
                "                        \"snow_cm\": 0.0,\n" +
                "                        \"humidity\": 75,\n" +
                "                        \"cloud\": 70,\n" +
                "                        \"feelslike_c\": 9.6,\n" +
                "                        \"feelslike_f\": 49.3,\n" +
                "                        \"windchill_c\": 9.6,\n" +
                "                        \"windchill_f\": 49.3,\n" +
                "                        \"heatindex_c\": 11.0,\n" +
                "                        \"heatindex_f\": 51.8,\n" +
                "                        \"dewpoint_c\": 6.8,\n" +
                "                        \"dewpoint_f\": 44.2,\n" +
                "                        \"will_it_rain\": 0,\n" +
                "                        \"chance_of_rain\": 0,\n" +
                "                        \"will_it_snow\": 0,\n" +
                "                        \"chance_of_snow\": 0,\n" +
                "                        \"vis_km\": 10.0,\n" +
                "                        \"vis_miles\": 6.0,\n" +
                "                        \"gust_mph\": 9.8,\n" +
                "                        \"gust_kph\": 15.8,\n" +
                "                        \"uv\": 1.0\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}", WeatherResponseModel.class);

        System.out.println(response);
    }
}
