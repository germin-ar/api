select garden.id                garden_id,
       garden.name              garden_name,
       garden.is_active         garden_is_active,
       garden_user.id           user_id
from garden.garden garden
         join garden.user garden_user on garden_user.id = garden.id_user
where garden_user.id = :idUser;
