package com.example.DoAnJava.services;

import com.example.DoAnJava.Repository.CourseRepository;
import com.example.DoAnJava.Repository.EnrollmentRepository;
import com.example.DoAnJava.Repository.UserRepository;
import com.example.DoAnJava.models.Course;
import com.example.DoAnJava.models.Enrollment;
import com.example.DoAnJava.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private List<Enrollment> enrollment = new ArrayList<>();
    public boolean enrollToCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));

        // Kiểm tra xem course có bị null hay không
        if (course == null) {
            throw new IllegalArgumentException("Course is null for courseId: " + courseId);
        }

        // Tiếp tục xử lý khi course không null
        for (Enrollment en : enrollment) {
            // Kiểm tra null cho course trong enrollment
            if (en.getCourse() != null && en.getCourse().getId().equals(courseId)) {
                return false;
            }
        }

        // Thêm mới Enrollment vào danh sách
        enrollment.add(new Enrollment(course));
        return true;
    }
    public List<Enrollment> getEnrollmentsByUser(User user) {
        return enrollmentRepository.findByUser(user);
    }
    // Lấy danh sách các Enrollment
    public List<Enrollment> getEnrollments() {
        return enrollmentRepository.findAll();
    }

    // Lưu Enrollment vào cơ sở dữ liệu
    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getEnrollment() {
        return enrollment;
    }

    public void removeEnrollment(Long courseId) {
        enrollment.removeIf(en -> en.getCourse().getId().equals(courseId));
    }

    public void clearEnrollments() {
        enrollment.clear();
    }
    public double calculateTotalPrice() {
        return enrollment.stream()
                .filter(en -> en.getCourse() != null) // Lọc bỏ những Enrollment có Course là null
                .mapToDouble(en -> en.getCourse().getPrice()) // Lấy giá của từng Course
                .sum(); // Tính tổng giá
    }
}
