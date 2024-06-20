select id, id_plant, notes, height, alias, url_image, modified_at, id_diseases
from garden.plant_history
where id_plant = :idPlant