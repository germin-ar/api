package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.UserModel;
import ar.germin.api.application.domain.User;
import ar.germin.api.application.exceptions.ErrorUserDeleteException;
import ar.germin.api.application.exceptions.ErrorUserSaveException;
import ar.germin.api.application.exceptions.ErrorUserUpdateException;
import ar.germin.api.application.exceptions.UserNotFoundException;
import ar.germin.api.application.port.out.DeleteUserRepository;
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
public class UserJdbcAdapter implements GetUserRepository, SaveUserRepository, UpdateUserRepository, DeleteUserRepository {

  private static final String GET_USER_BY_EMAIL_PATH = "sql/selectUserByEmail.sql";
  private static final String SAVE_USER_PATH = "sql/saveUser.sql";
  private static final String UPDATE_USER_PATH = "sql/updateUser.sql";
  private static final String DELETE_USER_PATH = "sql/deleteUser.sql";
  private static final String UPDATE_ROLE_USER_PATH = "sql/updateRoleUser.sql";

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final String selectUserByEmailSql;
  private final String saveUserSql;
  private final String updateUserSql;
  private final String deleteUserSql;
  private final String updateRoleUserSql;


  public UserJdbcAdapter(NamedParameterJdbcTemplate namedParameterJdbcTemplate, SqlReader sqlReader) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.selectUserByEmailSql = sqlReader.readSql(GET_USER_BY_EMAIL_PATH);
    this.saveUserSql = sqlReader.readSql(SAVE_USER_PATH);
    this.updateUserSql = sqlReader.readSql(UPDATE_USER_PATH);
    this.deleteUserSql = sqlReader.readSql(DELETE_USER_PATH);
    this.updateRoleUserSql = sqlReader.readSql(UPDATE_ROLE_USER_PATH);

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
      return userModel.toDomain();
    } catch (EmptyResultDataAccessException ex) {
      log.warn("User with email [{}] not found", email);
      throw new UserNotFoundException("User with email [{}] not found");
    }
  }


  @Override
  public void save(User user) {
    try {
      MapSqlParameterSource sqlParams = new MapSqlParameterSource()
              .addValue("username", user.getUsername())
              .addValue("email", user.getEmail())
              .addValue("isConfirmed", user.getIsConfirmed())
              .addValue("hash",user.getHash())
              .addValue("rol",user.getRol());
      //TODO : hacer un caso de uso para cambiar el rol al usuario.
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

  @Override
  public void changeRole(String email, String rol) {
    try {
      MapSqlParameterSource sqlParams = new MapSqlParameterSource()
              .addValue("email", email)
              .addValue("rol",rol);
      log.info("Changed role user with sql [{}] with params: [{}]", updateRoleUserSql, sqlParams);
      this.namedParameterJdbcTemplate.update(updateRoleUserSql, sqlParams);
    } catch (ErrorUserSaveException ex) {
      log.error("Error changing role user", ex);
      throw new ErrorUserUpdateException("No se pudo actualizar el usuario");
    }
  }

  @Override
  public void delete(String email) {
    try {
      MapSqlParameterSource sqlParams = new MapSqlParameterSource()
              .addValue("email", email);
      log.info("Deleting user with sql [{}] with params: [{}]", deleteUserSql, sqlParams);
      this.namedParameterJdbcTemplate.update(deleteUserSql, sqlParams);
    } catch (Exception ex) {
      log.error("Error deleting user", ex);
      throw new ErrorUserDeleteException("Could not delete the user");
    }
  }
}
