package com.familygroup.familygroup.services;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.familygroup.familygroup.config.JwtService;
import com.familygroup.familygroup.exceptions.CustomException;
import com.familygroup.familygroup.models.Users;
import com.familygroup.familygroup.models.dtos.LoginRequest;
import com.familygroup.familygroup.repositories.UserRepository;

@Service
public class AuthenticationService {

    private UserRepository userRepository;

    private JwtService jwtService;

    private BCryptPasswordEncoder encoder;

    public AuthenticationService(UserRepository userRepository, JwtService jwtService,
            BCryptPasswordEncoder encoder) {

        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    public String authenticateUser(LoginRequest request) throws CustomException {

        Users user = userRepository.findUserByUsername(request.username());

        if (user == null || user.isLoginCorrect(request, encoder)) {
            throw new CustomException("Username or password invalid");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getAuthorities());

        return jwtService.generateToken(authenticationToken);
    }

}
