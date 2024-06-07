insert into garden.garden (name,
                           id_user,
                           creation_date)
values (:name,
        :idUser,
        now());
