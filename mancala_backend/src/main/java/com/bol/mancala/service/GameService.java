package com.bol.mancala.service;

import com.bol.mancala.controller.dto.request.GamePlayRequest;
import com.bol.mancala.controller.dto.request.GameStartRequest;
import com.bol.mancala.controller.dto.response.GameDto;
import org.springframework.http.ResponseEntity;

public interface GameService {

    ResponseEntity<GameDto> startGame(GameStartRequest request);

    ResponseEntity<GameDto> joinGame(long gameId);

    ResponseEntity<GameDto> moveStone(GamePlayRequest request);
}
