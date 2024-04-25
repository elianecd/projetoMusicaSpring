package com.example.projetomusicafinal.exceptions;

public class AlbumAlreadyExistsException extends RuntimeException {
    public AlbumAlreadyExistsException(String message) {
        super(message);
    }
}

