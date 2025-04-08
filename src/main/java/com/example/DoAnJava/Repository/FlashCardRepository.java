package com.example.DoAnJava.Repository;

import com.example.DoAnJava.models.FlashCard;

import com.example.DoAnJava.models.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
    List<FlashCard> findByListFlashCardsId(Long listId);
}
