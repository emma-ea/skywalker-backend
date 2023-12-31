package io.github.emmaea.skywalker.controller;

import io.github.emmaea.skywalker.model.request.LoginRequest;
import io.github.emmaea.skywalker.model.request.SignUpRequest;
import io.github.emmaea.skywalker.model.response.ApiResponse;
import io.github.emmaea.skywalker.model.response.UserResponse;
import io.github.emmaea.skywalker.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/skywalker/")
public class UserController {

    private final Logger logger = Logger.getLogger(UserController.class.getName());

    private final UserService userService;

    public UserController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        ApiResponse<UserResponse> res = userService.login(request);
        if (res.getStatus().compareTo("SUCCESS") == 0) {
            return new ResponseEntity<>(userService.login(request), HttpStatus.OK);
        }
        ApiResponse<LoginRequest> ret = new ApiResponse<>("FAILED", "USER NOT FOUND", request, "null");
        return new ResponseEntity<>(ret, HttpStatus.NOT_FOUND);
    }


    @PostMapping("auth/signup")
    public ResponseEntity<ApiResponse<UserResponse>> signUp(@RequestBody SignUpRequest request) {
//        logger.log(Level.INFO, request.toString());
        ApiResponse<UserResponse> user = userService.signUp(request);
        if (user.getStatus().compareTo("FAILED") == 0) {
            return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    public void forgottenPassword() {}


    public void verifyEmail() {}

}
