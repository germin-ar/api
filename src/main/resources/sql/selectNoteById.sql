select note.id                id,
       note.observations      observations,
       users.id               user_id,
       plants.id              plant_id,
       note.creation_date     plant_creation_date,
       note.modification_date plant_modification_date
from garden.note note
         left join garden.plant plants on plants.id = note.plant_id
         left join garden.user users on users.id = note.user_id
where note.id = :id;