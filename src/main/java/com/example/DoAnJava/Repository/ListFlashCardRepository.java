package com.example.DoAnJava.Repository;

import com.example.DoAnJava.models.ListFlashCards;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListFlashCardRepository extends JpaRepository<ListFlashCards, Long> {
    List<ListFlashCards> findAllByUserId(Long userId);
}
