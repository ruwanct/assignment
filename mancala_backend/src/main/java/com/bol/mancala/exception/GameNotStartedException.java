package com.bol.mancala.exception;

public class GameNotStartedException extends GameException {

    public GameNotStartedException(long gameId) {
        super("Game ID " + gameId + " has not started.");
    }
}
