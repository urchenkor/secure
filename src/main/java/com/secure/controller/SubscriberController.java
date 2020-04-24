package com.secure.controller;

import com.secure.domain.Role;
import com.secure.domain.User;
import com.secure.repos.PaymentRepos;
import com.secure.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/subscriber")
@PreAuthorize("hasAuthority('ADMIN')")
public class SubscriberController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepos userRepos;
    //----------------------------------MAIN------------------------------------------------------

    /* @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepos.findAll());
        return "subscriberList";
    }*/

    //----------------------------------ADD CONTROLLER---------------------------------------
    @GetMapping("/add")
    public String add(User u, Model model) {
        return "addSubscriber";
    }

    /*@PostMapping("/add")
    public String addSubscriber(@RequestParam User u) {
        User newUser = new User(u.getNd(), u.getPassword(), u.getFirstName(), u.getLastName(),
                u.getPatronymic(), u.getAddress());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(Collections.singleton(Role.USER));
        userRepos.save(newUser);
        return "addSubscriber";
    }*/
    @PostMapping("/add")
    public String addSubscriber(@RequestParam String lastName, @RequestParam String firstName,
                                @RequestParam String patronymic, @RequestParam String address,
                                @RequestParam String nd, @RequestParam String password,
                                Model model) {
        User userFromDb = userRepos.findByNd(nd);
        if(userFromDb != null) {
            model.addAttribute("isExist", "Номер договора уже существует!");
            return "addSubscriber";
        }
        User user = new User(nd, password, firstName, lastName, patronymic, address);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));

        userRepos.save(user);

        model.addAttribute("done", "Абонент добавлен!");
        return "addSubscriber";
    }
    /*@PostMapping("/addSubscriber")
    public String addSubscriber(@Valid User user, BindingResult bindingResult, Model model) {
        User userFromDb = userRepos.findByNd(user.getNd());
        if(userFromDb != null) {

        } else if (bindingResult.hasErrors()) {
            return "addSubscriber";
        }
        User newSubscriber = new User(user.getNd(), user.getPassword(), user.getFirstName(),
                user.getLastName(), user.getPatronymic(), user.getAddress());
        newSubscriber.setPassword(passwordEncoder.encode(newSubscriber.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        userRepos.save(newSubscriber);
        model.addAttribute("message", "Абонент " + user.getUsername() + " создан.");
        return "redirect:/admin";
    }*/

    //------------------------------------READ CONTROLLER-------------------------------------------

    @GetMapping("/list")
    public String mainPage(Model model) {
        Iterable<User> user = userRepos.findAll();
        model.addAttribute("users", user);
        return "subscriberList";
    }

    //------------------------------------UPDATE CONTROLLER-----------------------------------------

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "subscriberEdit";
    }


    @PostMapping("/save")
    public String userSave(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String patronymic, @RequestParam String address,
            @RequestParam String nd, @RequestParam String password,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        if (!firstName.equals("")) {user.setFirstName(firstName);}

        if (!lastName.equals("")) {user.setLastName(lastName);}

        if (!patronymic.equals("")) {user.setPatronymic(patronymic);}

        if (!address.equals("")) {user.setAddress(address);}


        if (!nd.equals("")) {
            user.setUsername(nd);
            user.setNd(nd);
        }

        if (!password.equals("")) {
            user.setPassword(passwordEncoder.encode(password));
        }

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepos.save(user);

        return "redirect:/subscriber/list/";
    }

    //------------------------------------DELETE CONTROLLER-----------------------------------------

    @GetMapping("/delete/{user}")
    public String deleteUser(@PathVariable User user) {
        userRepos.delete(user);

        return "redirect:/subscriber/list/";
    }

}

