package com.secure.controller;

import com.secure.domain.Subscriber;
import com.secure.repos.SubscriberRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReadController {
    @Autowired
    private SubscriberRepos subscriberRepos;

    @GetMapping("/subscriberList")
    public String mainPage(Model model) {
        Iterable<Subscriber> subscribers = subscriberRepos.findAll();
        model.addAttribute("subscribers", subscribers);
        return "subscriberList";
    }

}
