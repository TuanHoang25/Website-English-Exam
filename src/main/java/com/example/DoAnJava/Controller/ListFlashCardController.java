package com.example.DoAnJava.Controller;

import com.example.DoAnJava.models.ListFlashCards;
import com.example.DoAnJava.services.ListFlashCardService;
import com.example.DoAnJava.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/listflashcards")
public class ListFlashCardController {

    private final ListFlashCardService listFlashCardService;
    private final UserService userService;


    // Show form to add a new list of flashcards
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("listFlashCard", new ListFlashCards());
        return "listflashcards/add-list";
    }

    // Process form submission to add a new list of flashcards
    @PostMapping("/add")
    public String addListFlashCard(@Valid ListFlashCards listFlashCard, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "listflashcards/add-list";
        }
        String username = principal.getName();
        Long userId = userService.findUserIdByUsername(username);
        listFlashCard.setUser(userService.findByid(userId));
        listFlashCardService.addListFlashCard(listFlashCard);
        return "redirect:/listflashcards";
    }

    // Process form submission to add a new list of flashcards

    // Show all lists of flashcards
    @GetMapping
    public String listListFlashCards(Model model, Principal principal) {
        String username = principal.getName();
        Long userId = userService.findUserIdByUsername(username);
        List<ListFlashCards> lists = listFlashCardService.getAllListFlashCardsByUserId(userId);
        model.addAttribute("lists", lists);
        model.addAttribute("listFlashCard", new ListFlashCards());
        return "listflashcards/flashcards-list";
    }


    // Show form to update a list of flashcards
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        ListFlashCards listFlashCard = listFlashCardService.getListCardById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid list Id:" + id));
        model.addAttribute("listFlashCard", listFlashCard);
        return "listflashcards/update-list";
    }

    // Process form submission to update a list of flashcards
    @PostMapping("/update/{id}")
    public String updateListFlashCard(@PathVariable("id") Long id,
                                      @Valid ListFlashCards listFlashCard,
                                      BindingResult result,
                                      Model model) {
        if (result.hasErrors()) {
            listFlashCard.setId(id);
            // Add listFlashCard to model to bind errors in Thymeleaf
            model.addAttribute("listFlashCard", listFlashCard);
            return "listflashcards/update-list";
        }
        listFlashCardService.updateListCard(listFlashCard);
        return "redirect:/listflashcards";
    }

    // Delete a list of flashcards
    @GetMapping("/delete/{id}")
    public String deleteListFlashCard(@PathVariable("id") Long id, Model model) {
        listFlashCardService.deleteListCardById(id);
        return "redirect:/listflashcards";
    }
}
