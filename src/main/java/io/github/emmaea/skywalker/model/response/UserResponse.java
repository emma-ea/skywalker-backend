package io.github.emmaea.skywalker.model.response;

import io.github.emmaea.skywalker.model.User;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String token;

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getEmail(),
                null
        );
    }

    public static UserResponse withToken(User user, String token) {
        return new UserResponse(
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getEmail(),
                token
        );
    }
}
