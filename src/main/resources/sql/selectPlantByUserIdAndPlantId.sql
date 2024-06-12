select id                id,
       alias             alias,
       id_garden         id_garden,
       modification_date modification_date,
       description       description,
       favorite          favorite,
       height            height,
       notes             notes,
       planting_date     planting_date
from garden.plant
where plant.is_active
  and plant.user_id = :idUser
  and plant.id = :idPlant
