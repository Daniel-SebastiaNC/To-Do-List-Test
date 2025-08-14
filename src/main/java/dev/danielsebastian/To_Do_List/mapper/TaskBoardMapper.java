package dev.danielsebastian.To_Do_List.mapper;

import dev.danielsebastian.To_Do_List.dto.task_board.TaskBoardRequest;
import dev.danielsebastian.To_Do_List.dto.task_board.TaskBoardResponse;
import dev.danielsebastian.To_Do_List.model.TaskBoard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskBoardMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    TaskBoard toTaskBoard(TaskBoardRequest taskBoardRequest);

    TaskBoardResponse toTaskBoardResponse(TaskBoard taskBoard);
}
