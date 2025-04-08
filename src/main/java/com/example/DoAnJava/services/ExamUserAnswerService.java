package com.example.DoAnJava.services;

import com.example.DoAnJava.Repository.ExamUserAnswerRepository;
import com.example.DoAnJava.models.ExamTestResultDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExamUserAnswerService {
    @Autowired
    private ExamUserAnswerRepository examUserAnswerRepository;


    public List<ExamTestResultDetail> findAll() {
        return examUserAnswerRepository.findAll();
    }

    public Optional<ExamTestResultDetail> findById(Long id) {
        return examUserAnswerRepository.findById(id);
    }

    public ExamTestResultDetail save(ExamTestResultDetail examUserAnswer) {
        return examUserAnswerRepository.save(examUserAnswer);
    }

    public void deleteById(Long id) {
        examUserAnswerRepository.deleteById(id);
    }

}
