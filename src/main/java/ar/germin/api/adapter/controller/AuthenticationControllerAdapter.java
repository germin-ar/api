package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.ConfirmSignupRequestModel;
import ar.germin.api.adapter.controller.models.LoginUserRequestModel;
import ar.germin.api.adapter.controller.models.LoginUserResponseModel;
import ar.germin.api.adapter.controller.models.SignupRequestModel;
import ar.germin.api.adapter.controller.models.UserResponseModel;
import ar.germin.api.application.domain.User;
import ar.germin.api.application.domain.UserSessionTokens;
import ar.germin.api.application.port.in.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationControllerAdapter {

    private final UserRegistrationPortIn userRegistrationPortIn;
    private final UserConfirmRegistrationPortIn userConfirmRegistrationPortIn;
    private final UserLoginPortIn userLoginPortIn;
    private final UserLogoutPortIn userLogoutPortIn;
    private final getUserPortIn getUserPortIn;

    public AuthenticationControllerAdapter(UserRegistrationPortIn userRegistrationPortIn,
                                           UserConfirmRegistrationPortIn userConfirmRegistrationPortIn,
                                           UserLoginPortIn userLoginPortIn, UserLogoutPortIn userLogoutPortIn, ar.germin.api.application.port.in.getUserPortIn getUserPortIn) {
        this.userRegistrationPortIn = userRegistrationPortIn;
        this.userConfirmRegistrationPortIn = userConfirmRegistrationPortIn;
        this.userLoginPortIn = userLoginPortIn;
        this.userLogoutPortIn = userLogoutPortIn;
        this.getUserPortIn = getUserPortIn;
    }


    @PostMapping("/signup")
    public UserResponseModel signUp(@RequestBody SignupRequestModel signupRequestModel) throws Exception {
        return UserResponseModel.fromDomain(userRegistrationPortIn.signUp(
                signupRequestModel.username(),
                signupRequestModel.password(),
                signupRequestModel.email()));
    }

    @PostMapping("/confirm-signup")
    public UserResponseModel confirmSignUp(@RequestBody ConfirmSignupRequestModel signupRequestModel) {
        return UserResponseModel.fromDomain(userConfirmRegistrationPortIn.confirmSignUp(signupRequestModel.email(), signupRequestModel.confirmationCode()));
    }

    @PostMapping("/login")
    public LoginUserResponseModel login(@RequestBody LoginUserRequestModel loginUserRequestModel) {
        UserSessionTokens response = userLoginPortIn.login(
                loginUserRequestModel.email(),
                loginUserRequestModel.password());
        log.info("loginResponse: {}", response);
        return LoginUserResponseModel.fromDomain(response);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String token) {
        userLogoutPortIn.logout(token);
    }

    @PostMapping("/get")
    public UserResponseModel getUser(@RequestHeader("sub") String sub){
        User response = getUserPortIn.getByHash(sub);
        return UserResponseModel.fromDomain(response);
    }

}
