package com.bol.mancala.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameId;
    private boolean started;
    @Column
    private int prevTurn;
    @Column
    private int nextTurn;
    @Column
    private int stonesPerPit;
    private int winner;
    private Pit lastSownPit;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id")
    private List<Player> players = Collections.emptyList();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    private Board board;

    public Game(int playersCount, int smallPitsCountPerSide, int stonesPerPit) {
        this.stonesPerPit = stonesPerPit;
        this.prevTurn = -1;
        this.board = new Board(playersCount, smallPitsCountPerSide + 1);
    }
}