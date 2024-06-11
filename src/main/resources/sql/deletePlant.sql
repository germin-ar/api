update garden.plant
set is_active         = false,
    modification_date = now()
where id = :id;

