package org.example.es_module.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginationParams {
    private int page;
    private int size;
    private String sort;
    private String order;
}
