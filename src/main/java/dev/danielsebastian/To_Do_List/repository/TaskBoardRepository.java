package dev.danielsebastian.To_Do_List.repository;

import dev.danielsebastian.To_Do_List.model.TaskBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskBoardRepository extends JpaRepository<TaskBoard, UUID> {
    Page<TaskBoard> findAll(Pageable pageable);
}
