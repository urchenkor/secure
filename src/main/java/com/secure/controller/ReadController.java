package com.secure.controller;

import com.secure.domain.User;
import com.secure.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReadController {
    @Autowired
    private UserRepos userRepos;



}
