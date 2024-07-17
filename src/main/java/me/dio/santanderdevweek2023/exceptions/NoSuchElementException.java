package me.dio.santanderdevweek2023.exceptions;

import java.io.Serial;

public class NoSuchElementException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NoSuchElementException(String message) {
        super(message);
    }

    public <T> NoSuchElementException(T id, String entidade) {
        super(entidade + " not found with ID: " + id);
    }
}
