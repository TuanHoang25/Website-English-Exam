package com.example.DoAnJava.Controller;

import com.example.DoAnJava.models.Exercise;
import com.example.DoAnJava.models.Lesson;
import com.example.DoAnJava.services.CategoryService;
import com.example.DoAnJava.services.LessonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Controller
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/lessons/category/{categoryId}")
    public String getLessonsByCategory(@PathVariable Long categoryId, Model model) {
        List<Lesson> lessons = lessonService.getLessonsByCategoryId(categoryId);
        model.addAttribute("lessons", lessons);
        return "lessons/lesson-list";
    }


    @GetMapping("/lessons/{id}")
    public String getLessonById(@PathVariable Long id, Model model) {
        Lesson lesson = lessonService.getLessonById(id);
        if (lesson != null) {
            model.addAttribute("lesson", lesson);
            model.addAttribute("exercises", lesson.getExercises());
        }
        return "lessons/lesson-detail";
    }

    @PostMapping("/lessons/submit")
    public String submitAllAnswers(@RequestParam Long lessonId,
                                   @RequestParam Map<String, String> params,
                                   Model model) {
        // Get the lesson and its exercises
        Lesson lesson = lessonService.getLessonById(lessonId);
        List<Exercise> exercises = lesson.getExercises();

        // Add lesson and exercises to the model
        model.addAttribute("lesson", lesson);
        model.addAttribute("exercises", exercises);

        // Add the submitted answers to the model
        model.addAttribute("submittedAnswers", params);

        return "lessons/lesson-detail";
    }
    @GetMapping("/admin/lessons")
    public String listCourses(Model model) {
        List<Lesson> lessons = lessonService.getAllLessons();
        model.addAttribute("lessons", lessons);
        return "lessons/lesson-manage";
    }
    @GetMapping("/admin/lessons/add")
    public String showAddForm(Model model) {
        model.addAttribute("lesson", new Lesson());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "lessons/add-lesson";
    }
    @PostMapping("/admin/lessons/add")
    public String addLesson(@Valid Lesson lesson, BindingResult result, @RequestParam("imageUrl") MultipartFile file) {
        // Xử lý upload hình ảnh nếu có
        if (!file.isEmpty()) {
            try {
                // Lưu hình ảnh vào thư mục static/images trong resources
                byte[] bytes = file.getBytes();
                Path path = Paths.get("src/main/resources/static/images/" + file.getOriginalFilename());
                Files.write(path, bytes);
                lesson.setImageUrl(file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/error-page"; // Điều hướng đến trang lỗi nếu có lỗi trong quá trình upload hình ảnh
            }
        }

        lessonService.addLesson(lesson);
        return "redirect:/admin/lessons";
    }
    // For editing a product
    @GetMapping("/admin/lessons/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Lesson lesson = lessonService.getLessonById(id);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("lesson", lesson);
        return "lessons/update-lesson";
    }
    @PostMapping("/admin/lessons/update/{id}")
    public String updateLesson(@PathVariable Long id, @Valid Lesson lesson, BindingResult result, @RequestParam("imageUrl") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get("src/main/resources/static/images/" + file.getOriginalFilename());
                Files.write(path, bytes);
                lesson.setImageUrl(file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Preserve the existing image if no new image is uploaded
            Lesson existingLesson = lessonService.getLessonById(id);
            lesson.setImageUrl(existingLesson.getImageUrl());
        }

        lessonService.updateLesson(lesson);
        return "redirect:/admin/lessons";
    }
    // Handle request to delete a product
    @GetMapping("/admin/lessons/delete/{id}")
    public String deleteLesson(@PathVariable Long id) {
        lessonService.deleteLessonById(id);
        return "redirect:/admin/lessons";
    }
}
