package com.secure.controller;

import com.secure.domain.Role;
import com.secure.domain.User;
import com.secure.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepos userRepos;
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        User userFromDB = userRepos.findByUsername(user.getUsername());
        if (userFromDB != null) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        userRepos.save(user);
        return "redirect:/login";
    }
}

