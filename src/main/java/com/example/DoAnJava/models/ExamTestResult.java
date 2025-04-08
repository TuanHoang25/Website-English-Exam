package com.example.DoAnJava.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="exam_test_result")
public class ExamTestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id", nullable = false)
    private Integer resultId;

    @Column(name = "correct_questions") //so  cau dung
    private int correctQuestions;

    @Column(name = "incorrect_questions")
    private int incorrectQuestions;

    @Column(name = "unchoosen_questions")
    private int unchoosenQuestions;

    @Column(name = "do_exam_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime doExamDate;

    @ManyToOne
    @JoinColumn(name="id",nullable= false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name="exam_test_id",nullable= false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ExamTest examTest;

    @OneToMany(mappedBy = "examTestResult", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExamTestResultDetail> examTestResultDetails;


    @PrePersist
    protected void onCreate() {
        doExamDate = LocalDateTime.now();
    }
}
