package dev.danielsebastian.To_Do_List.dto.task;

import dev.danielsebastian.To_Do_List.dto.user.UserResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskResponse(
        UUID id,
        String title,
        String description,
        LocalDateTime deadline,
        String status,
        short priority,
        List<UserResponse> users
) {
}
