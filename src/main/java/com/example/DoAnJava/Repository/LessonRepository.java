package com.example.DoAnJava.Repository;

import com.example.DoAnJava.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findByExercisesId(Long exerciseId);
    List<Lesson> findByCategoryId(Long categoryId);
}