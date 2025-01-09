package com.mariupol.schooldiary.controller;

import com.mariupol.schooldiary.model.Subject;
import com.mariupol.schooldiary.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @ModelAttribute("pageTitle")
    public String getPageTitle() {
        return "Subjects";
    }

    @GetMapping
    public String getAllSubjects(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "subjects";
    }

    @GetMapping("/add")
    public String addSubjectForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "subject-add";
    }

    @PostMapping("/add")
    public String createSubject(@ModelAttribute Subject subject) {
        subjectService.createSubject(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/edit/{id}")
    public String editSubjectForm(@PathVariable Integer id, Model model) {
        model.addAttribute("subject", subjectService.getSubjectById(id).orElse(null));
        return "subject-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateSubject(@PathVariable Integer id, @ModelAttribute Subject subject) {
        subjectService.updateSubject(id, subject);
        return "redirect:/subjects";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Integer id) {
        subjectService.deleteSubject(id);
        return "redirect:/subjects";
    }
}
