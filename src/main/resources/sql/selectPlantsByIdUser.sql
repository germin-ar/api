select id,
       alias,
       id_garden,
       id_plants_catalog,
       creation_date,
       modification_date,
       description,
       favorite,
       height,
       sun_exposure,
       notes,
       planting_date,
       user_id
from garden.plant plant
where user_id = :idUser
  and is_active
