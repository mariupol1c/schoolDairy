package com.mariupol.schooldiary.service;

import com.mariupol.schooldiary.datarepository.StudentRelationRepository;
import com.mariupol.schooldiary.model.StudentRelation;
import com.mariupol.schooldiary.model.RelationType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentRelationService {

    private final StudentRelationRepository studentRelationRepository;

    public StudentRelationService(StudentRelationRepository studentRelationRepository) {
        this.studentRelationRepository = studentRelationRepository;
    }

    public StudentRelation addRelation(StudentRelation relation) {
        return studentRelationRepository.save(relation);
    }

    public List<StudentRelation> getAllRelations() {
        return studentRelationRepository.findAll();
    }

    public StudentRelation getRelationById(Long id) {
        return studentRelationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Relation not found with id: " + id));
    }

    public StudentRelation updateRelation(Long id, StudentRelation relation) {
        relation.setId(id);
        return studentRelationRepository.save(relation);
    }

    public void deleteRelation(Long id) {
        studentRelationRepository.deleteById(id);
    }

//    public List<StudentRelation> getRelationsByStudent(Long studentId) {
//        return studentRelationRepository.findAllByStudentId(studentId);
//    }
//
//    public List<StudentRelation> getStudentsByRelatedUser(Long relatedUserId) {
//        return studentRelationRepository.findAllByRelatedUserId(relatedUserId);
//    }
//
//    public List<StudentRelation> getRelationsByStudentAndType(Long studentId, RelationType relationType) {
//        return studentRelationRepository.findAllByStudentIdAndRelationType(studentId, relationType);
//    }
}
