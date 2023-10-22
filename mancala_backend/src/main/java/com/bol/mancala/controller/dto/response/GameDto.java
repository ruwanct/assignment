package com.bol.mancala.controller.dto.response;

import com.bol.mancala.model.Board;
import com.bol.mancala.model.Pit;
import com.bol.mancala.model.Player;
import lombok.Builder;

import java.util.List;

@Builder
public record GameDto(long gameId, boolean started, int prevTurn, int nextTurn, int stonesPerPit, int winner,
                      Pit lastSownPit, List<Player> players,
                      Board board) {
}
