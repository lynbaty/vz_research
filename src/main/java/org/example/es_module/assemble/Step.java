package org.example.es_module.assemble;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Step {
    @JsonIgnore
    private Map<String, Object> output = new HashMap<>();
    private Point point;

    public void executeStep(VzProcess vzProcess){
        var value = point.getPointValue(vzProcess);
        output.put("out", value);
    }
//    public static Object getPointValue(List<Step> steps, Point point){
//        if(Objects.nonNull(point.getValue()))
//            return point.getValue();
//        return steps.get(point.getIndex()).getValues().get(point.getKey());
//    }
}
