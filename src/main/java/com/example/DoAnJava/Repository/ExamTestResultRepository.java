package com.example.DoAnJava.Repository;

import com.example.DoAnJava.models.ExamTestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamTestResultRepository extends JpaRepository<ExamTestResult, Integer> {
}