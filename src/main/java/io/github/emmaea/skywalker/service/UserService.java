package io.github.emmaea.skywalker.service;

import io.github.emmaea.skywalker.model.User;
import io.github.emmaea.skywalker.model.request.LoginRequest;
import io.github.emmaea.skywalker.model.request.SignUpRequest;
import io.github.emmaea.skywalker.model.response.ApiResponse;
import io.github.emmaea.skywalker.model.response.UserResponse;
import io.github.emmaea.skywalker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserService {

    private final Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Lazy AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public ApiResponse<UserResponse> login(LoginRequest login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword())
        );
        Optional<User> user = userRepository.findByEmail(login.getEmail());
        if (user.isEmpty()) {
           return new ApiResponse<>("LOGIN FAILED", "INVALID EMAIL OR PASSWORD", null, null);
        }
        String token = jwtService.generateToken(user.get());
        UserResponse dto = UserResponse.withToken(user.get(), token);
        return new ApiResponse<>("LOGIN SUCCESS", "FOUND USER", dto, null);
    }

    public ApiResponse<UserResponse> signUp(SignUpRequest request) {
        User newUser = User.from(request);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        logger.log(Level.INFO, newUser.toString());

        Optional<User> foundUserWithEmail = userRepository.findByEmail(newUser.getEmail());
        Optional<User> foundUserWithPhone = userRepository.findByCountryCodeAndPhone(newUser.getCountryCode(), newUser.getPhone());
        if (foundUserWithEmail.isPresent()) {
            // use exception classes
            return new ApiResponse<>("SIGNUP FAILED", "USER WITH EMAIL EXISTS", null, null);
        }
        if (foundUserWithPhone.isPresent()) {
            return new ApiResponse<>("SIGNUP FAILED", "USER WITH PHONE EXISTS", null, null);
        }

        userRepository.save(newUser);
        String token = jwtService.generateToken(newUser);
        UserResponse response = UserResponse.withToken(newUser, token);
        return new ApiResponse<>("SIGNUP SUCCESS", "USER CREATED", response, null);
    }

    public UserDetailsService userDetailsService() {
        return email -> userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
