package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.UserModel;
import ar.germin.api.application.domain.User;
import ar.germin.api.application.exceptions.ErrorUserSaveException;
import ar.germin.api.application.exceptions.ErrorUserUpdateException;
import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.SaveUserRepository;
import ar.germin.api.application.port.out.UpdateUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@Qualifier("jdbc")
public class UserJdbcAdapter implements GetUserRepository, SaveUserRepository, UpdateUserRepository {

  private static final String GET_USER_BY_EMAIL_PATH = "sql/selectUserByEmail.sql";
  private static final String SAVE_USER_PATH = "sql/saveUser.sql";
  private static final String UPDATE_USER_PATH = "sql/updateUser.sql";


  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final String selectUserByEmailSql;
  private final String saveUserSql;
  private final String updateUserSql;


  public UserJdbcAdapter(NamedParameterJdbcTemplate namedParameterJdbcTemplate, SqlReader sqlReader) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.selectUserByEmailSql = sqlReader.readSql(GET_USER_BY_EMAIL_PATH);
    this.saveUserSql = sqlReader.readSql(SAVE_USER_PATH);
    this.updateUserSql = sqlReader.readSql(UPDATE_USER_PATH);
  }

  @Override
  public Optional<User> get(String email) {
    try {
      MapSqlParameterSource params = new MapSqlParameterSource()
              .addValue("email", email);

      log.info("Querying User with sql [{}] with params: [{}]", selectUserByEmailSql, params);

      UserModel userModel = this.namedParameterJdbcTemplate.queryForObject(
              selectUserByEmailSql,
              params,
              new BeanPropertyRowMapper<>(UserModel.class));
      return Optional.of(userModel.toDomain());
    } catch (EmptyResultDataAccessException ex) {
      log.warn("User with email [{}] not found", email);
      return Optional.empty();
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

  @Override
  public void update(String email) {
    try {
      MapSqlParameterSource sqlParams = new MapSqlParameterSource()
              .addValue("email", email)
              .addValue("isConfirmed", true);
      log.info("Confirmed email user with sql [{}] with params: [{}]", updateUserSql, sqlParams);
      this.namedParameterJdbcTemplate.update(updateUserSql, sqlParams);
    } catch (ErrorUserSaveException ex) {
      log.error("Error confirm email user", ex);
      throw new ErrorUserUpdateException("No se pudo actualizar el usuario");
    }
  }
}
