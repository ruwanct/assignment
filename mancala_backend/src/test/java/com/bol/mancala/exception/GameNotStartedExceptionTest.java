package com.bol.mancala.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameNotStartedExceptionTest {

    @Test
    public void testGameNotStartedExceptionMessage() {
        long gameId = 123;
        GameNotStartedException exception = new GameNotStartedException(gameId);
        assertEquals("Game ID 123 has not started.", exception.getMessage());
    }
}
