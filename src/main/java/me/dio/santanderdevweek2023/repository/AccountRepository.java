package me.dio.santanderdevweek2023.repository;

import me.dio.santanderdevweek2023.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query("SELECT a FROM Account a WHERE a.client.cpf = :cpf")
    Optional<Account> findByClientCPF(String cpf);
}
