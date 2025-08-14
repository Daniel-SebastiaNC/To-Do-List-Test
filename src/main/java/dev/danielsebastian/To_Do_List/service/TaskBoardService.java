package dev.danielsebastian.To_Do_List.service;

import dev.danielsebastian.To_Do_List.dto.task_board.TaskBoardRequest;
import dev.danielsebastian.To_Do_List.dto.task_board.TaskBoardResponse;
import dev.danielsebastian.To_Do_List.exception.DataNotFoundException;
import dev.danielsebastian.To_Do_List.mapper.TaskBoardMapper;
import dev.danielsebastian.To_Do_List.model.Task;
import dev.danielsebastian.To_Do_List.model.TaskBoard;
import dev.danielsebastian.To_Do_List.repository.TaskBoardRepository;
import dev.danielsebastian.To_Do_List.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskBoardService {
    private final TaskBoardRepository taskBoardRepository;
    private final TaskRepository taskRepository;
    private final TaskBoardMapper taskBoardMapper;

    public TaskBoardResponse createTaskBoard(TaskBoardRequest taskBoardRequest) {
        TaskBoard taskBoard = taskBoardMapper.toTaskBoard(taskBoardRequest);

        List<Task> tasks = new ArrayList<>();

        if (taskBoardRequest.tasks() != null && !taskBoardRequest.tasks().isEmpty()) {
            for (UUID task : taskBoardRequest.tasks()) {
                Task taskFound = taskRepository.findById(task).orElseThrow(() -> new DataNotFoundException("Task not found"));

                taskFound.setTaskBoard(taskBoard);
                tasks.add(taskFound);
            }
        }

        taskBoard.setTasks(tasks);

        TaskBoard save = taskBoardRepository.save(taskBoard);

        return taskBoardMapper.toTaskBoardResponse(save);
    }

    public List<TaskBoardResponse> getAllTaskBoards(Pageable pageable) {
        Page<TaskBoard> all = taskBoardRepository.findAll(pageable);
        return all.stream().map(taskBoardMapper::toTaskBoardResponse).toList();
    }
}
