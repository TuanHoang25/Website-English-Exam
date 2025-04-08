package com.example.DoAnJava.Repository;

import com.example.DoAnJava.models.ExamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Integer> {
    @Query("SELECT q FROM ExamQuestion q WHERE q.examTest.id = :testId AND q.part = :part")
    List<ExamQuestion> findByTestIdAndPart(@Param("testId") Integer testId, @Param("part") int part);
}