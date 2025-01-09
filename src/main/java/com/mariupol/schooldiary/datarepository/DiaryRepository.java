package com.mariupol.schooldiary.datarepository;

import com.mariupol.schooldiary.model.DiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<DiaryEntry, Long> {
}
