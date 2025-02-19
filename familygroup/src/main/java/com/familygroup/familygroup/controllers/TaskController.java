package com.familygroup.familygroup.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.familygroup.familygroup.exceptions.CustomException;
import com.familygroup.familygroup.models.dtos.TaskDto;
import com.familygroup.familygroup.services.TaskService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Add new task to a specific group")
    @PostMapping("/newTask")
    public ResponseEntity<String> createGroupTask(@RequestBody TaskDto dto) throws CustomException {

        taskService.createGroupTask(dto);
        return ResponseEntity.ok("New group task created.");

    }

    @Operation(summary = "Gets all tasks related to a specific group")
    @GetMapping("/getTasks/{id}")
    public ResponseEntity<List<TaskDto>> getTasks(@PathVariable("id") Long id) throws CustomException {

        List<TaskDto> dtos = taskService.getTasksByGroup(id);

        return ResponseEntity.ok(dtos);

    }

    @Operation(summary = "Change task status to done")
    @PostMapping("/setTaskDone/{group_id}/{task_id}/{user_id}")
    public ResponseEntity<String> setTaskDone(@PathVariable("group_id") Long groupId,
            @PathVariable("task_id") Long taskId, @PathVariable("user_id") Long userId)
            throws CustomException {

        taskService.setTaskDone(groupId, taskId, userId);

        return ResponseEntity.ok("Task finished!");
    }

    @Operation(summary = "Delete specific task from specific user at specific group")
    @DeleteMapping("/deleteTask/{group_id}/{user_id}/{task_id}")
    public ResponseEntity<String> deleteTask(@PathVariable("group_id") Long groupId,
            @PathVariable("user_id") Long userId, @PathVariable("task_id") Long taskId) throws CustomException {

        taskService.deleteTask(groupId, userId, taskId);

        return ResponseEntity.ok("Task deleted!");
    }
}
