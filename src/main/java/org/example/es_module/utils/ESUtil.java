package org.example.es_module.utils;

import co.elastic.clients.elasticsearch.core.search.Hit;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class ESUtil {
    public static <T> List<T> toList(List<Hit<T>> hitList) {
        List<T> list = new ArrayList<T>();
        for (Hit<T> item : hitList) {
            list.add(item.source());
        }
        return list;
    }

    public static String toExactWord(String word) {
        return  "\"" + word + "\"";
    }

    public static String toQueryExactWord(List<String> words) {
        return String.join(" OR ", words.stream().map(ESUtil::toExactWord).toList());
    }

    public static Object checkParseDateTime(Object valueString) {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date parsedDate = formatter.parse((String) valueString);
            return String.valueOf(parsedDate.getTime());
        } catch (Exception ex){
            return valueString;
        }
    }
}
