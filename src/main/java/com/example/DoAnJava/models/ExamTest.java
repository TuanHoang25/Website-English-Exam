package com.example.DoAnJava.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="exam_test")
public class ExamTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_test_id", nullable = false)
    private Integer examTestId;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "examTest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExamQuestion> questions;

    @OneToMany(mappedBy = "examTest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExamTestResult> examTestResults;
}
