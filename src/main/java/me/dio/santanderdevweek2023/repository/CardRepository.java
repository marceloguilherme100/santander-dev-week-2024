package me.dio.santanderdevweek2023.repository;

import me.dio.santanderdevweek2023.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    @Query("SELECT c FROM Card c WHERE c.account.client.cpf = :cpf")
    List<Card> findCardsByCPF(String cpf);
}
