package me.dio.santanderdevweek2023.service;

import me.dio.santanderdevweek2023.dto.AccountDTO;
import me.dio.santanderdevweek2023.dto.AccountResponseDTO;
import me.dio.santanderdevweek2023.dto.CardDTO;
import me.dio.santanderdevweek2023.exceptions.NoSuchElementException;
import me.dio.santanderdevweek2023.model.Account;
import me.dio.santanderdevweek2023.model.Address;
import me.dio.santanderdevweek2023.model.Card;
import me.dio.santanderdevweek2023.model.Client;
import me.dio.santanderdevweek2023.model.security.UserRole;
import me.dio.santanderdevweek2023.repository.AccountRepository;
import me.dio.santanderdevweek2023.repository.CardRepository;
import me.dio.santanderdevweek2023.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceTest {
    public static final UUID UUID = java.util.UUID.randomUUID();
    public static final long NUMBER = 0L;
    public static final String AGENCY = "agency";
    public static final double BALANCE = 0.0;
    public static final double LIMIT = 0.1;
    public static final String PASSWORD = "password";
    public static final String CPF = "CPF";
    public static final String NAME = "UserTest";
    public static final Date BIRTHDATE    = new Date("25/09/2003");
    public static final String COMPLEMENT = "casa";
    public static final String CLI_NUMBER = "number";
    public static final UserRole USER_ROLE = UserRole.USER;
    @InjectMocks
    AccountService service;
    @Mock
    AccountRepository repository;
    @Mock
    ClientRepository clientRepository;
    @Mock
    ModelMapper modelMapper;
    @Mock
    CardRepository cardRepository;
    private Optional<Account> optionalAccount;
    private Account account;
    private CardDTO cardDTO;
    private Client client;
    private Card card;
    private Set<Card> cardSet;
    private AccountResponseDTO accountResponseDTO;
    private AccountDTO accountDTO;

    private Optional<Client> optionalClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAccount();
    }

    @Test
    void whenFindAllAccountThenReturnAListOfAccount() {
        when(repository.findAll()).thenReturn(List.of(account));

        List<Account> response = service.findAllAccounts();

        assertNotNull(response);
        assertEquals(Account.class, response.get(0).getClass());
        assertEquals(1, response.size());
        assertEquals(UUID, response.get(0).getId());
        assertEquals(NUMBER, response.get(0).getNumber());
        assertEquals(AGENCY, response.get(0).getAgency());
        assertEquals(BALANCE, response.get(0).getBalance());
        assertEquals(LIMIT, response.get(0).getLimit());
    }


    @Test
    void whenFindAccountByIdThenReturnAnInstanceOfAccount() {
        when(repository.findById(any())).thenReturn(optionalAccount);

        Account response = service.findAccountById(any());

        assertNotNull(response);
        assertEquals(Account.class, response.getClass());
        assertEquals(UUID, response.getId());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(AGENCY, response.getAgency());
        assertEquals(BALANCE, response.getBalance());
        assertEquals(LIMIT, response.getLimit());
    }
    @Test
    void whenFindAccountByExistingClientCPFThenReturnAccount() {
        // Arrange
        String existingCPF = "existingCPF";
        when(repository.findByClientCPF(existingCPF)).thenReturn(Optional.of(account));

        // Act
        Account result = service.findAccountByClientCPF(existingCPF);

        // Assert
        assertNotNull(result);
        assertEquals(Account.class, result.getClass());
        assertEquals(UUID, result.getId());
        assertEquals(NUMBER, result.getNumber());
        assertEquals(AGENCY, result.getAgency());
        assertEquals(BALANCE, result.getBalance());
        assertEquals(LIMIT, result.getLimit());
    }

    @Test
    void whenFindAccountByNonExistingClientCPFThenThrowNoSuchElementException() {
        // Arrange
        String nonExistingCPF = "nonExistingCPF";
        when(repository.findByClientCPF(nonExistingCPF)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> service.findAccountByClientCPF(nonExistingCPF));
    }

    @Test
    void whenFindAccountByIdThenReturnNoSuchElementException() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findAccountById(any()));
    }


    @Test
    void whenSaveAccountWithExistingClientThenReturnAccountResponseDTO() {
        // Arrange
        when(clientRepository.findByCpf(CPF)).thenReturn(optionalClient);
        when(repository.save(any(Account.class))).thenReturn(account);
        when(modelMapper.map(any(), any())).thenReturn(cardDTO);

        // Act
        AccountResponseDTO response = service.saveAccount(accountDTO);

        // Assert
        assertNotNull(response);
        assertEquals(AccountResponseDTO.class, response.getClass());
        assertEquals(UUID, response.getAccount().getId());
        assertEquals(NUMBER, response.getAccount().getNumber());
        assertEquals(AGENCY, response.getAccount().getAgency());
        assertEquals(BALANCE, response.getAccount().getBalance());
        assertEquals(LIMIT, response.getAccount().getLimit());
        assertEquals(cardDTO, response.getCardDTO());
    }

    @Test
    void whenSaveAccountWithNonExistingClientThenThrowRuntimeException() {
        // Arrange
        when(clientRepository.findByCpf(CPF)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.saveAccount(accountDTO));
    }

    @Test
    void whenSaveAccountWithRepositoryExceptionThenThrowNoSuchElementException() {
        // Arrange
        when(clientRepository.findByCpf(CPF)).thenReturn(optionalClient);
        when(repository.save(any(Account.class))).thenThrow(new RuntimeException("Test exception"));

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> service.saveAccount(accountDTO));
    }
    @Test
    void whenUpdateAccountThenReturnSuccess() {
        when(repository.findById(any())).thenReturn(optionalAccount);
        when(repository.save(account)).thenReturn(account);

        Account response = service.updateAccount(any(), account);

        assertNotNull(response);
        assertEquals(Account.class, response.getClass());
        assertEquals(UUID, response.getId());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(AGENCY, response.getAgency());
        assertEquals(BALANCE, response.getBalance());
        assertEquals(LIMIT, response.getLimit());
    }

    @Test
    void whenUpdateAccountThenReturnNoSuchElementException() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.updateAccount(any(), account));
    }

    @Test
    void deleteAccountThenReturnSuccess() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        doNothing().when(repository).deleteById(any());
        boolean response = service.deleteAccount(any());
        verify(repository, times(1)).deleteById(any());
        assertTrue(response);
    }


    private void startAccount() {
        client = new Client();
        card = new Card();
        cardDTO = new CardDTO();
        cardSet = Set.of(card);
        account = new Account(UUID, NUMBER, AGENCY, BALANCE, LIMIT, cardSet, client);
        optionalAccount = Optional.of(account);
        accountResponseDTO = new AccountResponseDTO(account, cardDTO);
        accountDTO = new AccountDTO(CPF, PASSWORD);
        optionalClient = Optional.of(new Client(CPF, NAME, PASSWORD, USER_ROLE, BIRTHDATE, COMPLEMENT, CLI_NUMBER, new Address()));
    }
}