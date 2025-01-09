package com.mariupol.schooldiary.service;

import com.mariupol.schooldiary.datarepository.DiaryRepository;
import com.mariupol.schooldiary.model.DiaryEntry;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;

    @Autowired
    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
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
                    existingEntry.setComment(newComment); // Обновляем только комментарий
                    return diaryRepository.save(existingEntry);
                });
    }
}
