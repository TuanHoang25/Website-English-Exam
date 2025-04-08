package com.example.DoAnJava.Repository;

import com.example.DoAnJava.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findTop3ByOrderByCreatedByDesc();
}
