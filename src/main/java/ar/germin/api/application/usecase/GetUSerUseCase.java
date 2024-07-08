package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.User;
import ar.germin.api.application.port.in.getUserPortIn;
import ar.germin.api.application.port.out.GetUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GetUSerUseCase implements getUserPortIn {
  private final GetUserRepository getUserJdbcRepository;
  private final GetUserRepository GetUserCognitoRepository;

  public GetUSerUseCase(@Qualifier("jdbc") GetUserRepository getUserJdbcRepository,
                        @Qualifier("cognito")GetUserRepository getUserCognitoRepository) {
    this.getUserJdbcRepository = getUserJdbcRepository;
    this.GetUserCognitoRepository = getUserCognitoRepository;
  }


  @Override
  public User getByHash(String sub) {
    return this.GetUserCognitoRepository.getByHash(sub);
  }
}
