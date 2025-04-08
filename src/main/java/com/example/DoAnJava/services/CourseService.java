package com.example.DoAnJava.services;

import com.example.DoAnJava.Repository.CourseRepository;
import com.example.DoAnJava.models.Course;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    private List<Course> courses = new ArrayList<>();
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }
    public List<Course> getCourses() {
        return courses ;
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }
    // Phương thức để lấy danh sách các khóa học liên quan (ví dụ: 3 khóa học ngẫu nhiên)
    public List<Course> getRelatedCourses(Long courseId) {
        // Thực hiện logic để lấy danh sách các khóa học liên quan (ví dụ: cùng danh mục, cùng chủ đề, ...)
        // Tạm thời làm mẫu với việc lấy 3 khóa học ngẫu nhiên
        return courseRepository.findTop3ByOrderByCreatedByDesc();
    }
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }
    public Course updateCourse(@NotNull Course course) {
        Course existingCourse = courseRepository.findById(course.getId()).orElseThrow(() -> new IllegalStateException("Course with ID " + course.getId() + " does not exist."));
        existingCourse.setTitle(course.getTitle());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setStudy_program(course.getStudy_program());
        existingCourse.setPrice(course.getPrice());
        existingCourse.setImageUrl(course.getImageUrl());
        return courseRepository.save(existingCourse);
    }
    // Delete a product by its id
    public void deleteCourseById(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new IllegalStateException("Course with ID " + id + " does not exist.");
        }
        courseRepository.deleteById(id);
    }
}
