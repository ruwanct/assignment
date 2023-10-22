package com.bol.mancala.controller;

import com.bol.mancala.controller.dto.request.GamePlayRequest;
import com.bol.mancala.controller.dto.request.GameStartRequest;
import com.bol.mancala.controller.dto.request.JoinGameRequest;
import com.bol.mancala.controller.dto.response.GameDto;
import com.bol.mancala.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameControllerTest {

    @Mock
    private GameService gameService;

    private GameController gameController;
    private GameDto gameDto;
    private ResponseEntity<GameDto> responseEntity;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameController = new GameController(gameService);
        gameDto = GameDto.builder()
                .gameId(1L)
                .stonesPerPit(4)
                .build();
        responseEntity = ResponseEntity.ok(gameDto);
    }

    @Test
    void testStartGame() {
        GameStartRequest request = new GameStartRequest();
        Mockito.when(gameService.startGame(request)).thenReturn(responseEntity);
        ResponseEntity<GameDto> responseEntity = gameController.startGame(request);
        Mockito.verify(gameService).startGame(request);
        assertEquals(ResponseEntity.ok(gameDto), responseEntity);
    }


    @Test
    void testMoveStone() {
        GamePlayRequest gamePlayRequest = new GamePlayRequest(1L, 1, 1);
        Mockito.when(gameService.moveStone(gamePlayRequest)).thenReturn(responseEntity);
        ResponseEntity<GameDto> responseEntity = gameController.moveStone(gamePlayRequest);
        Mockito.verify(gameService).moveStone(gamePlayRequest);
        assertEquals(ResponseEntity.ok(gameDto), responseEntity);
    }

    @Test
    void testJoinGame() {
        JoinGameRequest joinGameRequest = new JoinGameRequest(1L);
        long gameId = 1L;
        Mockito.when(gameService.joinGame(gameId)).thenReturn(responseEntity);
        ResponseEntity<GameDto> responseEntity = gameController.joinGame(joinGameRequest);
        Mockito.verify(gameService).joinGame(gameId);
        assertEquals(ResponseEntity.ok(gameDto), responseEntity);
    }
}