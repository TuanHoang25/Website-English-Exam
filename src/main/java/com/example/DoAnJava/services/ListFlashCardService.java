package com.example.DoAnJava.services;

import com.example.DoAnJava.Repository.ListFlashCardRepository;
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
public class ListFlashCardService {
    private final ListFlashCardRepository listcardRepository;
    private final UserService userService;

    public List<ListFlashCards> getAllListCards() {
        return listcardRepository.findAll();
    }

    public Optional<ListFlashCards> getListCardById(Long id) {
        return listcardRepository.findById(id);
    }

    public void addListFlashCard(ListFlashCards category) {
        listcardRepository.save(category);
    }

    public void updateListCard(@NotNull ListFlashCards card) {
        ListFlashCards existingCard = listcardRepository.findById(card.getId())
                .orElseThrow(() -> new IllegalStateException("Card with ID " +
                        card.getId() + " does not exist."));
        existingCard.setTitle(card.getTitle());
        existingCard.setDescription(card.getDescription());
        listcardRepository.save(existingCard);
    }

    public void deleteListCardById(Long id) {
        if (!listcardRepository.existsById(id)) {
            throw new IllegalStateException("Card with ID " + id + " does not exist.");
        }
        listcardRepository.deleteById(id);
    }

    public List<ListFlashCards> getAllListFlashCardsByUserId(Long userId) {
        return listcardRepository.findAllByUserId(userId);
    }
}
