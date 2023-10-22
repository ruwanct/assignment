package com.bol.mancala.service.impl;

import com.bol.mancala.controller.dto.request.GamePlayRequest;
import com.bol.mancala.controller.dto.request.GameStartRequest;
import com.bol.mancala.controller.dto.response.GameDto;
import com.bol.mancala.controller.dto.response.mappers.GameEntityToDtoMapper;
import com.bol.mancala.exception.GameNotFoundException;
import com.bol.mancala.exception.GameNotStartedException;
import com.bol.mancala.exception.PlayerIdMisMatchException;
import com.bol.mancala.model.Game;
import com.bol.mancala.model.Player;
import com.bol.mancala.repo.GameRepository;
import com.bol.mancala.service.GameRuleService;
import com.bol.mancala.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameRuleService gameRuleService;
    private final GameEntityToDtoMapper gameEntityToDtoMapper;

    public static final String PLAYER_01 = "Player_01";
    public static final String PLAYER_02 = "Player_02";

    @Override
    public ResponseEntity<GameDto> startGame(GameStartRequest request) {
        Game game = gameRuleService.initializeGame();
        List<Player> playersList = createPlayers(request.getPlayers());
        game.setPlayers(playersList);
        GameDto gameDto = gameEntityToDtoMapper.map(gameRepository.save(game));
        return ResponseEntity.status(HttpStatus.CREATED).body(gameDto);
    }

    private List<Player> createPlayers(List<String> players) {
        players = createDummyPlayersIfNoPlayersFound(players);
        return players.stream()
                .map(GameServiceImpl::createPlayerInstance).toList();
    }

    private static Player createPlayerInstance(String playerName) {
        return Player.builder().name(playerName).build();
    }

    private List<String> createDummyPlayersIfNoPlayersFound(List<String> players) {
        if (players == null || players.isEmpty()) {
            players = List.of(PLAYER_01, PLAYER_02);
        }
        return players;
    }

    @Override
    public ResponseEntity<GameDto> joinGame(long gameId) {
        return gameRepository.findById(gameId).map(gameEntityToDtoMapper::map).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<GameDto> moveStone(GamePlayRequest request) {
        Game game = findGameByID(request.getGameId());

        if (!game.isStarted()) {
            throw new GameNotStartedException(request.getGameId());
        }
        if (!isCorrectPlayerId(request, game)) {
            throw new PlayerIdMisMatchException();
        }
        gameRuleService.handleMovingStone(game, request.getPitId());
        gameRuleService.handleLastMovedInBigPit(game);
        gameRuleService.handleLastMovedInEmptyPit(game);
        gameRuleService.handleGameStatus(game);
        GameDto gameDto = gameEntityToDtoMapper.map(gameRepository.save(game));
        return ResponseEntity.status(HttpStatus.OK).body(gameDto);
    }

    private Game findGameByID(Long gameId) {
        return gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
    }

    private boolean isCorrectPlayerId(GamePlayRequest request, Game game) {
        return request.getPlayerId() == game.getNextTurn();
    }
}
