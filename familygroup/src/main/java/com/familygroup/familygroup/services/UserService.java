package com.familygroup.familygroup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import com.familygroup.familygroup.models.Role;
import com.familygroup.familygroup.models.Users;
import com.familygroup.familygroup.models.dtos.UsersDto;
import com.familygroup.familygroup.repositories.RoleRepository;
import com.familygroup.familygroup.repositories.UserRepository;

import jakarta.persistence.EntityExistsException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void createUser(UsersDto  userDto) {

        boolean isUser = userRepository.isUsername(userDto.getUsername());

        if (isUser == true) {
            throw new EntityExistsException("Username's already in use.");
        }

        Users user = new Users();

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        Role role = roleRepository.findUserRoleByName();
        user.setRoles(Arrays.asList(role));

        userRepository.save(user);
       
    }

    public Users findUserByUsername(String username, String password) {

        Users user = userRepository.findUserByUsername(username);
        
        if (user == null) {
            return null;
        }

        if (!user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }
    
}
