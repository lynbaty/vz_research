package org.example.es_module.utils;

import org.example.es_module.documents.Article;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Constant {
    public final static String ARTICLE_INDEX = "article";

    public static final Map<String, Type> INDEX_MAP = new HashMap<>() {
        {
            put(ARTICLE_INDEX, Article.class);
        }
    };
}
