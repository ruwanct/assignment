package com.bol.mancala.service.impl;

import com.bol.mancala.exception.PitNotFoundException;
import com.bol.mancala.model.Board;
import com.bol.mancala.model.Game;
import com.bol.mancala.model.Pit;
import com.bol.mancala.service.GameRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
public class GameRuleServiceImpl implements GameRuleService {

    @Value("${game.stones.count:4}")
    private int stonesPerPit;
    private static final int PLAYER_COUNT = 2;
    private static final int SMALL_PITS_COUNT_PER_SIDE = 6;

    @Override
    public Game initializeGame() {

        Game game = new Game(PLAYER_COUNT, SMALL_PITS_COUNT_PER_SIDE, stonesPerPit);
        initializeGameBoard(game.getBoard(), game);
        game.setWinner(-1);
        game.setNextTurn(0);
        game.setStarted(true);
        return game;
    }

    private void initializeGameBoard(Board board, Game game) {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                board.getPits()[i][j] = new Pit(j, game.getStonesPerPit(), false, i);
                board.getPits()[i][j] = new Pit(j, game.getStonesPerPit(), false, i);
            }
            board.getPits()[i][board.getCols() - 1].setStoneCount(0);
            board.getPits()[i][board.getCols() - 1].setBigPit(true);
        }
    }

    @Override
    public void handleMovingStone(Game game, int startingPitId) {
        int startIndex = startingPitId + 1;
        int playerId = game.getNextTurn();
        game.setPrevTurn(playerId);

        Pit startingPit = getPitByIndex(playerId, startingPitId, game.getBoard());

        int stoneCount = startingPit.getStoneCount();
        startingPit.setStoneCount(0);

        if (stoneCount > 0) {
            Pit current = null;
            for (int i = 0; i < stoneCount; i++) {
                int endingPitIndex = (startIndex + i) % game.getBoard().getPits()[playerId].length;
                current = getPitByIndex(playerId, endingPitIndex, game.getBoard());
                if (isPitBelongToSamePlayer(game, endingPitIndex, current)) {
                    current.setStoneCount(current.getStoneCount() + 1);
                } else {
                    stoneCount += 1;
                }
                playerId = togglePlayer(endingPitIndex, playerId);
            }
            int nextPlayerId = getNextPlayer(game.getPrevTurn());
            game.setNextTurn(nextPlayerId);
            game.setLastSownPit(current);
        }
    }

    private boolean isPitBelongToSamePlayer(Game game, int endingPitIndex, Pit current) {
        return endingPitIndex < SMALL_PITS_COUNT_PER_SIDE || isCurrentPlayersBigPit(game, current);
    }

    private boolean isCurrentPlayersBigPit(Game game, Pit current) {
        return current.getPitOwner() == game.getPrevTurn() && current.isBigPit();
    }

    private Pit getPitByIndex(int row, int col, Board board) {
        if (isInvalidPitIndex(row, col, board)) {
            log.debug("Invalid pit index");
            throw new PitNotFoundException("Invalid pit index");
        }
        return board.getPits()[row][col];
    }

    private static boolean isInvalidPitIndex(int row, int col, Board board) {
        return row < 0 || col < 0 || row > board.getPits().length || col > board.getPits()[0].length;
    }

    private int togglePlayer(int endingPitIndex, int playerId) {
        if (endingPitIndex == SMALL_PITS_COUNT_PER_SIDE) {
            return getNextPlayer(playerId);
        }
        return playerId;
    }

    private int getNextPlayer(int playerId) {
        return (playerId + 1) % PLAYER_COUNT;
    }

    @Override
    public void handleLastMovedInBigPit(Game game) {
        Optional<Pit> lastSownPit = Optional.ofNullable(game.getLastSownPit());
        if (lastSownPit.isEmpty()) return;

        if (lastSownPit.get().isBigPit()) {
            game.setNextTurn(game.getPrevTurn());
        }
    }


    @Override
    public void handleLastMovedInEmptyPit(Game game) {
        Optional<Pit> lastSownPit = Optional.ofNullable(game.getLastSownPit());
        if (lastSownPit.isEmpty()) return;

        if (lastSownPit.get().isBigPit()) return;

        if (lastSownPit.get().getPitOwner() != game.getPrevTurn()) return;

        int stoneCountInPit = lastSownPit.get().getStoneCount();
        if (stoneCountInPit == 1) {
            int opponentIndex = getOppositePit(lastSownPit.get().getId());
            Pit opponentPit = getPitByIndex(getNextPlayer(game.getPrevTurn()), opponentIndex, game.getBoard());

            int opponentStoneCount = opponentPit.getStoneCount();
            if (opponentStoneCount > 0) {
                int totalStoneCount = lastSownPit.get().getStoneCount() + opponentStoneCount;
                addStonesToPlayer(game, totalStoneCount);
                lastSownPit.get().setStoneCount(0);
                opponentPit.setStoneCount(0);
            }
        }
    }

    private void addStonesToPlayer(Game game, int totalStoneCount) {
        Pit playerPit = getBigPitByPlayerId(game, game.getPrevTurn());
        playerPit.setStoneCount(playerPit.getStoneCount() + totalStoneCount);
    }

    @Override
    public void handleGameStatus(Game game) {
        boolean isGameOver = false;
        boolean needToFinishGame = false;

        for (int i = 0; i < PLAYER_COUNT; i++) {
            Pit[] smallPitsArray = Arrays.copyOfRange(game.getBoard().getPits()[i], 0, SMALL_PITS_COUNT_PER_SIDE);
            int totalStoneCount = Arrays.stream(smallPitsArray).mapToInt(Pit::getStoneCount).sum();

            if (totalStoneCount == 0) {
                needToFinishGame = true;
            }

            if (needToFinishGame) {
                moveAllStonesToBigPit(game, i);
                moveAllStonesToBigPit(game, getNextPlayer(i));
                isGameOver = true;
            }
        }
        if (isGameOver) {
            findWinningPlayer(game);
        }
    }

    private void moveAllStonesToBigPit(Game game, int playerId) {
        Pit[] smallPitsArray = Arrays.copyOfRange(game.getBoard().getPits()[playerId], 0, SMALL_PITS_COUNT_PER_SIDE);
        int totalStoneCount = Arrays.stream(smallPitsArray).mapToInt(Pit::getStoneCount).sum();

        Pit bigPit = getBigPitByPlayerId(game, playerId);
        bigPit.setStoneCount(bigPit.getStoneCount() + totalStoneCount);

        Arrays.stream(smallPitsArray).forEach(p -> p.setStoneCount(0));
    }


    private void findWinningPlayer(Game game) {
        int pitCount = 0;
        int playerId = 0;

        boolean isGameDrawn = isGameDrawn(game);

        if (isGameDrawn) {
            game.setWinner(2);
        } else {
            for (int i = 0; i < PLAYER_COUNT; i++) {
                Pit pit = getBigPitByPlayerId(game, i);
                if (pit.getStoneCount() > pitCount) {
                    pitCount = pit.getStoneCount();
                    playerId = pit.getPitOwner();
                }
            }

            game.setWinner(playerId);
        }
        game.setStarted(false);
    }

    private boolean isGameDrawn(Game game) {
        Pit pit = getBigPitByPlayerId(game, 0);
        for (int i = 1; i < PLAYER_COUNT; i++) {
            if (pit.getStoneCount() != getBigPitByPlayerId(game, i).getStoneCount()) {
                return false;
            }
        }
        return true;
    }

    private Pit getBigPitByPlayerId(Game game, int playerId) {
        return getPitByIndex(playerId, SMALL_PITS_COUNT_PER_SIDE, game.getBoard());
    }

    private int getOppositePit(int pitId) {
        return SMALL_PITS_COUNT_PER_SIDE - (pitId + 1);
    }
}