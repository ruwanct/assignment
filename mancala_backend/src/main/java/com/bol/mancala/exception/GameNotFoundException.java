package com.bol.mancala.exception;

public class GameNotFoundException extends GameException {
    public GameNotFoundException() {
        super("Game not found");
    }
}
