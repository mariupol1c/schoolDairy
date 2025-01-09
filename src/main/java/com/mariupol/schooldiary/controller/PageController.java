package com.mariupol.schooldiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("pageTitle", "Welcome to the School Diary");
        return "index";
    }
}

