package org.example.es_module.services.impl;

import org.example.es_module.assemble.ProcessDTO;
import org.example.es_module.assemble.Step;
import org.example.es_module.assemble.VzProcess;
import org.example.es_module.services.CalculationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CalculationServiceImpl implements CalculationService {
    @Override
    public ResponseEntity<Map<String, Object>> excecute(ProcessDTO processDTO) {

        VzProcess process = new VzProcess();
        process.setInputs(processDTO.getInputs());
        process.setSteps(processDTO.getSteps());
        Map<String, Object> result = new HashMap<String, Object>();

        for(Step step : process.getSteps()) {
            step.executeStep(process);
            result = step.getOutput();
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
