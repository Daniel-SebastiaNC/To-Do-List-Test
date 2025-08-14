package dev.danielsebastian.To_Do_List.dto.task_board;

import dev.danielsebastian.To_Do_List.dto.task.TaskResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.UUID;

public record TaskBoardRequest(
        @NotEmpty(message = "required")
        String title,

        @NotEmpty(message = "required")
        @Pattern(
                regexp = "NOT_STARTED|IN_PROGRESS|COMPLETED|CANCELED",
                message = "Invalid status, please use: NOT_STARTED|IN_PROGRESS|COMPLETED|CANCELED"
        )
        String status,
        List<UUID> tasks
) {
}
