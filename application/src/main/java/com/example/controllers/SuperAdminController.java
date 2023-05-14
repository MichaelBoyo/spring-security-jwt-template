package com.example.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/superadmin")
@RestController
public class SuperAdminController {
    @PostMapping("/invite-admin")
    public String inviteAdmin() {
        return "Admin Invited";
    }
}
