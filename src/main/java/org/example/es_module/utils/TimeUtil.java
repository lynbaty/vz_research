package org.example.es_module.utils;

import java.sql.Timestamp;

public class TimeUtil {
    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }
}
