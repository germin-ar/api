package ar.germin.api.adapter.controller;


import ar.germin.api.application.port.in.GetCandidatesPlantsPortIn;
import ar.germin.api.application.port.in.UserRegistrationPortIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationControllerAdapter {

  private final UserRegistrationPortIn userRegistrationPortIn;

  public AuthenticationControllerAdapter(UserRegistrationPortIn userRegistrationPortIn) {
    this.userRegistrationPortIn = userRegistrationPortIn;
  }


  @PostMapping("/signup")
  public String signUp(@RequestParam String username, @RequestParam String password, @RequestParam String email) {

    String response = userRegistrationPortIn.signUp(username, password, email);
    log.info("signupResponse: {}", response);
    return response;
  }

}
