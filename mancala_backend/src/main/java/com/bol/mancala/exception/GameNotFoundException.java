package com.bol.mancala.exception;

public class GameNotFoundException extends GameException {
    public GameNotFoundException(long gameId) {
        super("Game id "+ gameId+ " not found");
    }
}
