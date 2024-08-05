package org.example.es_module.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "mail.smtp")
public class MailConfiguration {
    private String auth;
    private String starttlsEnable;
    private String host;
    private String port;
    private String starttlsRequired;
    private String userName;
    private String password;
    private String noReplyEmail;
    private String esignUrl;
    private String cmcUrl;
    private String adminEmail;
}