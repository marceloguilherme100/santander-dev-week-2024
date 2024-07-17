package me.dio.santanderdevweek2023.service;

import me.dio.santanderdevweek2023.exceptions.NoSuchElementException;
import me.dio.santanderdevweek2023.model.Account;
import me.dio.santanderdevweek2023.model.Card;
import me.dio.santanderdevweek2023.model.CardType;
import me.dio.santanderdevweek2023.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CardServiceTest {

    public static final long NUMBER = 1L;
    public static final CardType CARD_TYPE = CardType.DEBIT;
    public static final double LIMIT = 0.0;
    public static final Account ACCOUNT = new Account();
    @InjectMocks
    CardService service;

    @Mock
    CardRepository repository;
    private Optional<Card> optionalCard;
    private Card card;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCard();
    }

    @Test
    void whenFindAllCardThenReturnAListOfCard() {
        when(repository.findAll()).thenReturn(List.of(card));

        List<Card> response = service.findAllCards();

        assertNotNull(response);
        assertEquals(Card.class, response.get(0).getClass());
        assertEquals(1, response.size());
        assertEquals(NUMBER, response.get(0).getNumber());
        assertEquals(CARD_TYPE, response.get(0).getType());
        assertEquals(LIMIT, response.get(0).getLimit());
    }


    @Test
    void whenFindCardByIdThenReturnAnInstanceOfCard() {
        when(repository.findById(any())).thenReturn(optionalCard);

        Card response = service.findCardById(any());

        assertNotNull(response);
        assertEquals(Card.class, response.getClass());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(CARD_TYPE, response.getType());
        assertEquals(ACCOUNT, response.getAccount());
        assertEquals(LIMIT, response.getLimit());
    }

    @Test
    void whenFindCardByIdThenReturnNoSuchElementException() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findCardById(any()));
    }


    @Test
    void whenSaveCardThenReturnSuccess() {
        when(repository.save(card)).thenReturn(card);

        Card response = service.saveCard(card);

        assertNotNull(response);
        verify(repository, times(1)).save(card);
        assertEquals(Card.class, response.getClass());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(CARD_TYPE, response.getType());
        assertEquals(ACCOUNT, response.getAccount());
        assertEquals(LIMIT, response.getLimit());
    }

    @Test
    void whenUpdateCardThenReturnSuccess() {
        when(repository.findById(any())).thenReturn(optionalCard);
        when(repository.save(card)).thenReturn(card);

        Card response = service.updateCard(any(), card);

        assertNotNull(response);
        assertEquals(Card.class, response.getClass());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(CARD_TYPE, response.getType());
        assertEquals(ACCOUNT, response.getAccount());
        assertEquals(LIMIT, response.getLimit());
    }

    @Test
    void whenUpdateCardThenReturnNoSuchElementException() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.updateCard(any(), card));
    }

    @Test
    void deleteCardThenReturnSuccess() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        doNothing().when(repository).deleteById(any());
        boolean response = service.deleteCard(any());
        verify(repository, times(1)).deleteById(any());
        assertTrue(response);
    }


    private void startCard() {
        card = new Card(NUMBER, CARD_TYPE, LIMIT, ACCOUNT);
        optionalCard = Optional.of(card);
    }
}