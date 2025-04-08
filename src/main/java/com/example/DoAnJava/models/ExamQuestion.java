package com.example.DoAnJava.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="exam_question")
public class ExamQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "number")
    private Integer number;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "audio3Url")
    private String audioUrl;

    @Column(columnDefinition = "TEXT")
    private String paragraph;

    @Column(name = "part")
    private Integer part;

    @Column(name = "question")
    private String question;

    @Column(name = "optionA")
    private String optionA;

    @Column(name = "optionB")
    private String optionB;

    @Column(name = "optionC")
    private String optionC;

    @Column(name = "optionD")
    private String optionD;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @Column(name = "script", length = 10000)
    private String script;

    @Transient
    private String selectedAnswer;

    @ManyToOne
    @JoinColumn(name="exam_test_id",nullable= false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ExamTest examTest;

    public ExamQuestion(Integer questionId) {
    }

    public boolean isCorrect(String answer){
        return this.correctAnswer.equals(answer);
    }
}
