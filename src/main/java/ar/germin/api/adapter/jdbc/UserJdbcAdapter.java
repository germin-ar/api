package ar.germin.api.adapter.jdbc;

import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.SaveUserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("jdbc")
public class UserJdbcAdapter implements GetUserRepository, SaveUserRepository {
}
