package com.secure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {



    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ADMIN".equals(auth.getAuthority())) {
                return "addSubscriber";
            }
        }

        String authUser = authentication.getName();
        model.addAttribute("userName", authUser);
        return "redirect:/Account";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String admin(Model model) {
        return "/addSubscriber";
    }
}
