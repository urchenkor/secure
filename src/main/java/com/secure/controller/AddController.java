package com.secure.controller;

import com.secure.domain.Payment;
import com.secure.domain.Subscriber;
import com.secure.repos.PaymentRepos;
import com.secure.repos.SubscriberRepos;
import com.secure.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class AddController {
    @Autowired
    private SubscriberRepos subscriberRepos;

    @Autowired
    private PaymentRepos paymentRepos;

    @Autowired
    private UserRepos userRepos;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addSubscriber")
    public String add(Model model) {
        return "addSubscriber";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addSubscriber")
    public String addSubscriber(@RequestParam String firstName, @RequestParam String lastName,
                                @RequestParam String patronymic, @RequestParam String address,
                                @RequestParam String nd, @RequestParam String password
            ,Model model) {
        Integer intNd = Integer.parseInt(nd);
        Subscriber subscriber = new Subscriber(firstName, lastName, patronymic, address,
                intNd, password);
        subscriberRepos.save(subscriber);
        return "addSubscriber";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addPayments")
    public String addPay(Model model) {
        return "addPayments";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addPayments")
    public String addPayments(@RequestParam String nd, @RequestParam String value) {
        Float flValue = Float.parseFloat(value);
        Integer intNd = Integer.parseInt(nd);
        Payment pay = new Payment(flValue, intNd);
        Subscriber subscriber = subscriberRepos.findByNd(intNd);
        subscriber.setBalance(flValue + subscriber.getBalance());
        subscriberRepos.save(subscriber);
        paymentRepos.save(pay);
        return "addPayments";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/paymentList")
    public String getPaymentList(Model model) {
        model.addAttribute("payments", paymentRepos.findAll());
        return "paymentList";
    }

}

