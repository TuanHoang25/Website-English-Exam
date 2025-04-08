package com.example.DoAnJava.services;

import com.example.DoAnJava.Repository.ExamTestResultRepository;
import com.example.DoAnJava.models.Category;
import com.example.DoAnJava.models.ExamTestResult;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
@Transactional
public class ExamTestResultService {
    @Autowired
    private ExamTestResultRepository examTestResultRepository;

    public List<ExamTestResult> findAll() {
        return examTestResultRepository.findAll();
    }

    public Optional<ExamTestResult> findById(Integer id) {
        return examTestResultRepository.findById(id);
    }

    public ExamTestResult save(ExamTestResult examTestResult) {
        return examTestResultRepository.save(examTestResult);
    }

    public void updateExamTestResult(@NotNull ExamTestResult examTestResult) {
        ExamTestResult existingExamTestResult  = examTestResultRepository.findById(examTestResult.getResultId())
                .orElseThrow(() -> new IllegalStateException("ExamTestResult with ID " +
                        examTestResult.getResultId() + " does not exist."));
        existingExamTestResult.setUnchoosenQuestions(examTestResult.getUnchoosenQuestions());
        existingExamTestResult.setCorrectQuestions(examTestResult.getCorrectQuestions());
        existingExamTestResult.setIncorrectQuestions(examTestResult.getIncorrectQuestions());
        existingExamTestResult.setDoExamDate(examTestResult.getDoExamDate());
        existingExamTestResult.setUser(examTestResult.getUser());
        examTestResultRepository.save(existingExamTestResult);
    }

    public void deleteById(Integer id) {
        examTestResultRepository.deleteById(id);
    }
}
