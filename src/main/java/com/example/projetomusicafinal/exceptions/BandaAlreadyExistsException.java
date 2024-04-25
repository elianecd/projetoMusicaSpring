package com.example.projetomusicafinal.exceptions;

public class BandaAlreadyExistsException extends RuntimeException {
    public BandaAlreadyExistsException(String message) {
        super(message);
    }
}