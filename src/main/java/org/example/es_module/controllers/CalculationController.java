package org.example.es_module.controllers;

import org.example.es_module.assemble.ProcessDTO;
import org.example.es_module.documents.Article;
import org.example.es_module.entities.ArticleEntity;
import org.example.es_module.services.ArticleService;
import org.example.es_module.services.CalculationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("calculation")
public class CalculationController {
    private final CalculationService calculationService;

    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> getExpenses(@RequestBody ProcessDTO processDTO) {
        return calculationService.excecute(processDTO);
    }
}
