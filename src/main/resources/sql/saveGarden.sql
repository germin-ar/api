insert into garden.garden (name,
                           id_user,
                           creation_date,
                           is_active)
values (:name,
        :idUser,
        now(),
        true);
