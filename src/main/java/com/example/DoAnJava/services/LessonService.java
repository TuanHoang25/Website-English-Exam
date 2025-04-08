package com.example.DoAnJava.services;

import com.example.DoAnJava.Repository.ExerciseRepository;
import com.example.DoAnJava.Repository.LessonRepository;
import com.example.DoAnJava.models.Exercise;
import com.example.DoAnJava.models.Lesson;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    public Exercise getExerciseById(Long id) {
        return exerciseRepository.findById(id).orElse(null);
    }

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }
    public Lesson getLessonByExerciseId(Long exerciseId) {
        return lessonRepository.findByExercisesId(exerciseId);
    }
    public List<Lesson> getLessonsByCategoryId(Long categoryId) {
        return lessonRepository.findByCategoryId(categoryId);
    }

    public Lesson saveLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson addLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }
    public Lesson updateLesson(@NotNull Lesson lesson) {
        Lesson existingLesson = lessonRepository.findById(lesson.getId()).orElseThrow(() -> new IllegalStateException("Lesson with ID " + lesson.getId() + " does not exist."));
        existingLesson.setTitle(lesson.getTitle());
        existingLesson.setContent(lesson.getContent());
        existingLesson.setDescription(lesson.getDescription());
        existingLesson.setImageUrl(lesson.getImageUrl());
        existingLesson.setCategory(lesson.getCategory());
        return lessonRepository.save(existingLesson);
    }
    public void deleteLessonById(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new IllegalStateException("Lesson with ID " + id + " does not exist.");
        }
        lessonRepository.deleteById(id);
    }
}
