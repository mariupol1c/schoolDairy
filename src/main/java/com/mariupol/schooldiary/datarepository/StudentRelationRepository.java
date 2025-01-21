package com.mariupol.schooldiary.datarepository;

import com.mariupol.schooldiary.model.StudentRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRelationRepository extends JpaRepository<StudentRelation, Long> {

    List<StudentRelation> findAll();

    Optional<StudentRelation> findById(Long id);

    List<StudentRelation> findStudentIdsByRelatedUserId(Long relatedUserId);
}
