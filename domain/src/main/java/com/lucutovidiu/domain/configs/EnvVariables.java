package com.lucutovidiu.domain.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "env")
public class EnvVariables {

    private String defaultGmailEmail;
    private String defaultYahooEmail;
    private String locationEmailed;
    private String urlForCrossOrigin;

    public boolean shouldLocationBeEmailed() {
        return locationEmailed.equals("true");
    }
}
