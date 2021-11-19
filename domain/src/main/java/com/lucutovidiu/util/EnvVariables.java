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
    private String emailClientLocation;
    private String urlForCrossOrigin;
    private String commaSeparatedNotSavedLocations;
    private String commaSeparatedNotSavedOrgs;
    private String jwtSecret;
    private String emailExpiredProducts;
    private String commaSeparatedNotSavedOptionLocation;

    public boolean shouldLocationBeEmailed() {
        return emailClientLocation.equals("true");
    }

    private boolean shouldSaveByCountryOrCityLocation(String countryName, String cityName) {
        String[] locations = commaSeparatedNotSavedLocations.split(",");
        for (String loc : locations) {
            if (loc.equalsIgnoreCase(countryName.trim()) || loc.equalsIgnoreCase(cityName.trim())) {
                return false;
            }
        }
        return true;
    }

    public boolean shouldSaveLocation(Location location) {
        if (location == null) return false;
        return shouldSaveByCountryOrCityLocation(location.getCountry_name(), location.getCity()) && shouldSaveOrg(location.getOrg());
    }

    public boolean shouldSaveLocationByOptionAndValue(Location location) {
        if (location == null) return false;
        return shouldSaveByOptionAndValue(location);
    }

    public boolean shouldSaveByOptionAndValue(Location location) {
        String[] optionLocation = commaSeparatedNotSavedOptionLocation.split(",");
        for (String optVal : optionLocation) {
            String[] optVal1 = optVal.split("=");
            String opt = optVal1[0];
            String val = optVal1[1];
            if(location.toString().contains(opt + "=" + val)){
                return false;
            }
        }
        return true;
    }

    private boolean shouldSaveOrg(String userOrg) {
        String[] orgs = commaSeparatedNotSavedOrgs.split(",");
        for (String org : orgs) {
            if (org.equalsIgnoreCase(userOrg.trim().toUpperCase()))
                return false;
        }
        return true;
    }

    public boolean shouldExpiredProductsBeEmailed() {
        return emailExpiredProducts.equals("true");
    }
}
