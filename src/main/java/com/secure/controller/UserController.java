package com.secure.controller;

import com.secure.domain.Role;
import com.secure.domain.User;
import com.secure.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepos userRepos;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepos.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "subscriberEdit";
    }


    @GetMapping("/delete/{user}")
    public String deleteUser(@PathVariable User user) {
        userRepos.delete(user);

        return "redirect:/user";
    }
}

