select id,
       description,
       scientific_name,
       slug_scientific_name slug,
       max_size,
       fertilizer,
       soil,
       sunlight,
       insecticide,
       temperature_max,
       temperature_min,
       watering_frecuency   watering_frequency,
       tips,
       harvest_time,
       growth_season,
       planting_time,
       pruning
from garden.plant_catalog
where lower(garden.plant_catalog.slug_scientific_name) like lower(:slugScientificName);
