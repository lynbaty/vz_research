package org.example.es_module.services;

import org.example.es_module.dtos.PaginationParams;
import org.example.es_module.queries.VZQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QueryService {
    <T> ResponseEntity<List<T>> query(VZQuery query, String index, PaginationParams paginationParams);
}
