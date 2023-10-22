package com.bol.mancala.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PitNotFoundExceptionTest {

    @Test
    public void testPitNotFoundExceptionMessage() {
        String message = "Pit not found";
        PitNotFoundException exception = new PitNotFoundException(message);
        assertEquals("Pit not found", exception.getMessage());
    }
}
