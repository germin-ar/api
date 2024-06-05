select note.id                garden_id,
       note.observations      observations,
       users.id               user_id,
       plants.id              plant_id,
       note.creation_date     plant_creation_date,
       note.modification_date plant_modification_date
from note.note note
         left join plant.plant plants on plants.id = note.plant_id
         left join user.user users on user.id = note.user_id
where note.id = :id;