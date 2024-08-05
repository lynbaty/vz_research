package org.example.es_module.configurations;

import org.springframework.beans.factory.InitializingBean;

import java.util.TimeZone;

public class InitializingContextBean implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
