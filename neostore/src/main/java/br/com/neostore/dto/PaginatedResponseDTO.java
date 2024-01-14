package br.com.neostore.dto;

import jakarta.json.bind.annotation.JsonbProperty;

import java.util.List;

public class PaginatedResponseDTO<T>{

    @JsonbProperty("data")
    private List<T> data;
    @JsonbProperty("totalItems")
    private long totalItems;
    public PaginatedResponseDTO(List<T> data, long totalItems) {
        this.data = data;
        this.totalItems = totalItems;
    }

    public List<T> getData() {
        return data;
    }

    public long getTotalItems() {
        return totalItems;
    }
}
