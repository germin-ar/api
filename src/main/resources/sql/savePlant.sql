insert into garden.plant (name,
                          id_garden,
                          id_plants_catalog,
                          creation_date,
                          modification_date,
                          height,
                          notes,
                          planting_date)
values (:alias,
        :idGarden,
        :idPlantsCatalog,
        now(),
        now(),
        :height,
        :notes,
        :plantingDate);
