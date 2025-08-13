package dev.danielsebastian.To_Do_List.dto.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskRequest(
        String title,
        String description,
        LocalDateTime deadline,
        String status,
        short priority,
        List<UUID> users
) {
}
