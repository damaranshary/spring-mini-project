package com.prodemy.kelompok3.springminiproject.controller;

import com.prodemy.kelompok3.springminiproject.entity.User;
import com.prodemy.kelompok3.springminiproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        User user = userService.getCurrentUser();

        String role = user.getRoles().get(0).getName();

        model.addAttribute("user", user);

        if (role.equals("ROLE_ADMIN")) {
            return "indexAdmin";
        }

        return "indexUser";
    }
}
