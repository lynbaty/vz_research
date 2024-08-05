package org.example.es_module.assemble;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProcessDTO {
    private Map<String, Object> inputs;
    private List<Step> steps;
}
