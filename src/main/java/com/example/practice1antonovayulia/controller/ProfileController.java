package com.example.practice1antonovayulia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    @ResponseBody
    public String getProfile(Principal principal) {
        return "Успішна авторизація! Користувач: " + principal.getName();
    }
}
