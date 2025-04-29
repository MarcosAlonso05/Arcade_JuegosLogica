package com.example.application.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "nqueens_games")
public class NQueensGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int moves;
    private int boardSize;
    private LocalDateTime gameDate;

    public NQueensGame() {}

    public NQueensGame(User user, int moves, int boardSize) {
        this.user = user;
        this.moves = moves;
        this.boardSize = boardSize;
        this.gameDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public int getMoves() {
        return moves;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public LocalDateTime getGameDate() {
        return gameDate;
    }
}