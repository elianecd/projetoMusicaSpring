package com.example.projetomusicafinal.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.projetomusicafinal.models.Banda;
import com.example.projetomusicafinal.services.BandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler{
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private BandaService bandaService;

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleSecurityException(RuntimeException ex) {

        ProblemDetail errorDetail = null;

        if (ex instanceof BadCredentialsException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(400), ex.getMessage());
            errorDetail.setProperty("mensagem", "Erro na autenticação.");
        }

        if (ex instanceof AccessDeniedException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            errorDetail.setProperty("mensagem", "Usuário não autenticado");

            log.error(ex.getMessage(), ex);
        }

        if (ex instanceof JWTVerificationException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            errorDetail.setProperty("mensagem", "Usuário não autorizado!");
        }
        return errorDetail;
    }

    @ExceptionHandler(BandaAlreadyExistsException.class)
    public ResponseEntity<String> handleBandaAlreadyExistsException(BandaAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlbumAlreadyExistsException.class)
    public ResponseEntity<String> handleAlbumAlreadyExistsException(AlbumAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MusicaAlreadyExistsException.class)
    public ResponseEntity<String> handleMusicaAlreadyExistsException(MusicaAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

