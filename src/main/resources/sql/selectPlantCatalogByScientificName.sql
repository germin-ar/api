SELECT *
FROM garden.plant_catalog
WHERE LOWER(garden.plant_catalog.slug_scientific_name) LIKE LOWER(:scientificName);
