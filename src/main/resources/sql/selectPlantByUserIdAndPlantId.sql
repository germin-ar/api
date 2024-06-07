select plant.id plant_id,
       plant.alias plant_alias,
       plant.id_garden plant_id_garden,
       plant.modification_date plant_modification_date,
       plant.description plant_description,
       plant.favorite plant_favorite,
       plant.height plant_height,
       plant.notes plant_notes,
       plant.planting_date plant_planting_date
from garden.plant plant
where plant.is_active = true
and plant.user_id = :idUser
and plant.id = :idPlant