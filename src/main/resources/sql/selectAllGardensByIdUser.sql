select garden.id                garden_id,
       garden.name              garden_name,
       garden.is_active         garden_is_active,
       garden_user.id           user_id,
       garden_user.email        user_email,
       garden_user.name         user_name,
       plants.alias             plant_name,
       plants.id                plant_id,
       plants.creation_date     plant_creation_date,
       plants.modification_date plant_modification_date,
       plants.is_active         plant_is_active



from garden.plant plants
    left join garden.garden garden on plants.id_garden = garden.id
    left join garden.user garden_user on garden_user.id = garden.id_user
where garden_user.id = :idUser
   OR garden_user.id IS NULL;;