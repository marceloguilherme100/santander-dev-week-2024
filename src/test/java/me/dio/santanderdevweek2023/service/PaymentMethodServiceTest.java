package me.dio.santanderdevweek2023.service;

import me.dio.santanderdevweek2023.exceptions.NoSuchElementException;
import me.dio.santanderdevweek2023.model.PaymentMethod;
import me.dio.santanderdevweek2023.repository.PaymentMethodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentMethodServiceTest {
    public static final byte[] BYTES = "B".getBytes();
    public static final String DESCRIPTION = "DESCRIPTION";
    @InjectMocks
    PaymentMethodService service;
    @Mock
    PaymentMethodRepository repository;
    private Optional<PaymentMethod> optionalPaymentMethod;
    private PaymentMethod paymentMethod;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPaymentMethod();
    }

    @Test
    void whenFindAllPaymentMethodThenReturnAListOfPaymentMethod() {
        when(repository.findAll()).thenReturn(List.of(paymentMethod));

        List<PaymentMethod> response = service.findAllPaymentMethods();

        assertNotNull(response);
        assertEquals(PaymentMethod.class, response.get(0).getClass());
        assertEquals(1, response.size());
        assertEquals(BYTES, response.get(0).getIcon());
        assertEquals(DESCRIPTION, response.get(0).getDescription());
    }


    @Test
    void whenFindPaymentMethodByIdThenReturnAnInstanceOfPaymentMethod() {
        when(repository.findById(any())).thenReturn(optionalPaymentMethod);

        PaymentMethod response = service.findPaymentMethodById(any());

        assertNotNull(response);
        assertEquals(PaymentMethod.class, response.getClass());
        assertEquals(BYTES, response.getIcon());
        assertEquals(DESCRIPTION, response.getDescription());
    }

    @Test
    void whenFindPaymentMethodByIdThenReturnNoSuchElementException() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findPaymentMethodById(any()));
    }


    @Test
    void whenSavePaymentMethodThenReturnSuccess() {
        when(repository.save(paymentMethod)).thenReturn(paymentMethod);

        PaymentMethod response = service.savePaymentMethod(paymentMethod);

        assertNotNull(response);
        verify(repository, times(1)).save(paymentMethod);
        assertEquals(PaymentMethod.class, response.getClass());

        assertEquals(BYTES, response.getIcon());
        assertEquals(DESCRIPTION, response.getDescription());
    }

    @Test
    void whenUpdatePaymentMethodThenReturnSuccess() {
        when(repository.findById(any())).thenReturn(optionalPaymentMethod);
        when(repository.save(paymentMethod)).thenReturn(paymentMethod);

        PaymentMethod response = service.updatePaymentMethod(any(), paymentMethod);

        assertNotNull(response);
        assertEquals(PaymentMethod.class, response.getClass());
        assertEquals(BYTES, response.getIcon());
        assertEquals(DESCRIPTION, response.getDescription());
    }

    @Test
    void whenUpdatePaymentMethodThenReturnNoSuchElementException() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.updatePaymentMethod(any(), paymentMethod));
    }

    @Test
    void deletePaymentMethodThenReturnSuccess() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        doNothing().when(repository).deleteById(any());
        boolean response = service.deletePaymentMethod(any());
        verify(repository, times(1)).deleteById(any());
        assertTrue(response);
    }


    private void startPaymentMethod() {
        paymentMethod = new PaymentMethod(BYTES, DESCRIPTION);
        optionalPaymentMethod = Optional.of(paymentMethod);
    }
}
