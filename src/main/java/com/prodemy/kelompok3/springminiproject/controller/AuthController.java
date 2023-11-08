package com.prodemy.kelompok3.springminiproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.prodemy.kelompok3.springminiproject.dto.UserDto;
import com.prodemy.kelompok3.springminiproject.entity.User;
import com.prodemy.kelompok3.springminiproject.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    // handler method to handle register user form submit request
    @PostMapping("/registration")
    public String registerUserAccount(@Valid @ModelAttribute("user") UserDto user,
                                      BindingResult result,
                                      Model model) {
        User existing = userService.findByUsername(user.getUsername());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "registration";
        }

        userService.saveUser(user);
        return "redirect:/registration?success";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "registration";
    }


}
