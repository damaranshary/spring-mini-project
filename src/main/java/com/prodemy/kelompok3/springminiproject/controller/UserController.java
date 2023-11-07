package com.prodemy.kelompok3.springminiproject.controller;

import com.prodemy.kelompok3.springminiproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.prodemy.kelompok3.springminiproject.dto.UserDto;
//import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.User;
import com.prodemy.kelompok3.springminiproject.service.UserServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserServiceImpl userServiceImpl) {
        super();
        this.userService = userServiceImpl;
    }

    // to get users profile
    @GetMapping("/profile")
    public String getUserProfile(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "detailsUserPage";
    }

    // to update users profile
    @GetMapping("/profile/edit")
    public String updateUserProfileForm(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);

        return "updateUserPage";
    }

    @PostMapping("/profile/update")
    public String updateUser(@ModelAttribute(name = "user") UserDto userDto) {
        userService.updateUser(userDto);

        return "redirect:/profile/edit?success";
    }

    // this is the borderline between user and admin controller
    @GetMapping("/admin/get/users")
    public String getRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("userList", users);
        return "userListPage";
    }

    @GetMapping("/admin/get/user/{userId}")
    public String getUserById(@PathVariable(name = "userId") String userId, Model model){
        User user = userService.findUserById(userId);

        model.addAttribute("user", user);
        return "detailsUserPage";
    }

    @GetMapping("/admin/add/user")
    public String addUserForm(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "registration";
    }

    @PostMapping("/admin/add/user")
    public String addUser(@ModelAttribute("user") UserDto user) {
        userService.saveUser(user);
        return "redirect:/admin/add/user?success";
    }

    @GetMapping("/admin/update/user/{userId}")
    public String updateUserFromAdminForm(@PathVariable("userId") String userId, Model model) {
        User user = userService.findUserById(userId);

        model.addAttribute("user", user);

        return "updateUserPage";
    }

    @PostMapping("/admin/update/user")
    public String updateUserFromAdmin(@ModelAttribute(name = "user") UserDto userDto) {
        userService.updateUser(userDto);

        return "redirect:/admin/update/user/" + userDto.getId() + "?success";
    }

    @GetMapping("/admin/delete/user/{userId}")
    public String deleteUser(@PathVariable(name = "userId") String userId) {
        userService.deleteUserById(userId);

        return "redirect:/admin/get/users";
    }
}
