select garden.id        id,
       garden.name      name,
       garden.id_user   id_user
from garden.garden garden
where garden.id = :id and is_active;
