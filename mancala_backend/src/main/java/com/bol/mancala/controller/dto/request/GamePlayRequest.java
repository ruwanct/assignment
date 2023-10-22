package com.bol.mancala.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public final class GamePlayRequest {
    private @NotNull Long gameId;
    private @NotNull Integer pitId;
    private @NotNull Integer playerId;
}