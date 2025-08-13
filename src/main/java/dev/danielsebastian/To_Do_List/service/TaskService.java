package dev.danielsebastian.To_Do_List.service;

import dev.danielsebastian.To_Do_List.dto.task.TaskRequest;
import dev.danielsebastian.To_Do_List.dto.task.TaskResponse;
import dev.danielsebastian.To_Do_List.dto.user.JWTUserData;
import dev.danielsebastian.To_Do_List.enums.ProgressStatus;
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
import java.util.ArrayList;
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
        List<User> users = new ArrayList<>();

        List<User> usersUpdated = this.filterUserIds(users, taskRequest.users());

        users.add(user);
        task.setUsers(usersUpdated);
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
    public TaskResponse updateTask(UUID id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Task not found"));

        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        task.setDeadline(taskRequest.deadline());
        task.setPriority(taskRequest.priority());
        task.setStatus(ProgressStatus.valueOf(taskRequest.status()));

        List<User> users = task.getUsers();

        List<User> usersUpdated = this.filterUserIds(users, taskRequest.users());

        task.setUsers(usersUpdated);

        Task save = taskRepository.save(task);
        return taskMapper.toTaskResponse(save);
    }

    @Transactional
    public void deleteTask(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

    private List<User> filterUserIds(List<User> users, List<UUID> ids) {
        if (ids != null && !ids.isEmpty()) {
            for (UUID user : ids) {
                User userFound = userRepository.findById(user).orElseThrow(() -> new DataNotFoundException("User not found"));

                //Verifica se o user já está na lista e continua
                if (users.contains(userFound)) {
                    continue;
                }

                users.add(userFound);
            }
        } else {
            users.clear();
        }

        return users;
    }

}
