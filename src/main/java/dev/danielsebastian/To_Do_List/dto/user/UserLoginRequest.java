package dev.danielsebastian.To_Do_List.dto.user;

import jakarta.validation.constraints.Email;

public record UserLoginRequest(
        @Email
        String email,

        String password
) {
}
