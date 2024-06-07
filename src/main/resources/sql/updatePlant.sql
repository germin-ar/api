update garden.plant
set alias = :alias,
    favorite = :isFavorite,
    id_garden = :idGarden,
    height = :height,
    notes =:notes,
    planting_date = :plantingDate,
    is_active = :isActive
where plant.id= :id;


