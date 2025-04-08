package com.example.DoAnJava.Repository;

import com.example.DoAnJava.models.ExamTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import java.util.List;

@Repository
public interface ExamTestRepository extends JpaRepository<ExamTest, Integer> {
    Page<ExamTest> findByTitleContainingIgnoreCase(String title, Pageable pageable);

}