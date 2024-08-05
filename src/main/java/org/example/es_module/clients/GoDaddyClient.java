package org.example.es_module.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "goDaddy", url = "${spring.cloud.openfeign.client.config.goDaddy.url}")
public interface GoDaddyClient {

    @PatchMapping(value = "/v1/domains/{domain}/records")
    ResponseEntity<Object> getUser(@PathVariable String domain,
                                   @RequestBody List<Object> records);
}
