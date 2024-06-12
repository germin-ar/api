select plant.id                plant_id,
       plant.alias             plant_alias,
       plant.id_garden         plant_id_garden,
       plant.modification_date plant_modification_date,
       plant.description       plant_description,
       plant.favorite          plant_favorite,
       plant.height            plant_height,
       plant.notes             plant_notes,
       plant.planting_date     plant_planting_date,
       pc.family               pc_family,
       pc.genus                pc_genus,
       pc.irrigation           pc_irrigation,
       pc.description          pc_description
from garden.plant plant
         inner join garden.plant_catalog pc
                   on plant.id_plants_catalog = pc.id
where plant.is_active
  and plant.user_id = :idUser
  and plant.id = :idPlant
