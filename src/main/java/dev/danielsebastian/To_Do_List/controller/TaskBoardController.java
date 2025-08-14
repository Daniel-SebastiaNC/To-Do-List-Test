package dev.danielsebastian.To_Do_List.controller;

import dev.danielsebastian.To_Do_List.dto.task_board.TaskBoardRequest;
import dev.danielsebastian.To_Do_List.dto.task_board.TaskBoardResponse;
import dev.danielsebastian.To_Do_List.service.TaskBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task-board")
@RequiredArgsConstructor
public class TaskBoardController {

    private final TaskBoardService taskBoardService;

    @PostMapping("/create")
    public ResponseEntity<TaskBoardResponse> createTaskBoard(@RequestBody TaskBoardRequest taskBoardRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskBoardService.createTaskBoard(taskBoardRequest));
    }
}
