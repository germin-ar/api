update garden.plant
set alias            = :alias,
    favorite         = :is_favorite,
    id_garden        = :id_garden,
    height           = :height,
    notes            = :notes,
    --planting_date    = :planting_date,-->
    modification_date= now()
where plant.id = :id;


