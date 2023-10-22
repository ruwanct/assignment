package com.bol.mancala.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameNotFoundExceptionTest {

    @Test
    public void testGameNotFoundExceptionMessage() {
        GameNotFoundException exception = new GameNotFoundException(123L);
        assertEquals("Game id 123 not found", exception.getMessage());
    }
}
