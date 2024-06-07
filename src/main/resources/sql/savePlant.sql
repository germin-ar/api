insert into garden.plant (alias,
                          id_garden,
                          user_id,
                          id_plants_catalog,
                          creation_date,
                          modification_date,
                          height,
                          notes,
                          planting_date)
values (:alias,
        :idGarden,
        :idUser,
        :idPlantsCatalog,
        now(),
        now(),
        :height,
        :notes,
        :plantingDate);
