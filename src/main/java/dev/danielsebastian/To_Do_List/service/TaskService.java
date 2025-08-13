package dev.danielsebastian.To_Do_List.service;

import dev.danielsebastian.To_Do_List.dto.task.TaskRequest;
import dev.danielsebastian.To_Do_List.dto.task.TaskResponse;
import dev.danielsebastian.To_Do_List.dto.user.JWTUserData;
import dev.danielsebastian.To_Do_List.mapper.TaskMapper;
import dev.danielsebastian.To_Do_List.model.Task;
import dev.danielsebastian.To_Do_List.model.User;
import dev.danielsebastian.To_Do_List.repository.TaskRepository;
import dev.danielsebastian.To_Do_List.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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

        User user = userRepository.findById(principal.id()).orElseThrow(() -> new RuntimeException("errou"));

        task.setUsers(
                Arrays.asList(
                        user
                )
        );
        Task save = taskRepository.save(task);

        return taskMapper.toTaskResponse(save);
    }

    /*public List<TaskResponse> getAllTasks() {
        List<Task> all = taskRepository.findAll();

    }*/


}
