package io.github.emmaea.skywalker.model.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;
    private String error;
}
