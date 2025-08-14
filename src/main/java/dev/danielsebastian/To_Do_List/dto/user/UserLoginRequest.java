package dev.danielsebastian.To_Do_List.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserLoginRequest(
        @NotEmpty(message = "required")
        @Email(message = "must be a valid e-mail, ex: username@email.com")
        String email,

        @NotEmpty(message = "required")
        String password
) {
}
