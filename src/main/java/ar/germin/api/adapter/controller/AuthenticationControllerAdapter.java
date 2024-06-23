package ar.germin.api.adapter.controller;


import ar.germin.api.application.port.in.UserConfirmRegistrationPortIn;
import ar.germin.api.application.port.in.UserLoginPortIn;
import ar.germin.api.application.port.in.UserLogoutPortIn;
import ar.germin.api.application.port.in.UserRegistrationPortIn;
import lombok.extern.slf4j.Slf4j;
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
  public String signUp(@RequestParam String username, @RequestParam String password, @RequestParam String email) {

    String response = userRegistrationPortIn.signUp(username, password, email);
    log.info("signupResponse: {}", response);
    return response;
  }

  @PostMapping("/confirm-signup")
  public String confirmSignUp(@RequestParam String email, @RequestParam String confirmationCode) {
    String response = userConfirmRegistrationPortIn.confirmSignUp(email, confirmationCode);
    log.info("confirmSignUpResponse: {}", response);
    return response;
  }

  @PostMapping("/login")
  public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
    Map<String, String> response = userLoginPortIn.login(username, password);
    log.info("loginResponse: {}", response);
    return response;
  }

  @PostMapping("/logout")
  public String logout(@RequestHeader("Authorization") String token) {
    //refreshToken
    // Asume que el token se pasa directamente sin el prefijo 'Bearer '
    return userLogoutPortIn.logout(token);
  }


}
