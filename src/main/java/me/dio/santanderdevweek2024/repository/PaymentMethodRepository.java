package me.dio.santanderdevweek2024.repository;

import me.dio.santanderdevweek2024.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID>
{
}
