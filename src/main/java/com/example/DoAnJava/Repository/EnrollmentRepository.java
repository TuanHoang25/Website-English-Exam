package com.example.DoAnJava.Repository;

import com.example.DoAnJava.models.Enrollment;
import com.example.DoAnJava.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUser(User user);
}