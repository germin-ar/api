SELECT * FROM garden.plant_catalog
WHERE LOWER(garden.plant_catalog.scientific_name) LIKE LOWER(:scientificName);
