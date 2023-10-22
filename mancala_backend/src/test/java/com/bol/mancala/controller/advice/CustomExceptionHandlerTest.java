package com.bol.mancala.controller.advice;

import com.bol.mancala.exception.PitNotFoundException;
import com.bol.mancala.exception.PlayerIdMisMatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomExceptionHandlerTest {

    @InjectMocks
    private CustomExceptionHandler customExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleInvalidArgumentException() {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Test illegalArgumentException message");
        Map<String, String> response = customExceptionHandler.handleInvalidArgumentException(illegalArgumentException);
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        assertEquals(expectedHttpStatus.value(), HttpStatus.BAD_REQUEST.value());
        assertEquals("Test illegalArgumentException message", response.get("errorMessage"));
    }


    @Test
    void testHandleNotFoundException() {
        PitNotFoundException pitNotFoundException = new PitNotFoundException("Pit not found");
        Map<String, String> response = customExceptionHandler.handleNotFound(pitNotFoundException);
        HttpStatus expectedHttpStatus = HttpStatus.NOT_FOUND;
        assertEquals(expectedHttpStatus.value(), HttpStatus.NOT_FOUND.value());
        assertEquals("Pit not found", response.get("errorMessage"));
    }

    @Test
    void testHandleBadRequest() {
        PlayerIdMisMatchException exception = new PlayerIdMisMatchException();
        Map<String, String> response = customExceptionHandler.handleBadRequest(exception);
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        assertEquals(expectedHttpStatus.value(), HttpStatus.BAD_REQUEST.value());
        assertEquals("Player ID mismatch", response.get("errorMessage"));
    }

}