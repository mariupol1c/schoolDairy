package com.mariupol.schooldiary.service;

import com.mariupol.schooldiary.datarepository.DiaryRepository;
import com.mariupol.schooldiary.datarepository.StudentRelationRepository;
import com.mariupol.schooldiary.model.DiaryEntry;
import com.mariupol.schooldiary.model.Role;
import com.mariupol.schooldiary.model.StudentRelation;
import com.mariupol.schooldiary.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final StudentRelationRepository studentRelationRepository;
    private final UserService userService;

    @Autowired
    public DiaryService(DiaryRepository diaryRepository, StudentRelationRepository studentRelationRepository, UserService userService) {
        this.diaryRepository = diaryRepository;
        this.studentRelationRepository = studentRelationRepository;
        this.userService = userService;
    }

    public List<DiaryEntry> getAllDiaryEntries() {
        return diaryRepository.findAll();
    }

    public Optional<DiaryEntry> getDiaryEntryById(long id) {
        return diaryRepository.findById(id);
    }

    public void deleteDiaryEntryById(long id) {
        diaryRepository.deleteById(id);
    }

    public DiaryEntry addDiaryEntry(DiaryEntry diaryEntry) {
        return diaryRepository.save(diaryEntry);
    }

    public boolean existsById(Long id) {
        return diaryRepository.existsById(id);
    }

    public DiaryEntry updateDiaryEntry(Long id, DiaryEntry updatedEntry) {
        DiaryEntry existingEntry = diaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DiaryEntry not found with id: " + id));
        existingEntry.setSubject(updatedEntry.getSubject());
        existingEntry.setComment(updatedEntry.getComment());
        existingEntry.setGrade(updatedEntry.getGrade());
        existingEntry.setDiaryEntryType(updatedEntry.getDiaryEntryType());
        existingEntry.setDate(updatedEntry.getDate());
        return diaryRepository.save(existingEntry);
    }

    public Optional<DiaryEntry> updateComment(Long id, String newComment) {
        return diaryRepository.findById(id)
                .map(existingEntry -> {
                    existingEntry.setComment(newComment);
                    return diaryRepository.save(existingEntry);
                });
    }

    public List<DiaryEntry> getEntriesForCurrentUser() {
        User currentUser = userService.getCurrentUser();
        if (currentUser.hasRole(Role.ADMIN)) {
            return diaryRepository.findAll();
        } else if (currentUser.hasRole(Role.TEACHER) || currentUser.hasRole(Role.PARENT)) {
            List<Long> studentIds = studentRelationRepository.findStudentIdsByRelatedUserId((long) currentUser.getId())
                    .stream()
                    .map(StudentRelation::getStudentId)
                    .collect(Collectors.toList());
            return diaryRepository.findAllByUserIdIn(studentIds);
        } else if (currentUser.hasRole(Role.STUDENT)) {
            List<Long> studentIds = new ArrayList<>();
            studentIds.add((long) currentUser.getId());
            return diaryRepository.findAllByUserIdIn(studentIds);
        }
     else {
            return Collections.emptyList();
        }
    }
}
