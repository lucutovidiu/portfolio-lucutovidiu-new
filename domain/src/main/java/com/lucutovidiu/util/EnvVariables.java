package com.lucutovidiu.util;

import com.lucutovidiu.ip.Location;
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
    private String commaSeparatedNotSavedLocations;
    private String jwtSecret;
    private String emailExpiredProducts;

    public boolean shouldLocationBeEmailed() {
        return locationEmailed.equals("true");
    }

    public boolean shouldSaveLocation(Location location) {
        if (location == null) return false;
        String[] locations = commaSeparatedNotSavedLocations.split(",");
        for (String loc : locations) {
            if (location.getCountry_name().equalsIgnoreCase(loc) || location.getCity().equalsIgnoreCase(loc)) {
                return false;
            }
        }
        return true;
    }

    public boolean shouldExpiredProductsBeEmailed() {
        return emailExpiredProducts.equals("true");
    }
}
