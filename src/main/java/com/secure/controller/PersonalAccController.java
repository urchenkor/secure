package com.secure.controller;

import com.secure.domain.User;
import com.secure.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonalAccController {
    @Autowired
    private UserRepos userRepos;


    @GetMapping("/Account")
    public String personalAcc(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String authUser = authentication.getName();
        User user = userRepos.findByUsername(authUser);
        model.addAttribute("user", user);
        return "personalAcc";
    }
}
