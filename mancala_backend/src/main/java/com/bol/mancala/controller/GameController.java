package com.bol.mancala.controller;

import com.bol.mancala.controller.dto.request.GamePlayRequest;
import com.bol.mancala.controller.dto.request.GameStartRequest;
import com.bol.mancala.controller.dto.request.JoinGameRequest;
import com.bol.mancala.controller.dto.response.GameDto;
import com.bol.mancala.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/game")
@CrossOrigin("*") // should be replaced with the actual frontEnd URL
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping(value = "/start")
    public ResponseEntity<GameDto> startGame(@RequestBody GameStartRequest request) {
        return gameService.startGame(request);
    }

    @PostMapping(value = "/move")
    public ResponseEntity<GameDto> moveStone(@Valid @RequestBody GamePlayRequest request) {
        return gameService.moveStone(request);
    }

    @PostMapping(value = "/join")
    public ResponseEntity<GameDto> joinGame(@Valid @RequestBody JoinGameRequest joinGameRequest) {
        return gameService.joinGame(joinGameRequest.getGameId());
    }

}