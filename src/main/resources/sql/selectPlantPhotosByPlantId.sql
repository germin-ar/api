select id, id_plant, uploaded_at, url
from garden.plant_photo
where id_plant = :plantId;