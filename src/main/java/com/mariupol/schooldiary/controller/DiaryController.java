package com.mariupol.schooldiary.controller;

import com.mariupol.schooldiary.model.DiaryEntry;
import com.mariupol.schooldiary.model.Subject;
import com.mariupol.schooldiary.model.TypeEntry;
import com.mariupol.schooldiary.model.User;
import com.mariupol.schooldiary.service.DiaryService;
import com.mariupol.schooldiary.service.SubjectService;
import com.mariupol.schooldiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;
    private final UserService userService;
    private final SubjectService subjectService;

    @Autowired
    public DiaryController(DiaryService diaryService, UserService userService, SubjectService subjectService) {
        this.diaryService = diaryService;
        this.userService = userService;
        this.subjectService = subjectService;
    }

    @ModelAttribute("pageTitle")
    public String getPageTitle() {
        return "Diary Entries";
    }

    @GetMapping
    public String getDiary(Model model) {
        model.addAttribute("diaryEntries", diaryService.getEntriesForCurrentUser());
        return "diary";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("diaryEntry", new DiaryEntry());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("entryTypes", TypeEntry.values());
        return "diary-add";
    }

    @PostMapping("/add")
    public String addDiaryEntry(@ModelAttribute DiaryEntry diaryEntry,
                                @RequestParam int subjectId, @RequestParam int userId) {
        setUserAndSubject(diaryEntry, subjectId, userId);
        diaryService.addDiaryEntry(diaryEntry);
        return "redirect:/diary";
    }



    @GetMapping("/edit/{id}")
    public String showEditDiaryEntryForm(@PathVariable long id, Model model) {
        Optional<DiaryEntry> diaryEntry = diaryService.getDiaryEntryById(id);
        if (diaryEntry.isPresent()) {
            model.addAttribute("diaryEntry", diaryEntry.get());
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("entryTypes", TypeEntry.values());
            return "diary-edit";
        } else {
            return "redirect:/diary?error=notfound";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateDiaryEntry(@PathVariable long id, @ModelAttribute DiaryEntry updatedEntry,
                                   @RequestParam int subjectId, @RequestParam int userId) {
        setUserAndSubject(updatedEntry, subjectId, userId);
        diaryService.updateDiaryEntry(id, updatedEntry);
        return "redirect:/diary";
    }

    @PostMapping("/delete/{id}")
    public String deleteDiaryEntry(@PathVariable long id) {
        if (diaryService.existsById(id)) {
            diaryService.deleteDiaryEntryById(id);
        }
        return "redirect:/diary";
    }

    private void setUserAndSubject(DiaryEntry diaryEntry, int subjectId, int userId) {
        Subject subject = subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        diaryEntry.setSubject(subject);
        diaryEntry.setUser(user);
    }
}
