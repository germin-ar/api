package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.UserModel;
import ar.germin.api.application.domain.User;
import ar.germin.api.application.exceptions.ErrorUserSaveException;
import ar.germin.api.application.exceptions.UserNotFoundException;
import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.SaveUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Qualifier("jdbc")
public class UserJdbcAdapter implements GetUserRepository, SaveUserRepository {

  private static final String GET_USER_BY_EMAIL_PATH = "sql/selectUserByEmail.sql";
  private static final String SAVE_USER_PATH = "sql/saveUser.sql";

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final String selectUserByEmailSql;
  private final String saveUserSql;


  public UserJdbcAdapter(NamedParameterJdbcTemplate namedParameterJdbcTemplate, SqlReader sqlReader) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.selectUserByEmailSql = sqlReader.readSql(GET_USER_BY_EMAIL_PATH);
    this.saveUserSql = sqlReader.readSql(SAVE_USER_PATH);

  }

  @Override
  public User get(String email) {
    try {
      MapSqlParameterSource params = new MapSqlParameterSource()
              .addValue("email", email);

      log.info("Querying User with sql [{}] with params: [{}]", selectUserByEmailSql, params);

      UserModel userModel = this.namedParameterJdbcTemplate.queryForObject(
              selectUserByEmailSql,
              params,
              new BeanPropertyRowMapper<>(UserModel.class));
      assert userModel != null;
      return userModel.toDomain();
    } catch (UserNotFoundException ex) {
      log.warn("User with email [{}] not found", email);
      throw new UserNotFoundException();
    } catch (Exception e) {
      log.error("Error querying user with email [{}]", email, e);
      throw e;
    }
  }


  @Override
  public void save(User user) {
    try {
      MapSqlParameterSource sqlParams = new MapSqlParameterSource()
              .addValue("username", user.getUsername())
              .addValue("email", user.getEmail())
              .addValue("isConfirmed", user.getIsConfirmed());
      log.info("Saving user with sql [{}] with params: [{}]", saveUserSql, sqlParams);
      this.namedParameterJdbcTemplate.update(saveUserSql, sqlParams);
    } catch (ErrorUserSaveException ex) {
      log.error("Error saving user", ex);
      throw new ErrorUserSaveException("No se pudo guardar el usuario");
    }
  }
}
