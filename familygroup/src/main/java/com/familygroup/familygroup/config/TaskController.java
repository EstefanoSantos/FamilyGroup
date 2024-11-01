package com.familygroup.familygroup.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.familygroup.familygroup.exceptions.CustomException;
import com.familygroup.familygroup.models.dtos.TaskDto;
import com.familygroup.familygroup.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/newTask")
    public ResponseEntity<String> createGroupTask(@RequestBody TaskDto dto) throws CustomException {

            taskService.createGroupTask(dto);
            return ResponseEntity.ok("New group task created.");
  
    }
    
}
