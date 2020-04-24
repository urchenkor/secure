package com.secure.controller;

import com.secure.domain.Payment;
import com.secure.domain.User;
import com.secure.repos.PaymentRepos;
import com.secure.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")

public class PaymentController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private PaymentRepos paymentRepos;

    @Autowired
    private UserRepos userRepos;

    //-------------------------------ADD CONTROLLER-------------------------------------------------
    @GetMapping("/addPayments")
    public String addPay(Payment payment) {
        return "addPayments";
    }

    @PostMapping("/addPayments")
    public String addPayments(@RequestParam String nd, @RequestParam Float value, Model model){
        User userFromDb = userRepos.findByNd(nd);
        if(userFromDb == null) {
            model.addAttribute("notExist", "Номер договора не существует!");
            return "addPayments";
        }

        userFromDb.setBalance((userFromDb.getBalance() + value));
        Payment pay = new Payment(value, nd);
        userRepos.save(userFromDb);
        paymentRepos.save(pay);
        model.addAttribute("done", "Средства внесены!");
        return "addPayments";
    }

    //--------------------------------READ CONTROLLER-----------------------------------------------

    @GetMapping("/paymentList")
    public String getPaymentList(Model model) {
        model.addAttribute("payments", paymentRepos.findAll());
        return "paymentList";
    }

}

