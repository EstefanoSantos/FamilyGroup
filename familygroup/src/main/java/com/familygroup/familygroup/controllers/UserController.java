package com.familygroup.familygroup.controllers;

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
import com.familygroup.familygroup.models.dtos.UsersDto;
import com.familygroup.familygroup.services.UserService;

import jakarta.validation.Valid;


@RestController()
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
   }

    @PostMapping("/createUser")
    public ResponseEntity<String> newUser(@RequestBody @Valid UsersDto dto) throws CustomException{

        userService.createUser(dto);

        return new ResponseEntity<String>("User created!", HttpStatus.OK);
  
    }

    @GetMapping("/getUser/{username}")
    public ResponseEntity<UsersDto> getUser(@PathVariable("username") String username) throws CustomException {

        UsersDto dto = userService.findUserByUsername(username);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) throws CustomException {

        userService.deleteUser(id);

        return ResponseEntity.ok("User succesfully deleted!");
    }

}
