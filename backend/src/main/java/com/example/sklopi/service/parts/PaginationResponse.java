package com.example.sklopi.service.parts;

import java.util.List;

public class PaginationResponse<T> {
    private List<T> items;
    private int totalPages;

    public PaginationResponse(List<T> items, int totalPages) {
        this.items = items;
        this.totalPages = totalPages;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
