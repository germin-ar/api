update garden.plant
set alias = :alias,
    favorite = :favorite,
    id_garden = :idGarden,
    height = :height,
    notes =:notes,
    planting_date = :plantingDate
where plant.id= :id;


