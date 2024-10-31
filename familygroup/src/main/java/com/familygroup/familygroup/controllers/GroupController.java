package com.familygroup.familygroup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.familygroup.familygroup.exceptions.CustomException;
import com.familygroup.familygroup.models.dtos.GroupDto;
import com.familygroup.familygroup.services.GroupService;

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
    
}
