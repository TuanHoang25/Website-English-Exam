package com.example.DoAnJava.Controller;

import com.example.DoAnJava.importer.ExamQuestionImporter;
import com.example.DoAnJava.models.ExamQuestion;
import com.example.DoAnJava.models.ExamTest;
import com.example.DoAnJava.services.ExamQuestionService;
import com.example.DoAnJava.services.ExamTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RequestMapping("/admin/tests")
@RequiredArgsConstructor
@Controller
public class ExamTestController {
    private final ExamTestService examTestService;

    private final ExamQuestionService examQuestionService;

    private final ExamQuestionImporter examQuestionImporter;
    @GetMapping
    public String listTests(Model model) {
        model.addAttribute("tests",examTestService.findAll());
        return "tests/list-tests";
    }

    @GetMapping("/create")
    public String showCreateTestForm(Model model) {
        model.addAttribute("examTest", new ExamTest());
        return "tests/create-test";
    }

    @GetMapping("/edit/{examTestId}")
    public String showEditTestForm(@PathVariable("examTestId") Integer examTestId,Model model) {
        ExamTest examTest = examTestService.findById(examTestId)
                        . orElseThrow(() -> new IllegalArgumentException("Invalid examTestId: " + examTestId));


        model.addAttribute("examTest", examTest);

        return "tests/edit-test";
    }

    @PostMapping("/edit")
    public String editTest(@ModelAttribute("examTest") ExamTest examTest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("examTest", examTest);
            return "admin/editTest"; // Return to the edit form with errors
        }

        ExamTest existingExamTest = examTestService.findById(examTest.getExamTestId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid examTestId: " + examTest.getExamTestId()));

        existingExamTest.setTitle(examTest.getTitle());
        // Update other fields as necessary

        examTestService.save(existingExamTest);
        return "redirect:/admin/tests";
    }


    @GetMapping("/edit-qst/{quesstionId}")
    public String showEditForm(@PathVariable("quesstionId") Integer quesstionId, Model model) {
        ExamQuestion examQuestion = examQuestionService.findById(quesstionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question Id:" + quesstionId));
        model.addAttribute("examQuestion", examQuestion);
        return "tests/edit-question";
    }

    @PostMapping("/edit-qst")
    public String updateQuestion(@ModelAttribute("examQuestion") ExamQuestion examQuestion, BindingResult result, Model model) {
        ExamQuestion existingQuestion = examQuestionService.findById(examQuestion.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid question Id:" + examQuestion.getQuestionId()));

        // Update the existing question with the new values

        existingQuestion.setQuestion(examQuestion.getQuestion());
        existingQuestion.setOptionA(examQuestion.getOptionA());
        existingQuestion.setOptionB(examQuestion.getOptionB());
        existingQuestion.setOptionC(examQuestion.getOptionC());
        existingQuestion.setOptionD(examQuestion.getOptionD());
        existingQuestion.setCorrectAnswer(examQuestion.getCorrectAnswer());
        existingQuestion.setScript(examQuestion.getScript());

        examQuestionService.save(existingQuestion);
        return "redirect:/admin/tests";
    }

    @PostMapping("/create")
    public String createTest(@ModelAttribute("examTest") ExamTest examTest,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("imageFiles") MultipartFile[] imageFiles,
                             @RequestParam("audioFiles") MultipartFile[] audioFiles,
                             Model model) {
        // Save the ExamTest entity
        ExamTest savedExamTest = examTestService.save(examTest);

        // Process the uploaded file and add questions
        try {
            String filePath = saveUploadedFile(file);
            examQuestionImporter.importExcelFile(filePath, savedExamTest.getExamTestId());

            // Process the uploaded image files
            for (MultipartFile imageFile : imageFiles) {
                saveUploadedResourceFiles(imageFile, "static/images/test-images//"+savedExamTest.getExamTestId());
            }

            // Process the uploaded audio files
            for (MultipartFile audioFile : audioFiles) {
                saveUploadedResourceFiles(audioFile, "static/audio/test-audio//"+savedExamTest.getExamTestId());
            }

            return "redirect:/admin/tests";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while creating the test. Please try again.");
            return "tests/create-test";
        }


    }

    @PostMapping("/delete/{id}")
    public String deleteTest(@PathVariable("id") Integer id, Model model) {
        try {
            Optional<ExamTest> examTestOptional = examTestService.findById(id);
            if (examTestOptional.isPresent()) {
                ExamTest examTest = examTestOptional.get();

                // Delete image folder
                deleteDirectoryRecursively(Paths.get("src/main/resources/static/images/test-images/" + examTest.getExamTestId()));

                // Delete audio folder
                deleteDirectoryRecursively(Paths.get("src/main/resources/static/audio/test-audio/" + examTest.getExamTestId()));

                // Delete ExamTest entity
                examTestService.deleteById(id);
            }
            return "redirect:/admin/tests";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while deleting the test. Please try again.");
            return "tests/list-tests";
        }
    }

    private String saveUploadedFile(MultipartFile file) throws IOException {
        String filePath = System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename();
        file.transferTo(new java.io.File(filePath));
        return filePath;
    }

    private void saveUploadedResourceFiles(MultipartFile file, String folderName) throws IOException {
        Path directory = Paths.get("src/main/resources", folderName);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
        Path filePath = directory.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath);
    }

    private void deleteDirectoryRecursively(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path)
                    .sorted((path1, path2) -> path2.compareTo(path1)) // Sort in reverse order
                    .forEach(filePath -> {
                        try {
                            Files.delete(filePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
    /*private String updateFile(String uploadDir, MultipartFile file) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not save file: " + file.getOriginalFilename(), e);
        }
    }*/


}
