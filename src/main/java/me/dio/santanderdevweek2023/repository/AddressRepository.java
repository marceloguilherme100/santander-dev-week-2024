package me.dio.santanderdevweek2023.repository;

import me.dio.santanderdevweek2023.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Query("SELECT a FROM Address a WHERE a.cep = :cep")
    Optional<Address> findByCep(String cep);
}
