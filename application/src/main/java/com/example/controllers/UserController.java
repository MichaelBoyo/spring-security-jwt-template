package com.example.controllers;

import com.example.security.data.SecureUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@Slf4j
public class UserController {

    @PostMapping("/me")
    public String inviteAdmin(@AuthenticationPrincipal SecureUser secureUser) {
        log.info("user details => {}", secureUser);
        // use a model mapper to return the details of a user using a dto -- good practice
        return "User details";
    }
}
