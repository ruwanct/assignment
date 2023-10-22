package com.bol.mancala.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Pit implements Serializable {

    private int id;
    private int stoneCount;
    private boolean bigPit;
    private int pitOwner;
}