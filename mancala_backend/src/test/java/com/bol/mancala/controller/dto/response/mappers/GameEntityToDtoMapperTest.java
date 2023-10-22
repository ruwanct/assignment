package com.bol.mancala.controller.dto.response.mappers;

import com.bol.mancala.controller.dto.response.GameDto;
import com.bol.mancala.model.Game;
import com.bol.mancala.model.Pit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameEntityToDtoMapperTest {

    private final GameEntityToDtoMapper mapper = new GameEntityToDtoMapper();

    @Test
    public void testMap() {
        Game game = new Game();
        game.setGameId(1L);
        game.setStarted(true);
        game.setPrevTurn(1);
        game.setNextTurn(2);
        game.setStonesPerPit(4);
        game.setWinner(1);
        game.setLastSownPit(Pit.builder().build());
        GameDto gameDto = mapper.map(game);

        assertEquals(game.getGameId(), gameDto.gameId());
        assertEquals(game.isStarted(), gameDto.started());
        assertEquals(game.getPrevTurn(), gameDto.prevTurn());
        assertEquals(game.getNextTurn(), gameDto.nextTurn());
        assertEquals(game.getStonesPerPit(), gameDto.stonesPerPit());
        assertEquals(game.getWinner(), gameDto.winner());
        assertEquals(game.getLastSownPit(), gameDto.lastSownPit());
    }
}
