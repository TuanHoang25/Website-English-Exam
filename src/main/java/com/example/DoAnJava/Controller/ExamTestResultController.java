package com.example.DoAnJava.Controller;


import com.example.DoAnJava.models.ExamTestResultDetail;
import com.example.DoAnJava.services.ExamUserAnswerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/result")
public class ExamTestResultController {

    @Autowired
    private final ExamUserAnswerService examUserAnswerService;


    @GetMapping()
    public ResponseEntity<List<ExamTestResultDetail>> submitAnswers() {

        return new ResponseEntity<>(examUserAnswerService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/submit-answers")
    public ResponseEntity<String> submitAnswers(@RequestBody Map<String, String> answers, @RequestParam Integer examTestId) {
        // Xử lý logic submit câu trả lời tại đây
        System.out.println("Received answers: " + answers +"and test id: " + examTestId);
        return ResponseEntity.ok("Answers submitted successfully");
    }
}
