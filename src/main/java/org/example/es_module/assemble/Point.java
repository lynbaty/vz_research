package org.example.es_module.assemble;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class Point {
    private Integer index;
    private String key;
    private Double value;

//    private List<Point> subPoints;
    private Point first;
    private Point last;
    private Operator operator;

    public Double getPointValue(VzProcess vzProcess){
        if(Objects.nonNull(value))
            return value;

        if(Objects.nonNull(index) && Objects.nonNull(key))
            return index == 0 ? parseDouble(vzProcess.getInputs().get(key))
            : parseDouble(vzProcess.getSteps().get(index - 1).getOutput().get(key));

        double firstValue = first.getPointValue(vzProcess);
        double lastValue = last.getPointValue(vzProcess);

        return switch (operator) {
            case SUM -> firstValue + lastValue;
            case SUBTRACT -> firstValue - lastValue;
            case AVG -> firstValue + lastValue / 2;
            case MAX -> Math.max(firstValue, lastValue);
            case MIN -> Math.min(firstValue, lastValue);
            case POWER -> Math.pow(firstValue, lastValue);
        };
    }

    private Double parseDouble(Object value) {
        if(Objects.isNull(value))
            return null;

        if(value instanceof Integer)
            return Double.valueOf((Integer) value);

        return (Double) value;
    }
}
