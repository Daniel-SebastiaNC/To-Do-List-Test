package dev.danielsebastian.To_Do_List.dto.task;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskRequest(
        @NotEmpty(message = "required")
        String title,

        String description,

        @NotNull(message = "required")
        @Future(message = "deadline must be future")
        LocalDateTime deadline,

        @NotEmpty(message = "required")
        @Pattern(
                regexp = "NOT_STARTED|IN_PROGRESS|COMPLETED|CANCELED",
                message = "Invalid status, please use: NOT_STARTED|IN_PROGRESS|COMPLETED|CANCELED"
        )
        String status,

        short priority,

        List<UUID> users
) {
}
