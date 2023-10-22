package com.bol.mancala.controller.dto.response.mappers;

import com.bol.mancala.controller.dto.response.GameDto;
import com.bol.mancala.model.Game;
import org.springframework.stereotype.Component;

@Component
public class GameEntityToDtoMapper {

    public GameDto map(Game game) {
        return GameDto.builder()
                .gameId(game.getGameId())
                .started(game.isStarted())
                .prevTurn(game.getPrevTurn())
                .nextTurn(game.getNextTurn())
                .stonesPerPit(game.getStonesPerPit())
                .winner(game.getWinner())
                .lastSownPit(game.getLastSownPit())
                .players(game.getPlayers())
                .board(game.getBoard())
                .build();
    }
}