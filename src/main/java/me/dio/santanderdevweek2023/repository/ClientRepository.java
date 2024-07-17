package me.dio.santanderdevweek2023.repository;

import me.dio.santanderdevweek2023.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    @Query("SELECT c FROM Client c WHERE c.cpf = :cpf")
    Optional<Client> findByCpf(String cpf);
}
