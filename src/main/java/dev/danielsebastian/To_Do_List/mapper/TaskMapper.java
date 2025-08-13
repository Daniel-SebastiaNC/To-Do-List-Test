package dev.danielsebastian.To_Do_List.mapper;

import dev.danielsebastian.To_Do_List.dto.task.TaskRequest;
import dev.danielsebastian.To_Do_List.dto.task.TaskResponse;
import dev.danielsebastian.To_Do_List.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Task toTask(TaskRequest taskRequest);

    TaskResponse toTaskResponse(Task task);

}
