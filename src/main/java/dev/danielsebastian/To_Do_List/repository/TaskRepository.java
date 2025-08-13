package dev.danielsebastian.To_Do_List.repository;

import dev.danielsebastian.To_Do_List.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query(
            value = "SELECT * FROM tb_task t WHERE " +
                    "t.status = CAST(COALESCE(:status, CAST(t.status AS text)) AS progress_status) AND " +
                    "t.priority = COALESCE(:priority, t.priority) AND " +
                    "t.deadline >= COALESCE(:startOfDay, t.deadline) AND " +
                    "t.deadline <= COALESCE(:endOfDay, t.deadline)",
            nativeQuery = true
    )
    Page<Task> findByFilters(
            @Param("status") String status,
            @Param("priority") Short priority,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            Pageable pageable
    );
}
