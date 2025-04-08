package com.example.DoAnJava.services;

import com.example.DoAnJava.Repository.ExamQuestionRepository;
import com.example.DoAnJava.Repository.ExamTestRepository;
import com.example.DoAnJava.models.ExamQuestion;
import com.example.DoAnJava.models.ExamTest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExamTestService {
    @Autowired
    private ExamTestRepository examTestRepository;

    @Autowired
    private ExamQuestionRepository examQuestionRepository;
    public List<ExamTest> findAll() {
        return examTestRepository.findAll();
    }

    public Optional<ExamTest> findById(Integer id) {
        return examTestRepository.findById(id);
    }

    public ExamTest save(ExamTest examTest) {
        return examTestRepository.save(examTest);
    }

    public void deleteById(Integer id) {
        examTestRepository.deleteById(id);
    }
    public List<ExamQuestion> findQuestionsByTestIdAndPart(Integer testId, int part) {
        return examQuestionRepository.findByTestIdAndPart(testId, part);
    }

    public Page<ExamTest> searchExamTest(String searchString, Pageable pageable) {
        if (searchString != null && !searchString.isEmpty()) {
            return examTestRepository.findByTitleContainingIgnoreCase(searchString, pageable);
        }
        return examTestRepository.findAll(pageable);
    }
}

