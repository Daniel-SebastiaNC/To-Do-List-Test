package dev.danielsebastian.To_Do_List.controller;

import dev.danielsebastian.To_Do_List.dto.task_board.TaskBoardRequest;
import dev.danielsebastian.To_Do_List.dto.task_board.TaskBoardResponse;
import dev.danielsebastian.To_Do_List.service.TaskBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task-board")
@RequiredArgsConstructor
public class TaskBoardController {

    private final TaskBoardService taskBoardService;

    @PostMapping("/create")
    public ResponseEntity<TaskBoardResponse> createTaskBoard(@RequestBody TaskBoardRequest taskBoardRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskBoardService.createTaskBoard(taskBoardRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskBoardResponse>> getAllTaskBoards(Pageable pageable) {
        return ResponseEntity.ok(taskBoardService.getAllTaskBoards(pageable));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskBoardResponse> updateTaskBoard(@PathVariable UUID id, @RequestBody TaskBoardRequest taskBoardRequest) {
        return ResponseEntity.ok(taskBoardService.updateTaskBoard(id, taskBoardRequest));
    }
}
