package com.mariupol.schooldiary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "diary_entries")
public class DiaryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Size(max = 255, message = "Comment cannot exceed 255 characters.")
    @Column(name = "comment")
    private String comment;

    @Min(value = 1, message = "Grade must be at least 1.")
    @Max(value = 5, message = "Grade must not exceed 5.")
    @Column(name = "grade")
    private Integer grade;

    @Column(name = "date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type", nullable = false)
    private TypeEntry diaryEntryType;
}

