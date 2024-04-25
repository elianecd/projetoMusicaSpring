package com.example.projetomusicafinal.exceptions;

public class MusicaAlreadyExistsException extends RuntimeException{
    public MusicaAlreadyExistsException(String message) {
        super(message);
    }
}
