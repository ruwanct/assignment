package com.bol.mancala.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameNotFoundExceptionTest {

    @Test
    public void testGameNotFoundExceptionMessage() {
        GameNotFoundException exception = new GameNotFoundException();
        assertEquals("Game not found", exception.getMessage());
    }
}
