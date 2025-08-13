package dev.danielsebastian.To_Do_List.service;

import dev.danielsebastian.To_Do_List.controller.TaskUpdateStatus;
import dev.danielsebastian.To_Do_List.dto.task.TaskRequest;
import dev.danielsebastian.To_Do_List.dto.task.TaskResponse;
import dev.danielsebastian.To_Do_List.dto.user.JWTUserData;
import dev.danielsebastian.To_Do_List.exception.DataNotFoundException;
import dev.danielsebastian.To_Do_List.mapper.TaskMapper;
import dev.danielsebastian.To_Do_List.model.Task;
import dev.danielsebastian.To_Do_List.model.User;
import dev.danielsebastian.To_Do_List.repository.TaskRepository;
import dev.danielsebastian.To_Do_List.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    private final UserRepository userRepository;

    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = taskMapper.toTask(taskRequest);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JWTUserData principal = (JWTUserData) auth.getPrincipal();

        User user = userRepository.findById(principal.id()).orElseThrow(() -> new DataNotFoundException("User not found"));

        task.setUsers(
                Arrays.asList(
                        user
                )
        );
        Task save = taskRepository.save(task);

        return taskMapper.toTaskResponse(save);
    }

    public List<TaskResponse> getAllTasks(Pageable pageable, String  status, Short priority, String deadline) {

        LocalDate deadlineDate = null;
        LocalDateTime startDay = null;
        LocalDateTime endDay = null;
        if (deadline != null && !deadline.isEmpty()) {
            deadlineDate = LocalDate.parse(deadline);

            startDay = deadlineDate.atStartOfDay();
            endDay = deadlineDate.plusDays(1).atStartOfDay();
        }

        Page<Task> all = taskRepository.findByFilters(status, priority, startDay, endDay, pageable);

        return all.stream().map(taskMapper::toTaskResponse).toList();
    }

    @Transactional
    public TaskResponse updateStatusTask(UUID id, TaskUpdateStatus taskUpdateStatus) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Task not found"));
        task.setStatus(taskUpdateStatus.status());
        Task save = taskRepository.save(task);
        return taskMapper.toTaskResponse(save);
    }

}
