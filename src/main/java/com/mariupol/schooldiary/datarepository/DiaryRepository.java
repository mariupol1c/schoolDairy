package com.mariupol.schooldiary.datarepository;

import com.mariupol.schooldiary.model.DiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<DiaryEntry, Long> {

    List<DiaryEntry> findAllByUserIdIn(List<Long> UserIds);
}
