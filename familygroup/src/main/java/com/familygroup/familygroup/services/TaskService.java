package com.familygroup.familygroup.services;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.familygroup.familygroup.exceptions.CustomException;
import com.familygroup.familygroup.models.Group;
import com.familygroup.familygroup.models.Task;
import com.familygroup.familygroup.models.Users;
import com.familygroup.familygroup.models.dtos.TaskDto;
import com.familygroup.familygroup.repositories.GroupRepository;
import com.familygroup.familygroup.repositories.TaskRepository;
import com.familygroup.familygroup.repositories.UserRepository;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;


    public void createGroupTask(TaskDto dto) throws CustomException {

        Users user = userRepository.findById(dto.userId())
        .orElseThrow(() -> new CustomException("User with id " + dto.userId() + " not found."));

        Group group = groupRepository.findById(dto.groupId())
        .orElseThrow(() -> new CustomException("Group with id " + dto.groupId() + " not found"));

        if (!taskRepository.isUserPartOfGroup(group.getId(), user.getId())) {
            throw new 
            CustomException("User with id " + user.getId() + " is not part of group with id " + group.getId());
        }

        Task task = new Task();

        task.setTaskName(dto.taskName());
        task.setTaskDescription(dto.taskDescription());
        task.setCreationTime(OffsetDateTime.now());
        task.setCreatedBy(user);
        task.setGroupId(group);

        taskRepository.save(task);
    }
}
