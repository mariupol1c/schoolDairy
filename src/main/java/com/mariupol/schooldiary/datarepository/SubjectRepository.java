package com.mariupol.schooldiary.datarepository;

import com.mariupol.schooldiary.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Optional<Object> findByName(String name);
}
