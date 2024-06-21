insert into garden.plant_history(id_plant,
                                 notes,
                                 height,
                                 alias,
                                 url_image,
                                 modified_at,
                                 id_diseases)
values (:idPlant,
        :notes,
        :height,
        :alias,
        :urlImage,
        now(),
        :idDiseases)