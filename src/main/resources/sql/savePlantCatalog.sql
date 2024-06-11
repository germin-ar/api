insert into garden.plant_catalog (scientific_name, description,slug_scientific_name, genus, family, average_size, fertilizer, irrigation, pruning, soil, insecticide, tips )
values (:scientific_name,
        :description,
        :slug_scientific_name,
        :genus,
        :family,
        :average_size,
        :fertilizer,
        :irrigation,
        :pruning,
        :soil,
        :insecticide,
        :tips
        );

-- //TODO: agregar los demas atributos que va a necesitar este plant catalog.
