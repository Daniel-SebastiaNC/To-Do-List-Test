package dev.danielsebastian.To_Do_List.service;

import dev.danielsebastian.To_Do_List.dto.task_board.TaskBoardRequest;
import dev.danielsebastian.To_Do_List.dto.task_board.TaskBoardResponse;
import dev.danielsebastian.To_Do_List.enums.ProgressStatus;
import dev.danielsebastian.To_Do_List.exception.DataNotFoundException;
import dev.danielsebastian.To_Do_List.exception.NeedCompledAllTasksException;
import dev.danielsebastian.To_Do_List.mapper.TaskBoardMapper;
import dev.danielsebastian.To_Do_List.model.Task;
import dev.danielsebastian.To_Do_List.model.TaskBoard;
import dev.danielsebastian.To_Do_List.repository.TaskBoardRepository;
import dev.danielsebastian.To_Do_List.repository.TaskRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public TaskBoardResponse createTaskBoard(TaskBoardRequest taskBoardRequest) {
        TaskBoard taskBoard = taskBoardMapper.toTaskBoard(taskBoardRequest);

        List<Task> tasks = new ArrayList<>();
        tasks = this.filterTasks(tasks, taskBoardRequest.tasks(), taskBoard);

        taskBoard.setTasks(tasks);

        TaskBoard save = taskBoardRepository.save(taskBoard);

        return taskBoardMapper.toTaskBoardResponse(save);
    }

    public List<TaskBoardResponse> getAllTaskBoards(Pageable pageable) {
        Page<TaskBoard> all = taskBoardRepository.findAll(pageable);
        return all.stream().map(taskBoardMapper::toTaskBoardResponse).toList();
    }

    @Transactional
    public TaskBoardResponse updateTaskBoard(UUID id, TaskBoardRequest taskBoardRequest) {
        TaskBoard taskBoardFound = taskBoardRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Task Board not found"));

        List<Task> tasks = taskBoardFound.getTasks();

        tasks = filterTasks(tasks, taskBoardRequest.tasks(), taskBoardFound);

        taskBoardFound.setTasks(tasks);
        taskBoardFound.setTitle(taskBoardRequest.title());


        if (ProgressStatus.valueOf(taskBoardRequest.status()) == ProgressStatus.COMPLETED) {
            this.tasksAreCompleted(taskBoardFound);
        }
        taskBoardFound.setStatus(ProgressStatus.valueOf(taskBoardRequest.status()));

        taskBoardRepository.save(taskBoardFound);
        return taskBoardMapper.toTaskBoardResponse(taskBoardFound);
    }

    private List<Task> filterTasks(List<Task> tasks, List<UUID> taskIds, TaskBoard taskBoard) {
        if (taskIds != null && !tasks.isEmpty()) {
            for (UUID task : taskIds) {
                Task taskFound = taskRepository.findById(task).orElseThrow(() -> new DataNotFoundException("Task not found"));

                if (tasks.contains(taskFound)) {
                    continue;
                }

                taskFound.setTaskBoard(taskBoard);
                tasks.add(taskFound);
            }
        } else {
            tasks.clear();
        }

        return tasks;
    }

    private void tasksAreCompleted(TaskBoard taskBoard) {
        for (Task task : taskBoard.getTasks()) {
            if (task.getStatus() != ProgressStatus.COMPLETED || task.getStatus() == ProgressStatus.CANCELED) {
                throw new NeedCompledAllTasksException("Some tasks were not completed or canceled");
            }
        }
    }
}
