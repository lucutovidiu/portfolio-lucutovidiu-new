package com.lucutovidiu.util;

import com.lucutovidiu.ip.Location;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EnvVariablesTest {

    private final EnvVariables envVariables = new EnvVariables();

    @Test
    public void shouldSaveOrg_shouldReturnFalse() {
        envVariables.setCommaSeparatedNotSavedOrgs("YANDEX,GOOGLE,MID_UNIVERSAL");
        envVariables.setCommaSeparatedNotSavedLocations("Halmeu,Satu Mare,United States");
        assertThat(envVariables.shouldSaveLocation(getLocation())).isFalse();
        envVariables.setCommaSeparatedNotSavedOrgs("YANDEX,GOOGLE");
        envVariables.setCommaSeparatedNotSavedLocations("Halmeu,Satu Mare,United States");
        assertThat(envVariables.shouldSaveLocation(getLocation())).isFalse();
        envVariables.setCommaSeparatedNotSavedOrgs("YANDEX,GOOGLE, MID_UNIVERSAL");
        envVariables.setCommaSeparatedNotSavedLocations("Halmeu,United States");
        assertThat(envVariables.shouldSaveLocation(getLocation())).isFalse();
    }

    @Test
    public void shouldSaveOrg_shouldReturnTrue() {
        envVariables.setCommaSeparatedNotSavedOrgs("YANDEX,GOOGLE");
        envVariables.setCommaSeparatedNotSavedLocations("Halmeu,United States");
        assertThat(envVariables.shouldSaveLocation(getLocation())).isTrue();
    }

    private Location getLocation() {
        String CITY = "SATU MARE";
        String COUNTRY = "Romania";
        String ORG = "MID_UNIVERSAL";
        Location location = new Location();
        location.setCity(CITY);
        location.setCountry_name(COUNTRY);
        location.setOrg(ORG);
        return location;
    }

}
