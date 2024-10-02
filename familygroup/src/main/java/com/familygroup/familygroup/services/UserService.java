package com.familygroup.familygroup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.familygroup.familygroup.models.Users;
import com.familygroup.familygroup.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(Users  user) {
        userRepository.save(user);
    }
    
}
