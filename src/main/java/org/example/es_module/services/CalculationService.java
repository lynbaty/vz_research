package org.example.es_module.services;

import org.example.es_module.assemble.ProcessDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CalculationService {
    ResponseEntity<Map<String, Object>> excecute(ProcessDTO processDTO);
}
