package com.example.kanban.models;

import java.util.List;
import java.util.UUID;

public class ProjectBoardResponse {
    private UUID id;

    private String name;
    private List<BoardResponse> board;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BoardResponse> getBoard() {
        return board;
    }

    public void setBoard(List<BoardResponse> board) {
        this.board = board;
    }
}
