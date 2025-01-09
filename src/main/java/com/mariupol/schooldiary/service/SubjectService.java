package com.mariupol.schooldiary.service;

import com.mariupol.schooldiary.datarepository.SubjectRepository;
import com.mariupol.schooldiary.model.Subject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject createSubject(Subject subject) {
        if (subjectRepository.findByName(subject.getName()).isPresent()) {
            throw new IllegalArgumentException("Subject with this name already exists.");
        }
        return subjectRepository.save(subject);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // Получение предмета по ID
    public Optional<Subject> getSubjectById(Integer id) {
        return subjectRepository.findById(id);
    }

    // Обновление существующего предмета
    public Optional<Subject> updateSubject(Integer id, Subject updatedSubject) {
        return subjectRepository.findById(id)
                .map(existingSubject -> {
                    existingSubject.setName(updatedSubject.getName());
                    return subjectRepository.save(existingSubject);
                });
    }

    // Удаление предмета
    public boolean deleteSubject(Integer id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

