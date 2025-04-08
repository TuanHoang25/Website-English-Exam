package com.example.DoAnJava.Controller;

import com.example.DoAnJava.models.Course;
import com.example.DoAnJava.services.CourseService;
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

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Hiển thị danh sách tất cả các khóa học
    @GetMapping("/courses")
    public String getAllCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses/course-list";
    }
    @GetMapping("/admin/courses")
    public String listCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses/course-manage";
    }
    // Hiển thị thông tin chi tiết của một khóa học theo id
    @GetMapping("/courses/{id}")
    public String getCourseById(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        List<Course> relatedCourses = courseService.getRelatedCourses(id);
        model.addAttribute("relatedCourses", relatedCourses);
        return "courses/course-detail";
    }
    @GetMapping("/admin/courses/add")
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        return "courses/add-course";
    }
    @PostMapping("/admin/courses/add")
    public String addCourse(@Valid Course course, BindingResult result, @RequestParam("imageUrl") MultipartFile file) {
        // Xử lý upload hình ảnh nếu có
        if (!file.isEmpty()) {
            try {
                // Lưu hình ảnh vào thư mục static/images trong resources
                byte[] bytes = file.getBytes();
                Path path = Paths.get("src/main/resources/static/images/" + file.getOriginalFilename());
                Files.write(path, bytes);
                course.setImageUrl(file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/error-page"; // Điều hướng đến trang lỗi nếu có lỗi trong quá trình upload hình ảnh
            }
        }

        courseService.addCourse(course);
        return "redirect:/admin/courses";
    }
    // For editing a product
    @GetMapping("/admin/courses/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "courses/update-course";
    }
    @PostMapping("/admin/courses/update/{id}")
    public String updateCourse(@PathVariable Long id, @Valid Course course, BindingResult result, @RequestParam("imageUrl") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get("src/main/resources/static/images/" + file.getOriginalFilename());
                Files.write(path, bytes);
                course.setImageUrl(file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Preserve the existing image if no new image is uploaded
            Course existingCourse = courseService.getCourseById(id);
            course.setImageUrl(existingCourse.getImageUrl());
        }

        courseService.updateCourse(course);
        return "redirect:/admin/courses";
    }
    // Handle request to delete a product
    @GetMapping("/admin/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourseById(id);
        return "redirect:/admin/courses";
    }
}
