package com.bol.mancala.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerIdMisMatchExceptionTest {

    @Test
    public void testPlayerIdMisMatchExceptionMessage() {
        PlayerIdMisMatchException exception = new PlayerIdMisMatchException();
        assertEquals("Player ID mismatch", exception.getMessage());
    }
}
