select garden.id        id,
       garden.name      name
from garden.garden garden
where garden.id = :id and is_active;
