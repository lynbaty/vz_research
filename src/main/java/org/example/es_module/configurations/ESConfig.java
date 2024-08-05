package org.example.es_module.configurations;

import nl.altindag.ssl.SSLFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.support.HttpHeaders;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.NoSuchAlgorithmException;

@Configuration
@EnableElasticsearchRepositories(basePackages = "org.example.es_module.repositories.els")
//@ComponentScan(basePackages = {"org.example.es_module.services"})
public class ESConfig extends ElasticsearchConfiguration {

    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.username}")
    private String username;
    @Value("${elasticsearch.password}")
    private String password;

//    @Override
//    public ClientConfiguration clientConfiguration() {
//        SSLFactory sslFactory = SSLFactory.builder()
//                .withUnsafeTrustMaterial()
//                .withUnsafeHostnameVerifier()
//                .build();
//       return   ClientConfiguration.builder()
//                .connectedTo(host)
//               .withClientConfigurer(ElasticsearchClients.ElasticsearchHttpClientConfigurationCallback.from(httpAsyncClientBuilder ->
//                   httpAsyncClientBuilder.setSSLContext(sslFactory.getSslContext())
//                           .setSSLHostnameVerifier(sslFactory.getHostnameVerifier())
//                ))
//                .withBasicAuth(username, password)
//                .withHeaders(() -> {
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-type", "application/json");
//            headers.add("Accept", "application/json");
//            return headers;
//        }).build();
//
//    }

    @Override
    public ClientConfiguration clientConfiguration() {
          return   ClientConfiguration.builder()
                .connectedTo(host)
                .withBasicAuth(username, password)
                .build();
    }
}
