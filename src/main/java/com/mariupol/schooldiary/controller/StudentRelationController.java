package com.mariupol.schooldiary.controller;

import com.mariupol.schooldiary.model.StudentRelation;
import com.mariupol.schooldiary.model.RelationType;
import com.mariupol.schooldiary.model.User;
import com.mariupol.schooldiary.service.StudentRelationService;
import com.mariupol.schooldiary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student-relations")
public class StudentRelationController {

    private final StudentRelationService studentRelationService;
    private final UserService userService;

    public StudentRelationController(StudentRelationService studentRelationService, UserService userService) {
        this.studentRelationService = studentRelationService;
        this.userService = userService;
    }

    @GetMapping
    public String listRelations(Model model) {
        List<StudentRelation> relations = studentRelationService.getAllRelations();

        List<Map<String, Object>> enrichedRelations = relations.stream().map(relation -> {
            Map<String, Object> relationView = new HashMap<>();
            relationView.put("id", relation.getId());
            relationView.put("relationType", relation.getRelationType());

            String studentName = userService.getUserById(Math.toIntExact(relation.getStudentId())).map(User::getName).orElse("Unknown");
            String relatedUserName = userService.getUserById(Math.toIntExact(relation.getRelatedUserId())).map(User::getName).orElse("Unknown");

            relationView.put("studentName", studentName);
            relationView.put("relatedUserName", relatedUserName);

            return relationView;
        }).toList();

        model.addAttribute("relations", enrichedRelations);
        model.addAttribute("pageTitle", "Student Relations");
        return "student-relations";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        List<User> students = userService.getAllStudents();
        List<User> relatedUsers = userService.getAllTeachersAndParents();

        model.addAttribute("relation", new StudentRelation());
        model.addAttribute("students", students);
        model.addAttribute("relatedUsers", relatedUsers);
        model.addAttribute("relationTypes", RelationType.values());
        return "student-relations-add";
    }

    @PostMapping("/add")
    public String addRelation(@ModelAttribute StudentRelation relation) {
        studentRelationService.addRelation(relation);
        return "redirect:/student-relations?success=Relation added successfully!";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        StudentRelation relation = studentRelationService.getRelationById(id);
        List<User> students = userService.getAllStudents();
        List<User> relatedUsers = userService.getAllTeachersAndParents();

        model.addAttribute("relation", relation);
        model.addAttribute("students", students);
        model.addAttribute("relatedUsers", relatedUsers);
        model.addAttribute("relationTypes", RelationType.values());
        return "student-relations-edit";
    }

    @PostMapping("/edit/{id}")
    public String editRelation(@PathVariable Long id, @ModelAttribute StudentRelation updatedRelation) {
        studentRelationService.updateRelation(id, updatedRelation);
        return "redirect:/student-relations?success=Relation updated successfully!";
    }

    @GetMapping("/delete/{id}")
    public String deleteRelation(@PathVariable Long id) {
        studentRelationService.deleteRelation(id);
        return "redirect:/student-relations?success=Relation deleted successfully!";
    }
}
