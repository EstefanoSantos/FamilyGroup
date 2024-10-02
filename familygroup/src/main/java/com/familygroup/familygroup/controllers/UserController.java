package com.familygroup.familygroup.controllers;

import org.hibernate.boot.model.source.internal.hbm.ModelBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.familygroup.familygroup.models.Users;
import com.familygroup.familygroup.models.dtos.UsersDto;
import com.familygroup.familygroup.services.UserService;

import jakarta.validation.Valid;

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
    public String newUser(@ModelAttribute("user") UsersDto user) {
        userService.createUser(user);

        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login/authenticate")
    public String userAuthentication(@Valid @RequestParam(value ="username", required=true) String username,
    @RequestParam(value = "password", required = true) String password,
     RedirectAttributes redirectAttributes, BindingResult errors, RedirectAttributes model) {

        if (errors.hasErrors()) {
            return "login";
        }

        Users user = userService.findUserByUsername(username, password);

        if (user == null) {
            return "redirect:/login?fail";
        }

        return "redirect:/home";
    }
}
