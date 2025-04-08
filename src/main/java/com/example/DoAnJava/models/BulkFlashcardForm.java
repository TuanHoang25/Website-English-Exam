package com.example.DoAnJava.models;

import com.example.DoAnJava.models.FlashCard;
import lombok.Data;


import java.util.List;

@Data
public class BulkFlashcardForm {

    private List<FlashCard> cards;
}
