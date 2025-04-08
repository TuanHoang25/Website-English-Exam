package com.example.DoAnJava.Controller;

import com.example.DoAnJava.models.BulkFlashcardForm;
import com.example.DoAnJava.models.FlashCard;
import com.example.DoAnJava.models.ListFlashCards;
import com.example.DoAnJava.services.FlashCardService;
import com.example.DoAnJava.services.ListFlashCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/flashcards")
public class FlashCardController {
    @Autowired
    private final FlashCardService cardService;
    @Autowired
    private final ListFlashCardService listFlashCardService;
    // Show form to add a new card
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("card", new FlashCard());
        return "flashcards/add-card";
    }

    // Process form submission to add a new card
    @PostMapping("/add")
    public String addCard(@Valid FlashCard card, BindingResult result) {
        if (result.hasErrors()) {
            return "flashcards/add-card";
        }
        cardService.addCard(card);
        return "redirect:/flashcards";
    }

    // Show all cards (Renamed method to avoid conflict)
    @GetMapping
    public String listAllCards(Model model) {
        List<FlashCard> cards = cardService.getAllCards();
        model.addAttribute("cards", cards);
        return "flashcards/cards-list";
    }

    // Show all cards in listFlashCards
    @GetMapping("/list/{id}")
    public String listCardsById(@PathVariable("id") Long id, Model model) {
        List<FlashCard> cards = cardService.findAllCardsByListId(id);
        Optional<ListFlashCards> listOptional = listFlashCardService.getListCardById(id);
        if (listOptional.isPresent()) {
            ListFlashCards list = listOptional.get();
            model.addAttribute("cards", cards);
            model.addAttribute("list", list);
        } else {
            // Handle the case where the list doesn't exist
            throw new IllegalArgumentException("Invalid list Id:" + id);
        }
        return "flashcards/cards-list";
    }


    // Show form to add a new card to a specific list
    @GetMapping("/list/{listId}/add")
    public String showAddCardForm(@PathVariable("listId") Long listId, Model model) {
        model.addAttribute("card", new FlashCard());
        model.addAttribute("listId", listId);
        return "flashcards/add-card";
    }

    // Process form submission to add a new card to a specific list
    @PostMapping("/list/{listId}/add")
    public String addCardToList(@PathVariable("listId") Long listId, @Valid FlashCard card, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listId", listId);
            return "flashcards/add-card";
        }
        Optional<ListFlashCards> list = listFlashCardService.getListCardById(listId);
        if (list.isPresent()) {
            card.setListFlashCards(list.get());
            cardService.addCard(card);
        } else {
            // Handle the case where the list doesn't exist
            throw new IllegalArgumentException("Invalid list Id:" + listId);
        }
        return "redirect:/flashcards/list/" + listId;
    }

    // Practice cards
    @GetMapping("/practice")
    public String practiceCards(Model model) {
        List<FlashCard> cards = cardService.getAllCards();
        model.addAttribute("cards", cards);
        return "flashcards/practice-card";
    }

    // Show form to update a card
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        FlashCard card = cardService.getCardById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid card Id:" + id));
        model.addAttribute("card", card);
        return "flashcards/update-card";
    }

    // Process form submission to update a card
    @PostMapping("/update/{id}")
    public String updateCard(@PathVariable("id") Long id, @Valid FlashCard card,
                             BindingResult result) {
        if (result.hasErrors()) {
            card.setId(id);
            return "flashcards/update-card";
        }
        cardService.updateCard(card);
        return "redirect:/listflashcards";
    }

    // Delete a card
    @GetMapping("/delete/{id}")
    public String deleteCard(@PathVariable("id") Long id) {
        cardService.deleteCardById(id);
        return "redirect:/flashcards/list/" + id;
    }

    @PostMapping("/list/{listid}/delete/{id}")
    public String deleteCardFromList(@PathVariable("listid") Long listid, @PathVariable("id") Long id) {
        cardService.deleteCardById(id);
        return "redirect:/flashcards/list/" + listid;
    }
    @GetMapping("/lists/{listId}/review")
    public String reviewFlashcards(@PathVariable("listId") Long listId, Model model) {
        model.addAttribute("listId", listId);
        return "flashcards/review";
    }

    @GetMapping("/list/{listId}/review/load")
    @ResponseBody
    public List<FlashCard> loadFlashcardsForReview(@PathVariable("listId") Long listId) {
        List<FlashCard> cards = cardService.findAllCardsByListId(listId);
        System.out.print(cards);
        Collections.shuffle(cards);
        return cards;
    }

    @PostMapping("/terms/{termId}/review/action")
    @ResponseBody
    public String reviewAction(@PathVariable("termId") Long termId, @RequestParam("action") double action) {
        // Handle the review action (e.g., update review statistics, etc.)
        // For simplicity, we'll just return a success message
        return "success";
    }


    // Show form to add bulk flashcards
    @GetMapping("/list/{listId}/add-bulk")
    public String showAddBulkForm(@PathVariable("listId") Long listId, Model model) {
        model.addAttribute("listId", listId);
        model.addAttribute("bulkFlashcardForm", new BulkFlashcardForm());
        return "flashcards/add-bulk";
    }

    @PostMapping("/list/{listId}/add-bulk")
    public String addBulkFlashcards(@PathVariable("listId") Long listId, @ModelAttribute @Valid BulkFlashcardForm bulkFlashcardForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listId", listId);
            return "flashcards/add-bulk";
        }

        // Retrieve the list of flashcards from the form
        List<FlashCard> cards = bulkFlashcardForm.getCards();

        // Process each flashcard
        for (FlashCard card : cards) {
            Optional<ListFlashCards> list = listFlashCardService.getListCardById(listId);
            if (list.isPresent()) {
                card.setListFlashCards(list.get());
                cardService.addCard(card);
            } else {
                throw new IllegalArgumentException("Invalid list Id:" + listId);
            }
        }

        return "redirect:/flashcards/list/" + listId;
    }

    // Show form to update a card in a specific list
    @GetMapping("/list/{listid}/edit/{id}")
    public String showFormEdit(@PathVariable("listid") Long listId, @PathVariable("id") Long id, Model model) {
        FlashCard card = cardService.getCardById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid card Id:" + id));
        model.addAttribute("card", card);
        model.addAttribute("listId", listId);
        return "flashcards/update-card";
    }

    // Process form submission to update a card in a specific list
    @PostMapping("/list/{listid}/edit/{id}")
    public String updateCardFromList(@PathVariable("listid") Long listId, @PathVariable("id") Long id, @Valid FlashCard card,
                                     BindingResult result) {
        if (result.hasErrors()) {
            card.setId(id);
            return "flashcards/update-card";
        }
        cardService.updateCard(card);
        return "redirect:/flashcards/list/" + listId;
    }
}
