select garden.id                garden_id,
       garden.name              garden_name,
       garden_user.id           user_id,
       garden_user.email        user_email,
       garden_user.name         user_name,
       plants.name              plant_name,
       plants.id                plant_id,
       plants.creation_date     plant_creation_date,
       plants.modification_date plant_modification_date
from garden.garden garden
         left join garden.user garden_user on garden_user.id = garden.id_user
         left join garden.plant plants on garden.id = plants.id_garden
where garden_user.id = :idUser;