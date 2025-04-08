package com.example.DoAnJava.services;

import com.example.DoAnJava.Repository.ExamQuestionRepository;
import com.example.DoAnJava.models.ExamQuestion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExamQuestionService {
    @Autowired
    private ExamQuestionRepository examQuestionRepository;

    public List<ExamQuestion> findAllE() {
        return examQuestionRepository.findAll();
    }

    public Optional<ExamQuestion> findById(Integer id) {
        return examQuestionRepository.findById(id);
    }

    public ExamQuestion save(ExamQuestion examQuestion) {
        return examQuestionRepository.save(examQuestion);
    }

    public void deleteById(Integer id) {
        examQuestionRepository.deleteById(id);
    }


}
