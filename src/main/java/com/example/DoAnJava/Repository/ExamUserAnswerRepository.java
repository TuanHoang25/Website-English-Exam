package com.example.DoAnJava.Repository;

import com.example.DoAnJava.models.ExamTestResultDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamUserAnswerRepository extends JpaRepository<ExamTestResultDetail, Long> {

}
