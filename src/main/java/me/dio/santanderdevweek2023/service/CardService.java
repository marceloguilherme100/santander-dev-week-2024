package me.dio.santanderdevweek2023.service;

import me.dio.santanderdevweek2023.dto.CardDTO;
import me.dio.santanderdevweek2023.exceptions.NoSuchElementException;
import me.dio.santanderdevweek2023.model.Card;
import me.dio.santanderdevweek2023.repository.CardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CardService {
    @Autowired
    CardRepository repository;

    @Autowired
    ModelMapper modelMapper;

    public List<Card> findAllCards(){
        return repository.findAll();
    }

    public Card findCardById(UUID id){
        Optional<Card> CardFound = repository.findById(id);
        return CardFound.orElseThrow(() -> new NoSuchElementException(id, "Card"));
    }

    public List<CardDTO> findCardsByCPF(String cpf){
        List<Card> cardsFound = repository.findCardsByCPF(cpf);
        List<CardDTO> cardsDTO = cardsFound.stream()
                .map(card -> modelMapper.map(card, CardDTO.class))
                .collect(Collectors.toList());
        return cardsDTO;
    }

    public Card saveCard(Card Card){
        return repository.save(Card);
    }

    public Card updateCard(UUID id, Card Card){
        Optional<Card> CardFound = repository.findById(id);
        if(CardFound.isPresent()) {
            return repository.save(Card);
        }
        else {
            throw new NoSuchElementException(id, "Card");
        }
    }
    public boolean deleteCard(UUID id){
        repository.deleteById(id);
        Optional<Card> CardDelete = repository.findById(id);
        return CardDelete.isEmpty();
    }

}
