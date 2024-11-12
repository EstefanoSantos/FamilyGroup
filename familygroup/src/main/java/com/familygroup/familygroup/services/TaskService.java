package com.familygroup.familygroup.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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

    //create new task associated with a user and group

    public void createGroupTask(TaskDto dto) throws CustomException {

        //check if is a valid user
        Users user = userRepository.findById(dto.userId())
        .orElseThrow(() -> new CustomException("User with id " + dto.userId() + " not found."));

        //check if is a valid group
        Group group = groupRepository.findById(dto.groupId())
        .orElseThrow(() -> new CustomException("Group with id " + dto.groupId() + " not found"));

        //Throws exception if user is not associated with the group
        if (!taskRepository.isUserPartOfGroup(group.getId(), user.getId())) {
            throw new 
            CustomException("User with id " + user.getId() + " is not part of group with id " + group.getId());
        }

        //mapping dto to Task
        Task task = new Task();

        task.setTaskName(dto.taskName());
        task.setTaskDescription(dto.taskDescription());
        task.setCreationTime(OffsetDateTime.now());
        task.setCreatedBy(user);
        task.setGroupId(group);

        //saves new task
        taskRepository.save(task);
    }


    //Bring tasks associated with some group
    public List<TaskDto> getTasksByGroup(Long groupId) throws CustomException {

        //check if is a valid group
        boolean group = groupRepository.existsById(groupId);

        if (group == false) {
            throw new CustomException("Group with id " + groupId + " was not found.");
        }
        
        //Tries to get group tasks otherwise throws exception
        List<Task> tasks = taskRepository.getTasksByGroup(groupId);

        if (tasks.isEmpty()) {
            throw new CustomException("There is no tasks associated with the group.");
        }

        //map tasks to dto
        List<TaskDto> dtos = new ArrayList<>();

        for(Task task :  tasks) {
            dtos.add(mapToTaskDto(task));
        }

        return dtos;
    }

    //Finish some task
    public void setTaskDone(Long groupId, Long taskId, Long userId) throws CustomException {

        //check if group exists
        boolean group = groupRepository.existsById(groupId);

        if (group == false) {
            throw new CustomException("Group with id " + groupId + " was not found.");
        }

        //check if task was created from both user and group
        boolean isUserTask = taskRepository.isUsersTask(groupId, taskId, userId);

        if (isUserTask == false) {
            throw new CustomException("User task not found.");
        }

        //here we check if task is finished by finished_at field at database
        Instant isTaskFinished = taskRepository.isTaskDone(groupId, taskId);

        if (isTaskFinished != null) {
            throw new CustomException("Task is already done.");
        } 

        OffsetDateTime instance = OffsetDateTime.now();
        
        //set task as done
        taskRepository.setTaskDone(instance, groupId, taskId);
    }

    //Delete task from group relation
    public void deleteTask(Long groupId, Long userId, Long taskId) throws CustomException {
        
        //check if task was created from both user and group
        boolean isUserTask = taskRepository.isUsersTask(groupId, taskId, userId);

        if (isUserTask == false) {
            throw new CustomException("Task was not found.");
        }

        taskRepository.deleteById(taskId);
    }

    //Map a Task class to TaskDto
    private TaskDto mapToTaskDto(Task task) {

        TaskDto dto = new TaskDto(
            task.getId(), 
            task.getTaskName(),
            task.getTaskDescription(),
            task.getCreationTime(),
            task.getFinishedAt(),
            task.getCreatedBy().getId(),
            task.getGroupId().getId()
            );

            return dto;
    }

}
