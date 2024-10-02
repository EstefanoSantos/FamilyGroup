package com.familygroup.familygroup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.familygroup.familygroup.models.Users;
import com.familygroup.familygroup.services.UserService;

@Controller()
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/register")
    public String register(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);
        
        return "register";
    }

    @PostMapping("/newUser")
    public String newUser(@ModelAttribute("user") Users user) {
        userService.createUser(user);

        return "home";
    }
}
