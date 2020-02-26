package com.secure.controller;

import com.secure.domain.Subscriber;
import com.secure.domain.User;
import com.secure.repos.SubscriberRepos;
import com.secure.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private SubscriberRepos subscriberRepos;

    @PostMapping("/main")
    public String addSubscriber(@RequestParam String firstName, @RequestParam String lastName,
                          Model model) {
        Subscriber subscriber = new Subscriber(firstName, lastName);
        subscriberRepos.save(subscriber);
        return "main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/nomain")
    public String mainPage(Model model) {
        Iterable<Subscriber> subscribers = subscriberRepos.findAll();
        model.addAttribute("subscribers", subscribers);
        return "/nomain";
    }

    @GetMapping("/main")
    public String main(Model model) {
        return "/main";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "/admin";
    }
}
