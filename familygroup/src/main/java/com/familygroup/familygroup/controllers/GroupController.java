package com.familygroup.familygroup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.familygroup.familygroup.exceptions.CustomException;
import com.familygroup.familygroup.models.dtos.GroupDto;
import com.familygroup.familygroup.services.GroupService;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/createGroup")
    public ResponseEntity<String> createGroup(@RequestBody GroupDto dto) throws CustomException {

        groupService.createGroup(dto);

        return ResponseEntity.ok("Group created!");
    }

    @GetMapping("/getGroupsByUser/{user_id}")
    public ResponseEntity<List<GroupDto>> getGroupsByUser(@PathVariable("user_id") Long userId)
            throws CustomException {

        List<GroupDto> dtos = groupService.getGroupsByUser(userId);

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/getGroupById/{id}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable("id") Long id) throws CustomException {

        GroupDto dto = groupService.getGroupById(id);

        return new ResponseEntity<GroupDto>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{groupId}/{created_by}")
    public ResponseEntity<String> deleteById(@PathVariable("groupId") Long groupId,
            @PathVariable("created_by") Long createdBy) throws CustomException {

        groupService.deleteGroupById(groupId, createdBy);

        return ResponseEntity.ok("Group deleted!");
    }
}
