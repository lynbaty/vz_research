package org.example.es_module.utils;

import org.example.es_module.documents.Article;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class IndexHash {
    private static Map<String, Type> indexMap() {
        Map<String, Type> map = new HashMap<>();
        map.put("article", Article.class);

        return map;
    }
}
