package me.dio.santanderdevweek2023.service;

import me.dio.santanderdevweek2023.dto.AccountDTO;
import me.dio.santanderdevweek2023.dto.AccountResponseDTO;
import me.dio.santanderdevweek2023.dto.CardDTO;
import me.dio.santanderdevweek2023.exceptions.NoSuchElementException;
import me.dio.santanderdevweek2023.model.Account;
import me.dio.santanderdevweek2023.model.Card;
import me.dio.santanderdevweek2023.model.CardType;
import me.dio.santanderdevweek2023.model.Client;
import me.dio.santanderdevweek2023.repository.AccountRepository;
import me.dio.santanderdevweek2023.repository.CardRepository;
import me.dio.santanderdevweek2023.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    AccountRepository repository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<Account> findAllAccounts(){
        return repository.findAll();
    }

    public Account findAccountById(UUID id) {
        Optional<Account> accountFound = repository.findById(id);
        return accountFound.orElseThrow(() -> new NoSuchElementException(id, "account"));
    }
    public Account findAccountByClientCPF(String cpf) {
        Optional<Account> accountFound = repository.findByClientCPF(cpf);
        if (accountFound.isPresent()){
            return accountFound.get();
        }else {
            throw new NoSuchElementException("Can't find an Account with CPF Owner: " + cpf);
        }
    }

    public AccountResponseDTO saveAccount(AccountDTO account) {
        Optional<Client> client = clientRepository.findByCpf(account.getCpf());
        if (client.isPresent()) {
            Account savedAccount;
            CardDTO savedCard;
            Account defaultAccount = generateDefaultAccount(client);
            savedAccount = repository.save(defaultAccount);
            savedCard = modelMapper.map(generateDefaultCard(savedAccount), CardDTO.class);
            return new AccountResponseDTO(savedAccount, savedCard);
        }else {
            throw new RuntimeException("Error, Client CPF not found! ");
        }
    }

    public Account updateAccount(UUID id, Account account){
        Optional<Account> accountFound = repository.findById(id);

        if (accountFound.isPresent()) {
            return repository.save(account);
        } else {
            throw new NoSuchElementException("Account not found with ID: " + id);
        }
    }

    public boolean deleteAccount(UUID id){
        repository.deleteById(id);
        Optional<Account> accountDelete = repository.findById(id);
        return accountDelete.isEmpty();
    }

    private static Account generateDefaultAccount(Optional<Client> client) {
        Account defaultAccount = new Account();
        Random generator = new Random();
        defaultAccount.setNumber(generator.nextLong(99999999));
        defaultAccount.setAgency("0001");
        defaultAccount.setBalance(0);
        defaultAccount.setClient(client.get());
        defaultAccount.setCards(null);
        return defaultAccount;
    }
    private Card generateDefaultCard(Account savedAccount) {
        Card savedCard;
        try {
            Card defaultCard = new Card(0L, CardType.DEBIT, 100.0, savedAccount);
            savedCard = cardRepository.save(defaultCard);
        } catch (RuntimeException e) {
            throw new NoSuchElementException("Card can't be assigned to the new Account! Error: " + e.getMessage());
        }
        return savedCard;
    }

}
