package com.familygroup.familygroup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.familygroup.familygroup.exceptions.CustomException;
import com.familygroup.familygroup.models.dtos.LoginRequest;
import com.familygroup.familygroup.models.dtos.UsersDto;
import com.familygroup.familygroup.services.AuthenticationService;
import com.familygroup.familygroup.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("/user")
@Tag(name = "/user")
public class UserController {

    private UserService userService;

    private AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Create a new user", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newUser(@RequestBody @Valid UsersDto dto) throws CustomException {

        userService.createUser(dto);

        return new ResponseEntity<String>("User created!", HttpStatus.OK);

    }

    @Operation(summary = "Authenticate the user", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is authenticated."),
            @ApiResponse(responseCode = "409", description = "Authentication error")
    })
    @PostMapping("/auth")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest request) throws CustomException {

        String token = authenticationService.authenticateUser(request);

        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Search for a user", method = "POST")
    @GetMapping("/getUser/{username}")
    public ResponseEntity<UsersDto> getUser(@PathVariable("username") String username) throws CustomException {

        UsersDto dto = userService.findUserByUsername(username);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Delete a user", method = "DELETE")
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) throws CustomException {

        userService.deleteUser(id);

        return ResponseEntity.ok("User succesfully deleted!");
    }

    @Operation(summary = "Check if a user is valid", method = "GET")
    @GetMapping("/isUserValid/{username}")
    public ResponseEntity<Long> isUserValid(@PathVariable("username") String username) throws CustomException {

        Long isUser = userService.isUserValid(username);

        return new ResponseEntity<Long>(isUser, HttpStatus.OK);
    }

}
