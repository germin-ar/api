package ar.germin.api.adapter.controller;


import ar.germin.api.adapter.controller.models.LogoutResponse;
import ar.germin.api.adapter.controller.models.UserResponseModel;
import ar.germin.api.application.port.in.UserConfirmRegistrationPortIn;
import ar.germin.api.application.port.in.UserLoginPortIn;
import ar.germin.api.application.port.in.UserLogoutPortIn;
import ar.germin.api.application.port.in.UserRegistrationPortIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationControllerAdapter {

  private final UserRegistrationPortIn userRegistrationPortIn;
  private final UserConfirmRegistrationPortIn userConfirmRegistrationPortIn;
  private final UserLoginPortIn userLoginPortIn;
  private final UserLogoutPortIn userLogoutPortIn;

  public AuthenticationControllerAdapter(UserRegistrationPortIn userRegistrationPortIn,
                                         UserConfirmRegistrationPortIn userConfirmRegistrationPortIn,
                                         UserLoginPortIn userLoginPortIn, UserLogoutPortIn userLogoutPortIn) {
    this.userRegistrationPortIn = userRegistrationPortIn;
    this.userConfirmRegistrationPortIn = userConfirmRegistrationPortIn;
    this.userLoginPortIn = userLoginPortIn;
    this.userLogoutPortIn = userLogoutPortIn;
  }


  @PostMapping("/signup")
  public UserResponseModel signUp(@RequestParam String username, @RequestParam String password, @RequestParam String email) throws Exception {
    return UserResponseModel.fromDomain(userRegistrationPortIn.signUp(username, password, email));
  }

  @PostMapping("/confirm-signup")
  public UserResponseModel confirmSignUp(@RequestParam String email, @RequestParam String confirmationCode) {
    return UserResponseModel.fromDomain(userConfirmRegistrationPortIn.confirmSignUp(email,confirmationCode));
  }

  @PostMapping("/login")
  public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
    Map<String, String> response = userLoginPortIn.login(username, password);
    log.info("loginResponse: {}", response);
    return response;
  }

  @PostMapping("/logout")
  public ResponseEntity<LogoutResponse> logout(@RequestHeader("Authorization") String token) {
    // Asume que el token se pasa directamente sin el prefijo 'Bearer '

    LogoutResponse logoutResponse =userLogoutPortIn.logout(token);
    if (logoutResponse.getIsLogout()){
      return ResponseEntity.ok(logoutResponse);
    }
    return ResponseEntity.internalServerError().body(logoutResponse);
  }

}
