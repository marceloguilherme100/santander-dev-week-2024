package me.dio.santanderdevweek2023.controller;

import me.dio.santanderdevweek2023.dto.AccountDTO;
import me.dio.santanderdevweek2023.dto.AccountResponseDTO;
import me.dio.santanderdevweek2023.model.Account;
import me.dio.santanderdevweek2023.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService service;

    @GetMapping
    public ResponseEntity<Iterable<Account>> findAllAccounts(){
        return ResponseEntity.ok(service.findAllAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findAccountById(@PathVariable("id") UUID id){
        return new ResponseEntity<>(service.findAccountById(id),HttpStatus.OK);
    }

    @GetMapping("/client/{cpf}")
    public ResponseEntity<Account> findAccountByClientCPF(@PathVariable("cpf") String cpf){
        return new ResponseEntity<>(service.findAccountByClientCPF(cpf),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") UUID id, @RequestBody Account account){
        service.updateAccount(id, account);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping
    public ResponseEntity<AccountResponseDTO> saveAccount(@RequestBody AccountDTO account){

        return new ResponseEntity<>(service.saveAccount(account),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable("id") UUID id){
        boolean isAccountDeleted = service.deleteAccount(id);
        return ResponseEntity.status(isAccountDeleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND).build();
    }

}
