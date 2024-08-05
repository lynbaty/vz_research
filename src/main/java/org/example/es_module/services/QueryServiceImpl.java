package org.example.es_module.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import org.apache.commons.lang3.ObjectUtils;
import org.example.es_module.dtos.PaginationParams;
import org.example.es_module.queries.VZConditionType;
import org.example.es_module.queries.VZFunction;
import org.example.es_module.queries.VZQuery;
import org.example.es_module.utils.ESUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.es_module.utils.Constant.INDEX_MAP;

@Service
public class QueryServiceImpl implements QueryService {

    private final ElasticsearchClient elasticsearchClient;

    public QueryServiceImpl(ElasticsearchClient elasticsearchClient) {

        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public <T> ResponseEntity<List<T>> query(VZQuery query, String index, PaginationParams paginationParams){
        if (!INDEX_MAP.containsKey(index))
            throw new RuntimeException();

        Query esQuery = createQuery(query);
        return ResponseEntity.ok(executeQuery(esQuery, index, paginationParams));
    }

    public <T> List<T> executeQuery(Query esQuery, String index, PaginationParams paginationParams) {
        try {
            SearchResponse<T> response;
            if(Objects.nonNull(paginationParams.getSort()))
            {
                SortOrder sortOrder = paginationParams.getOrder().equals("asc") ? SortOrder.Asc : SortOrder.Desc;
                response = elasticsearchClient.search(s -> s.query(esQuery)
                        .from(paginationParams.getPage() * paginationParams.getSize())
                        .size(paginationParams.getSize())
                        .sort(c -> c.field(b -> b.order(sortOrder).field(paginationParams.getSort() + ".keyword"))), INDEX_MAP.get(index));
            } else {
                response = elasticsearchClient.search(s -> s.query(esQuery)
                        .from(paginationParams.getPage() * paginationParams.getSize())
                        .size(paginationParams.getSize()), INDEX_MAP.get(index));
            }
            return ESUtil.toList(response.hits().hits());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Query createQuery(VZQuery query) {

        String fuzziness = query.getIsFuzzy() ? "AUTO" : "0";
        if (!ObjectUtils.isEmpty(query.getChildConditions())) {
            List<Query> queries = new ArrayList<>();
            for (VZQuery childQuery : query.getChildConditions()) {
                queries.add(createQuery(childQuery));
            }
            if (VZConditionType.AND.equals(query.getConditionType()))
                return new Query.Builder().bool(b -> b.must(queries)).build();
            else
                return new Query.Builder().bool(b -> b.should(queries)).build();
        }

        if (VZFunction.Like.equals(query.getFunction())) {
            String value = (String) query.getValue();
            return new Query.Builder().match(m -> m.field(query.getField())
                    .query(value)
                    .operator(Operator.And)
                    .fuzziness(fuzziness)).build();
        }

        if (VZFunction.Eq.equals(query.getFunction())) {
            if(Objects.isNull(query.getValue()))
                return new Query.Builder().bool(b -> b.mustNot(m -> m.exists(e -> e.field(query.getField())))).build();

            String value = (String) query.getValue();
            return new Query.Builder().queryString(q -> q.query(ESUtil.toExactWord(value)).fields(List.of(query.getField()))).build();
        }

        if (VZFunction.NotEq.equals(query.getFunction())) {
            if(Objects.isNull(query.getValue()))
                return new Query.Builder().bool(b -> b.must(m -> m.exists(e -> e.field(query.getField())))).build();

            String value = (String) query.getValue();
            return new Query.Builder().bool(b -> b.mustNot(List.of(new Query.Builder()
                            .queryString(q -> q.query(ESUtil.toExactWord(value))
                                    .fields(List.of(query.getField())))
                            .build())))
                    .build();
        }

        if (VZFunction.Ge.equals(query.getFunction())) {
            return new Query.Builder().range(r -> r.field(query.getField())
                    .gte(JsonData.of(query.getValue()))).build();
        }

        if (VZFunction.Ge.equals(query.getFunction())) {
            return new Query.Builder().range(r -> r.field(query.getField())
                    .gte(JsonData.of(query.getValue()))).build();
        }

        if (VZFunction.Gt.equals(query.getFunction())) {
            return new Query.Builder().range(r -> r.field(query.getField())
                    .gt(JsonData.of(query.getValue()))).build();
        }

        if (VZFunction.Le.equals(query.getFunction())) {
            return new Query.Builder().range(r -> r.field(query.getField())
                    .lte(JsonData.of(query.getValue()))).build();
        }

        if (VZFunction.Lt.equals(query.getFunction())) {
            return new Query.Builder().range(r -> r.field(query.getField())
                    .lt(JsonData.of(query.getValue()))).build();
        }

        if (VZFunction.Bw.equals(query.getFunction())) {
            List<String> values = (List<String>) query.getValue();
            return new Query.Builder().range(r -> r.field(query.getField())
                    .gte(JsonData.of(ESUtil.checkParseDateTime(values.get(0))))
                    .lte(JsonData.of(ESUtil.checkParseDateTime(values.get(1))))).build();
        }

        if (VZFunction.In.equals(query.getFunction())) {
            List<String> values = (List<String>) query.getValue();
            return new Query.Builder().queryString(q -> q.query(ESUtil.toQueryExactWord(values)).fields(List.of(query.getField()))).build();
        } else return null;
    }
}
