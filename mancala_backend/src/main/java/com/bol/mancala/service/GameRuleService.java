package com.bol.mancala.service;

import com.bol.mancala.model.Game;

public interface GameRuleService {

    Game initializeGame();

    void handleMovingStone(Game game, int pitId);

    void handleLastMovedInBigPit(Game game);

    void handleLastMovedInEmptyPit(Game game);

    void handleGameStatus(Game game);
}

