package org.example.mediavaultbackend.exception;

import org.example.mediavaultbackend.dtos.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.CredentialException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(NoSuchElementException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponseDto.builder()
                .error(ex.getMessage())
                .build());
    }

    @ExceptionHandler(CredentialException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentials(CredentialException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponseDto.builder()
                .error(ex.getMessage())
                .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArguments(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponseDto.builder()
                .error(ex.getMessage())
                .build());
    }

}
