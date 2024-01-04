package io.github.emmaea.skywalker.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.github.emmaea.skywalker.model.User;
import io.github.emmaea.skywalker.model.UserRole;
import io.github.emmaea.skywalker.model.request.LoginRequest;
import io.github.emmaea.skywalker.model.response.ApiResponse;
import io.github.emmaea.skywalker.model.response.UserResponse;
import io.github.emmaea.skywalker.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setEmail("johndoe@gmail.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhone("540650527");
        user.setRole(UserRole.USER);
        user.setCountryCode("+233");
        user.setDob(LocalDate.now());
        user.setFirstSignUp(LocalDateTime.now());
        user.setPassword("JOHNdoe1");
    }

    @Test
    void givenLoginRequest_logsInAnd_returnssuccess() {

        LoginRequest loginRequest = new LoginRequest("JOHNdoe1", "johndoe@gmail.com", null);

        BDDMockito.given(userRepository.findByEmail(loginRequest.getEmail())).willReturn(Optional.of(user));

        BDDMockito.given(jwtService.generateToken(user)).willReturn("token");

        BDDMockito.given(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), 
                loginRequest.getPassword()))
            ).willReturn(null);

       ApiResponse<UserResponse> response =  userService.login(loginRequest);

       assertTrue(response.getStatus().contains("SUCCESS"));

    }


    @Test
    void givenLoginRequest_withInvalidCreds_logsInAnd_returnsFailed() {

        LoginRequest loginRequest = new LoginRequest("JOHN", "john@gmail.com", null);

        BDDMockito.given(userRepository.findByEmail(loginRequest.getEmail())).willReturn(Optional.of(user));

        BDDMockito.given(jwtService.generateToken(user)).willReturn("token");

        BDDMockito.given(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), 
                loginRequest.getPassword()))
            ).willReturn(null);

       ApiResponse<UserResponse> response =  userService.login(loginRequest);

       assertNotEquals(response.getData().getEmail(), loginRequest.getEmail());

    }

    @Test
    void testSignUp() {

    }
}
