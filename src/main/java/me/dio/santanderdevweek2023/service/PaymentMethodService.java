package me.dio.santanderdevweek2023.service;

import me.dio.santanderdevweek2023.exceptions.NoSuchElementException;
import me.dio.santanderdevweek2023.model.PaymentMethod;
import me.dio.santanderdevweek2023.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentMethodService {
    @Autowired
    PaymentMethodRepository repository;

    public List<PaymentMethod> findAllPaymentMethods(){
        return repository.findAll();
    }

    public PaymentMethod findPaymentMethodById(UUID id) {
        Optional<PaymentMethod> PaymentMethodFound = repository.findById(id);
        return PaymentMethodFound.orElseThrow(() -> new NoSuchElementException(id, "PaymentMethod"));
    }

    public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod){
        return repository.save(paymentMethod);
    }

    public PaymentMethod updatePaymentMethod(UUID id, PaymentMethod PaymentMethod){
        Optional<PaymentMethod> PaymentMethodFound = repository.findById(id);
        if(PaymentMethodFound.isPresent()) {
            return repository.save(PaymentMethod);
        } else{
            throw new NoSuchElementException(id, "PaymentMethod");
        }
    }

    public boolean deletePaymentMethod(UUID id){
        repository.deleteById(id);
        Optional<PaymentMethod> PaymentMethodDelete = repository.findById(id);
        return PaymentMethodDelete.isEmpty();
    }

}
