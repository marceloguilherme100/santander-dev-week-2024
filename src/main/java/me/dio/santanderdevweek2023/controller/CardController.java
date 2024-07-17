package me.dio.santanderdevweek2023.controller;

import me.dio.santanderdevweek2023.dto.CardDTO;
import me.dio.santanderdevweek2023.model.Card;
import me.dio.santanderdevweek2023.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    CardService service;

    @GetMapping
    public ResponseEntity<List<Card>> findAllCards(){
        return ResponseEntity.ok(service.findAllCards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> findAllCards(@PathVariable("id") UUID id){
        return ResponseEntity.ok(service.findCardById(id));
    }
    @GetMapping("/client/{cpf}")
    public ResponseEntity<List<CardDTO>> findCardsByCPF(@PathVariable("cpf") String cpf){
        return ResponseEntity.ok(service.findCardsByCPF(cpf));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable("id") UUID id, @RequestBody Card card){
        service.updateCard(id, card);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping
    public ResponseEntity<Card> saveCard( @RequestBody Card card){
        service.saveCard(card);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardById(@PathVariable("id") UUID id){
        boolean isCardDeleted = service.deleteCard(id);
        return ResponseEntity.status(isCardDeleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND).build();
    }

}
