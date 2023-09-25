package com.example.kanban.entitys;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"total_Elements" , "Page", "content"})
public class Pagination {
    private int page;
    private int size;

    private int total_Elements;

    private List<Project> content;

    public Pagination() {
    }

    public Pagination(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
