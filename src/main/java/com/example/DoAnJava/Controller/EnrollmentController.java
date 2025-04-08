package com.example.DoAnJava.Controller;

import com.example.DoAnJava.models.Course;
import com.example.DoAnJava.models.Enrollment;
import com.example.DoAnJava.models.User;
import com.example.DoAnJava.services.CourseService;
import com.example.DoAnJava.services.EnrollmentService;
import com.example.DoAnJava.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String showEnrollments(Model model) {
        // Lấy thông tin người dùng hiện tại
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username).orElse(null);
        if (user != null) {
            model.addAttribute("user", user);
        }

        // Lấy danh sách khóa học đã đăng ký và tổng giá
        List<Enrollment> enrollments = enrollmentService.getEnrollment();
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("totalPrice", enrollmentService.calculateTotalPrice());

        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);

        return "enrollment/enrollment-page";
    }
    @PostMapping("/pay")
    public String enrollCourse(Model model, @RequestParam(name = "courseId") Long courseId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username).orElse(null);

        if (user == null) {
            return "redirect:/error";
        }

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return "redirect:/error";
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setUser(user);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setIsActive(true);

        enrollmentService.saveEnrollment(enrollment);

        model.addAttribute("user", user);
        model.addAttribute("course", course);

        return "redirect:/enrollments/confirmation";
    }

    @GetMapping("/confirmation")
    public String showConfirmationPage(Model model) {
        model.addAttribute("message", "Bạn đã đăng ký khóa học của chúng tôi, chúng tôi sẽ gọi điện cho bạn để xác nhận và kích hoạt khóa học !!");
        return "enrollment/enrollment-confirmation";
    }
    @PostMapping("/add")
    public String addToEnrollments(@RequestParam Long courseId) {

        enrollmentService.enrollToCourse(courseId);

        return "redirect:/enrollments";
    }

    @GetMapping("/remove/{courseId}")
    public String removeFromEnrollments(@PathVariable Long courseId) {
        enrollmentService.removeEnrollment(courseId);

        return "redirect:/enrollments";
    }

    @GetMapping("/clear")
    public String clearEnrollments() {

        enrollmentService.clearEnrollments();
        return "redirect:/enrollments";
    }
}
