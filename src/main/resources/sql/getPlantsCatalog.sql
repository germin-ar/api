select id,
       scientific_name,
       description,
       slug_scientific_name,
       genus,
       family_name,
       max_size,
       fertilizer,
       watering_frecuency,
       pruning,
       soil,
       insecticide,
       tips,
       sunlight,
       watering_care,
       common_name,
       lifespan,
       propagation,
       fruit,
       edible,
       growth_rate,
       maintenance,
       temperature_max,
       temperature_min,
       specie,
       toxic,
       repotting,
       dormancy,
       growth_season,
       atmospheric_humidity,
       planting_time,
       harvest_time,
       plant_type,
       width,
       url_image
from garden.plant_catalog
where temperature_min <= :minTemperature
and temperature_max >= :maxTemperature
and planting_time ilike '%' || :temporada || '%'
and sunlight ilike '%' || :luz || '%'
and width >= :squareCentimeters;
