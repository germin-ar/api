select garden.id   id,
       garden.name name
from garden.garden garden
where garden.id_user = :idUser
  and is_active
