select id,
       name,
       email,
       is_confirmed,
       username,
       rol
from garden.user
where hash = :hash;
