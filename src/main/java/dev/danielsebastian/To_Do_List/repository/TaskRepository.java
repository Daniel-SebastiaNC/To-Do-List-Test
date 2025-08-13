package dev.danielsebastian.To_Do_List.repository;

import dev.danielsebastian.To_Do_List.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
}
