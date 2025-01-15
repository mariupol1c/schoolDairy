package com.mariupol.schooldiary.controller;

import com.mariupol.schooldiary.model.User;
import com.mariupol.schooldiary.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("pageTitle")
    public String getPageTitle(Model model) {
        return "Users";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            model.addAttribute("errorMessage", "User not found");
            return "redirect:/users";
        }
        model.addAttribute("user", user.get());
        return "user-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("user") User updatedUser) {
        userService.updateUser(id, updatedUser);
        return "redirect:/users?success=UserUpdated";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        if (!userService.existsById(id)) {
            return "redirect:/users?error=UserNotFound";
        }
        userService.deleteUserById(id);
        return "redirect:/users?success=UserDeleted";
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user-add";
    }

    @PostMapping("/add")
    public String saveNewUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/users?success=UserAdded";
    }
}

