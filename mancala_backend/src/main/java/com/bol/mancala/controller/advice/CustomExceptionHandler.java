package com.bol.mancala.controller.advice;

import com.bol.mancala.exception.GameNotFoundException;
import com.bol.mancala.exception.GameNotStartedException;
import com.bol.mancala.exception.PitNotFoundException;
import com.bol.mancala.exception.PlayerIdMisMatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleInvalidArgumentException(IllegalArgumentException exception) {
        Map<String, String> map =  new HashMap<>();
        map.put("errorMessage", exception.getMessage());
        log.error("Error: ", exception);
        return map;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({PitNotFoundException.class, GameNotFoundException.class})
    public Map<String, String> handleNotFound(Exception exception) {
        Map<String, String> map =  new HashMap<>();
        map.put("errorMessage", exception.getMessage());
        log.error("Error: ", exception);
        return map;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({PlayerIdMisMatchException.class, GameNotStartedException.class})
    public Map<String, String> handleBadRequest(Exception exception) {
        Map<String, String> map =  new HashMap<>();
        map.put("errorMessage", exception.getMessage());
        log.error("Error: ", exception);
        return map;
    }
}