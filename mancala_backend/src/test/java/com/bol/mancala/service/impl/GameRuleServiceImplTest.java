package com.bol.mancala.service.impl;

import com.bol.mancala.model.Game;
import com.bol.mancala.model.Pit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class GameRuleServiceImplTest {

    @InjectMocks
    private GameRuleServiceImpl gameRuleService;
    private GameRuleServiceImpl gameService;
    private Game game = null;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameService = new GameRuleServiceImpl();
        game = gameService.initializeGame();
        game.setStonesPerPit(4);
    }

    @Test
    void testInitializeGame() {
        assertNotNull(game);
        assertEquals(4, game.getStonesPerPit());
        assertEquals(-1, game.getWinner());
        assertEquals(0, game.getNextTurn());
        assertTrue(game.isStarted());
    }


    @Test
    void testHandleMovingStone_LastMovedPit_ShouldBeNull_When_StoneCountIsZero() {
        game.setNextTurn(1);
        gameService.handleMovingStone(game, 0);
        assertNull(game.getLastSownPit());
    }


    @Test
    void testHandleMovingStone_pitZeroShouldBeEmpty_And_BigPitShouldBeOne() {
        game.getBoard().getPits()[0][0].setStoneCount(8);
        game.getBoard().getPits()[0][1].setStoneCount(3);
        game.getBoard().getPits()[0][2].setStoneCount(2);
        game.getBoard().getPits()[0][3].setStoneCount(1);
        game.getBoard().getPits()[0][4].setStoneCount(0);
        game.getBoard().getPits()[0][5].setStoneCount(5);
        // Set the big pit to 0
        game.getBoard().getPits()[0][6].setStoneCount(0);

        game.setNextTurn(0);
        gameRuleService.handleMovingStone(game, 0);
        assertEquals(0, game.getBoard().getPits()[0][0].getStoneCount());
        assertEquals(1, game.getBoard().getPits()[0][6].getStoneCount());
    }

    @Test
    void testHandleLastMovedInBigPit() {

        game.setNextTurn(0);
        //Last moved pit is a big pit
        Pit lastSownPit = new Pit(6, 0, true, 0);
        game.setLastSownPit(lastSownPit);
        gameRuleService.handleMovingStone(game, 0);
        gameRuleService.handleLastMovedInBigPit(game);
        // Next turn should also be player 0
        assertEquals(0, game.getNextTurn());
    }

    @Test
    void testHandleLastMovedInEmptyPit_when_oppositeHasNoStones() {
        game.setNextTurn(0);
        // An empty small pit of player 0
        Pit lastSownPit = new Pit(0, 1, false, 0);
        game.setLastSownPit(lastSownPit);
        gameRuleService.handleMovingStone(game, 0);
        gameRuleService.handleLastMovedInEmptyPit(game);
        // For example, check if the player's pit is empty and the opponent's pit is emptied and stones added to the player's big pit
        assertEquals(0, game.getBoard().getPits()[0][0].getStoneCount());
    }

    @Test
    void testHandleLastMovedInEmptyPit_when_oppositeHasStones_shouldAddTo_CurrentPlayer() {
        game.setNextTurn(0);
        game.getBoard().getPits()[1][5].setStoneCount(3);
        Pit lastMovedPit = new Pit(0, 1, false, 0);
        game.setLastSownPit(lastMovedPit);
        gameRuleService.handleMovingStone(game, 0);
        gameRuleService.handleLastMovedInEmptyPit(game);
        assertEquals(0, game.getBoard().getPits()[0][0].getStoneCount());
        //get the stones from the opposite pit and add to the current player's big pit
        assertEquals(4, game.getBoard().getPits()[0][6].getStoneCount());
        //Opponent's pit should be empty
        assertEquals(0, game.getBoard().getPits()[1][5].getStoneCount());
    }

    @Test
    void testHandleGameStatusGameFinished_when_GameIsDraw() {
        gameRuleService.handleGameStatus(game);
        assertFalse(game.isStarted());
        assertNotEquals(-1, game.getWinner());
    }

    @Test
    void testHandleGameStatusGameFinished_when_There_Is_A_Winner() {
        //Player 01 has more stones in the big pit
        game.getBoard().getPits()[0][6].setStoneCount(30);
        game.getBoard().getPits()[1][6].setStoneCount(18);
        gameRuleService.handleGameStatus(game);
        assertFalse(game.isStarted());
        assertEquals(0, game.getWinner());
    }
}