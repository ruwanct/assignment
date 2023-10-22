package com.bol.mancala.service.impl;

import com.bol.mancala.controller.dto.request.GamePlayRequest;
import com.bol.mancala.controller.dto.request.GameStartRequest;
import com.bol.mancala.controller.dto.response.GameDto;
import com.bol.mancala.controller.dto.response.mappers.GameEntityToDtoMapper;
import com.bol.mancala.exception.GameNotStartedException;
import com.bol.mancala.exception.PlayerIdMisMatchException;
import com.bol.mancala.model.Game;
import com.bol.mancala.repo.GameRepository;
import com.bol.mancala.service.GameRuleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {

    @InjectMocks
    private GameServiceImpl gameService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameRuleService gameRuleService;

    @Mock
    private GameEntityToDtoMapper gameEntityToDtoMapper;

    private Game game;
    private GameDto gameDto;
    private GamePlayRequest gamePlayRequest;
    private GameStartRequest gameStartRequest;

    @BeforeEach
    public void setUp() {
        game = new Game(2, 6, 4);
        gameDto = GameDto.builder()
                .gameId(1L)
                .stonesPerPit(4)
                .build();
        gamePlayRequest = new GamePlayRequest(1L, 1, 1);
        gameStartRequest = new GameStartRequest(List.of("Player_01", "Player_02"));
    }

    @Test
    public void testStartGame() {

        when(gameRuleService.initializeGame()).thenReturn(game);
        when(gameRepository.save(game)).thenReturn(game);
        when(gameEntityToDtoMapper.map(game)).thenReturn(gameDto);
        ResponseEntity<GameDto> responseEntity = gameService.startGame(gameStartRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(gameDto, responseEntity.getBody());
    }

    @Test
    public void testJoinGame() {
        long gameId = 1L;
        when(gameRepository.findById(gameId)).thenReturn(java.util.Optional.of(game));
        when(gameEntityToDtoMapper.map(game)).thenReturn(gameDto);

        ResponseEntity<GameDto> responseEntity = gameService.joinGame(gameId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(gameDto, responseEntity.getBody());
    }

    @Test
    public void testMoveStone() {
        game.setStarted(true);
        game.setNextTurn(1);

        when(gameEntityToDtoMapper.map(game)).thenReturn(gameDto);
        when(gameRepository.save(game)).thenReturn(game);
        when(gameRepository.findById(gamePlayRequest.getGameId())).thenReturn(java.util.Optional.of(game));
        ResponseEntity<GameDto> responseEntity = gameService.moveStone(gamePlayRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(gameDto, responseEntity.getBody());
    }

    @Test
    public void testMoveStonePlayerIdMisMatchException() {
        game.setStarted(true);
        game.setNextTurn(2);
        when(gameRepository.findById(gamePlayRequest.getGameId())).thenReturn(java.util.Optional.of(game));
        assertThrows(PlayerIdMisMatchException.class, () -> gameService.moveStone(gamePlayRequest));
        verify(gameRuleService, times(0)).handleMovingStone(any(), anyInt());
    }

    @Test
    public void testMoveStoneGameNotStartedException() {
        game.setNextTurn(2);
        when(gameRepository.findById(gamePlayRequest.getGameId())).thenReturn(java.util.Optional.of(game));
        assertThrows(GameNotStartedException.class, () -> gameService.moveStone(gamePlayRequest));
        verify(gameRuleService, times(0)).handleMovingStone(any(), anyInt());
    }
}