package org.example.es_module.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.es_module.utils.ESUtil;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VZQuery {
    private String field;
    private Object value;
    private VZFunction function;
    private Boolean isFuzzy = Boolean.TRUE;
    private List<VZQuery> childConditions = new ArrayList<>();
    private VZConditionType conditionType = VZConditionType.AND;

    public VZQuery(String field, Object value, VZFunction function)
    {
        this.field = field;
        this.value = value;
        this.function = function;
    }

    public VZQuery(List<VZQuery> childConditions, VZConditionType conditionType)
    {
        this.childConditions = childConditions;
        this.conditionType = conditionType;
    }

    public Object getValue() {
        return (Object) ESUtil.checkParseDateTime(value);
    }
}
