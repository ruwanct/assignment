package com.bol.mancala.exception;

public class PlayerIdMisMatchException extends GameException {
    public PlayerIdMisMatchException() {
        super("Player ID mismatch");
    }
}
