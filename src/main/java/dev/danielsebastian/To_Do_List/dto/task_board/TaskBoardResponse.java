package dev.danielsebastian.To_Do_List.dto.task_board;

import dev.danielsebastian.To_Do_List.dto.task.TaskResponse;

import java.util.List;
import java.util.UUID;

public record TaskBoardResponse(
        UUID id,
        String title,
        String status,
        List<TaskResponse> tasks
) {
}
