package dev.danielsebastian.To_Do_List.dto.task;

import java.time.LocalDateTime;

public record TaskRequest(
        String title,
        String description,
        LocalDateTime deadline,
        String status,
        short priority
) {
}
