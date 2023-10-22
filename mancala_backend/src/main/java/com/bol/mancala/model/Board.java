package com.bol.mancala.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rows;
    private int cols;
    @JdbcTypeCode(SqlTypes.BINARY)
    private Pit[][] pits;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.pits = new Pit[this.rows][this.cols];
    }
}