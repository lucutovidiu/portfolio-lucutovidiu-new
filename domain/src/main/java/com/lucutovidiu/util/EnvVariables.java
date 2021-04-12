package com.lucutovidiu.util;

import com.lucutovidiu.ip.Location;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Data
@Configuration
@ConfigurationProperties(prefix = "env")
public class EnvVariables {

    private String defaultGmailEmail;
    private String defaultYahooEmail;
    private String locationEmailed;
    private String urlForCrossOrigin;
    private String commaSeparatedNotSavedLocations;
    private String commaSeparatedNotSavedOrgs;
    private String jwtSecret;
    private String emailExpiredProducts;

    public boolean shouldLocationBeEmailed() {
        return locationEmailed.equals("true");
    }

    public boolean shouldSaveLocation(Location location) {
        if (location == null) return false;
        String[] locations = commaSeparatedNotSavedLocations.split(",");
        for (String loc : locations) {
            if (loc.equalsIgnoreCase(location.getCountry_name()) || loc.equalsIgnoreCase(location.getCity())) {
                return false;
            }
        }
        return true;
    }

    public boolean shouldSaveOrg(String userOrg) {
        if (userOrg == null) return false;
        String[] orgs = commaSeparatedNotSavedOrgs.split(",");
        for (String org : orgs) {
            if (org.toUpperCase().startsWith(userOrg.toUpperCase()))
                return false;
        }
        return true;
    }

    public boolean shouldExpiredProductsBeEmailed() {
        return emailExpiredProducts.equals("true");
    }
}
