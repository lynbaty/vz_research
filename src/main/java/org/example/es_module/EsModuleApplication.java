package org.example.es_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
@EnableAsync
public class EsModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsModuleApplication.class, args);
    }

}
