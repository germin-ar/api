select plant.id                plant_id,
       plant.alias             plant_alias,
       plant.id_garden         plant_id_garden,
       plant.modification_date plant_modification_date,
       plant.description       plant_description,
       plant.favorite          plant_favorite,
       plant.height            plant_height,
       plant.notes             plant_notes,
       plant.planting_date     plant_planting_date,
       pc.scientific_name      pc_scientific_name,
       pc.common_name          pc_common_name,
       pc.family_name          pc_family_name,
       pc.genus                pc_genus,
       pc.watering_frecuency   pc_watering_frecuency,
       pc.description          pc_description,
       pc.sunlight             pc_sunligt,
plant.id_plants_catalog plant_idcatalog
from garden.plant plant
         inner join garden.plant_catalog pc
                    on plant.id_plants_catalog = pc.id
where plant.is_active
  and plant.user_id = :idUser
  and plant.id = :idPlant