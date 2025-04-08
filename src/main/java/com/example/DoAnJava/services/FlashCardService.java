package com.example.DoAnJava.services;


import com.example.DoAnJava.Repository.FlashCardRepository;

import com.example.DoAnJava.models.FlashCard;
import com.example.DoAnJava.models.ListFlashCards;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FlashCardService {
    private final FlashCardRepository cardRepository;
    /**
     * Retrieve all categories from the database.
     * @return a list of categories
     */
    public List<FlashCard> getAllCards() {
        return cardRepository.findAll();
    }
    /**
     * Retrieve a category by its id.
     * @param id the id of the category to retrieve
     * @return an Optional containing the found category or empty if not found
     */
    public Optional<FlashCard> getCardById(Long id) {
        return cardRepository.findById(id);
    }
    /**
     * Add a new category to the database.
     * @param category the category to add
     */
    public void addCard(FlashCard category) {
        cardRepository.save(category);
    }
    /**
     * Update an existing category.
     * @param card the category with updated information
     */
    public void updateCard(@NotNull FlashCard card) {
        FlashCard existingCard = cardRepository.findById(card.getId())
                .orElseThrow(() -> new IllegalStateException("Card with ID " +
                        card.getId() + " does not exist."));
        existingCard.setQuestion(card.getQuestion());
        existingCard.setAnswer(card.getAnswer());
        cardRepository.save(existingCard);
    }
    public void deleteCardById(Long id) {
        if (!cardRepository.existsById(id)) {
            throw new IllegalStateException("Card with ID " + id + " does not exist.");
        }
        cardRepository.deleteById(id);
    }

    public List<FlashCard> findAllCardsByListId(Long listId){
        return cardRepository.findByListFlashCardsId(listId);
    }
}
