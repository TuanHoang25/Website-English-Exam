package com.example.DoAnJava.Controller;

import com.example.DoAnJava.models.*;
import com.example.DoAnJava.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/practice")
@RequiredArgsConstructor
@Controller
public class PracticeController {
    private final ExamTestService examTestService;

    private final ExamQuestionService examQuestionService;

    private final ExamUserAnswerService examUserAnswerService;

    private final ExamTestResultService examTestResultService;

    private final UserService userService;

    @GetMapping
    public String listTests(@RequestParam(value = "searchString", required = false, defaultValue = "") String searchString,
                            @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size",defaultValue = "4") int size,
                            Model model, Principal principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            // Handle the case when the user is not logged ină
            Pageable pageable = PageRequest.of(page, size);
            Page<ExamTest> testsPage = examTestService.searchExamTest(searchString, pageable);

            model.addAttribute("testsPage", testsPage);
            return "practice/index"; // Show the page without user-specific data
        }

        User user = userService.findByUsername(principal.getName()).
                orElseThrow(() -> new IllegalArgumentException("Invalid username: " + principal.getName()));

        Pageable pageable = PageRequest.of(page, size);
        Page<ExamTest> testsPage = examTestService.searchExamTest(searchString, pageable);

        model.addAttribute("testsPage", testsPage);
        model.addAttribute("examTestResults", user.getExamTestResults());
        return "practice/index";
    }

    @GetMapping("/do-test/{id}/{mins}")
    public String showDoTestPage(@RequestParam("id") Integer id, @RequestParam("mins") Integer minutes, Model model ) {
        Optional<ExamTest> examTest = examTestService.findById(id);

        if (examTest.isPresent()) {
            model.addAttribute("examTest", examTest.get());
            model.addAttribute("mins", minutes);

            for (int part = 1; part <= 7; part++) {
                List<ExamQuestion> questionsOfPart = examTestService.findQuestionsByTestIdAndPart(id, part);
                model.addAttribute("questionsOfPart" + part, questionsOfPart);
            }
            return "practice/do-test"; // Replace with your actual Thymeleaf template for viewing a test
        } else {
            return "error/404"; // Replace with your actual 404 error page
        }
    }

    @PostMapping("/submit-answers")
    public String submitAnswers(@RequestParam Map<String, String> answers, @RequestParam Integer examTestId, Principal principal, Model model) {
        // Process answers


        System.out.println("test " + examTestId);
        System.out.println("Submitted answers: " + answers);

        // Initialize variables to track correct and incorrect answers
        int correctQuestions = 0; //so cau dung
        int incorrectQuestions = 0; //so cau sai
        int unChoosenQuestions = 0; // so cau chua lam

        // Retrieve the exam test from the database
        ExamTest examTest = examTestService.findById(examTestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid examTestId: " + examTestId));

        // Prepare a new ExamTestResult to save
        ExamTestResult newExamTestResult = new ExamTestResult();
        newExamTestResult.setCorrectQuestions(correctQuestions); // Initialize with correct answers count
        newExamTestResult.setIncorrectQuestions(incorrectQuestions);
        newExamTestResult.setUnchoosenQuestions(unChoosenQuestions); // Initialize with incorrect answers count
        newExamTestResult.setExamTest(examTest);
        String username = principal.getName();
        Long userId = userService.findUserIdByUsername(username);// Set the associated exam test
        newExamTestResult.setUser(userService.findByid(userId));
        // Save the ExamTestResult (you may need to adjust how you handle this part based on your service and repository setup)
        ExamTestResult savedExamTestResult = examTestResultService.save(newExamTestResult);

        // Process each question in the exam test
        for (ExamQuestion currentQuestion : examTest.getQuestions()) {
            String qusstionId = currentQuestion.getQuestionId().toString();
            String answer = answers.get("answers["+qusstionId+"]");

            // Create a new ExamTestResultDetail for each question
            ExamTestResultDetail resultDetail = new ExamTestResultDetail();
            resultDetail.setUserAnswer(answer);
            resultDetail.setQuestion(currentQuestion);
            resultDetail.setExamTestResult(savedExamTestResult); // Associate with the newly saved ExamTestResult

            // Save the ExamTestResultDetail (you may need to adjust how you handle this part based on your service and repository setup)
            examUserAnswerService.save(resultDetail);

            if(answer != null){
                if (currentQuestion.isCorrect(answer)) {
                    correctQuestions++;
                } else {
                    incorrectQuestions++;
                }
            }else{
                unChoosenQuestions++;
            }
            // Check if the answer is correct
        }
      /*  System.out.println("unchoosen questions: " + unChoosenQuestions);
        System.out.println("correct qustions: " + correctQuestions);
        System.out.println("incorrect qustions: " + incorrectQuestions);*/
        savedExamTestResult.setCorrectQuestions(correctQuestions); // update lại só câu sai số câu đúng, số câu chưa chọn
        savedExamTestResult.setIncorrectQuestions(incorrectQuestions);
        savedExamTestResult.setUnchoosenQuestions(unChoosenQuestions);
        examTestResultService.updateExamTestResult(savedExamTestResult);

        return "redirect:/practice/result/" + savedExamTestResult.getResultId();

    }

    @GetMapping("/result/{resultId}")
    public String getResultTest(@PathVariable("resultId") Integer resultId, Model model, Principal principal){

        ExamTestResult examTestResult = examTestResultService.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid resultId: " + resultId));

        ExamTest examTest = examTestResult.getExamTest();
        User user = userService.findByUsername(principal.getName()).
                orElseThrow(() -> new IllegalArgumentException("Invalid username: " + principal.getName()));

        if(!Objects.equals(user.getId(), examTestResult.getUser().getId())){
            return "error/404";
        }

        List<ExamTestResultDetail> examTestResultDetails = examTestResult.getExamTestResultDetails();
        List<ExamQuestion> examQuestions = new ArrayList<>(); //lưu câu trả lời của ngươi dùng
        for(ExamTestResultDetail index : examTestResultDetails){
            ExamQuestion newExamQuestion = index.getQuestion();
            newExamQuestion.setSelectedAnswer(index.getUserAnswer());
            examQuestions.add(newExamQuestion);
        }

        model.addAttribute("questions", examQuestions);
        model.addAttribute("examTest", examTest);
        model.addAttribute("examTestResult", examTestResult);
        model.addAttribute("totalQuestions", examTest.getQuestions().size());

        for (int part = 1; part <= 7; part++) {
            int part_temp = part;
            List<ExamQuestion> questionsOfPart = examQuestions.stream().filter(examQuestion -> examQuestion.getPart() == part_temp ).collect(Collectors.toList());
            model.addAttribute("questionsOfPart" + part, questionsOfPart);
        }

        return "practice/test-result";
    }
}
