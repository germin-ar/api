update garden.plant
set alias = :alias,
    favorite = :favorite,
    id_garden = :idGarden,
    height = :height,
    notes =:notes
where plant.id= :id;


