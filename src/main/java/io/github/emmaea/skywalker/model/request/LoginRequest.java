package io.github.emmaea.skywalker.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequest {
    private String password;
    private String email;
    // TODO: Optional login with phone number
}
