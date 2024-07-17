package me.dio.santanderdevweek2023.repository;

import me.dio.santanderdevweek2023.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID>
{
}
