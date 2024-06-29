UPDATE garden.user
SET is_confirmed = :isConfirmed
WHERE email = :email;
