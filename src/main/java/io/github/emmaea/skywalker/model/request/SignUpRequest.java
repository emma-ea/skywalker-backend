package io.github.emmaea.skywalker.model.request;

import io.github.emmaea.skywalker.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    @Email
    private String email;
    @NotEmpty
    private String password;
    private String countryCode;
    private String phone;
    private LocalDate dob;
}
