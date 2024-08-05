package org.example.es_module.controllers;

import org.example.es_module.dtos.PaginationParams;
import org.example.es_module.queries.VZQuery;
import org.example.es_module.services.QueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("article")
public class QueryController {
    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @PostMapping("/{index}")
    public<T> ResponseEntity<List<T>> query(@RequestBody VZQuery query,
                                            @PathVariable String index,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(required = false) String sort,
                                            @RequestParam(defaultValue = "asc") String sortOrder) {

        PaginationParams paginationParams = new PaginationParams(page, size, sort, sortOrder);
        return queryService.query(query, index, paginationParams);
    }
}
