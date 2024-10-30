package com.familygroup.familygroup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.familygroup.familygroup.exceptions.CustomException;
import com.familygroup.familygroup.models.Group;
import com.familygroup.familygroup.services.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/createGroup")
    public ResponseEntity<String> createGroup(@RequestBody Group group) throws CustomException {

        if (group.getCreatedBy() == null || group.getCreatedBy().getId() == null) {
            throw new CustomException("Creator ID must be provided.");
        }
        

        groupService.createGroup(group);

        return ResponseEntity.ok("Group created!");
    }
    
}
