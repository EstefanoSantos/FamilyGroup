package com.familygroup.familygroup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import com.familygroup.familygroup.exceptions.CustomException;
import com.familygroup.familygroup.models.Role;
import com.familygroup.familygroup.models.Users;
import com.familygroup.familygroup.models.dtos.UsersDto;
import com.familygroup.familygroup.repositories.RoleRepository;
import com.familygroup.familygroup.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void createUser(UsersDto userDto) throws CustomException {

        boolean isUser = userRepository.isUsername(userDto.username());

        if (isUser == true) {
            throw new CustomException("Username's already in use.");
        }

        String password = userDto.password();
        System.out.println(password);

        var user = mapToUser(userDto);

        user.setPassword(encoder.encode(password));

        Role role = roleRepository.findUserRoleByName();
        user.setRoles(Arrays.asList(role));

        userRepository.save(user);

    }

    public Long isUserValid(String username) throws CustomException {

        Long isUser = userRepository.isValidUser(username);

        if (isUser == null) {
            throw new CustomException("Username is not valid.");
        }

        return isUser;
    }

    public UsersDto findUserByUsername(String username) throws CustomException {

        Users user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new CustomException("User not found.");
        }

        UsersDto dto = mapToDto(user);

        return dto;
    }

    public void deleteUser(Long id) throws CustomException {

        boolean isUser = userRepository.existsById(id);

        if (isUser == false) {
            throw new CustomException("There's no user with the given id.");
        }

        userRepository.deleteById(id);

    }

    private Users mapToUser(UsersDto dto) {

        Users user = new Users();

        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(encoder.encode(dto.password()));

        return user;
    }

    private UsersDto mapToDto(Users user) {

        UsersDto dto = new UsersDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword());

        return dto;
    }

}
