package com.lucutovidiu.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucutovidiu.ip.Location;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EnvVariablesTest {

    public static final String NOT_SAVED_ORGS = "YANDEX LLC,GOOGLE";
    private final EnvVariables envVariables = new EnvVariables();

    @Test
    public void shouldSaveOrg_shouldReturnFalse() {
        envVariables.setCommaSeparatedNotSavedOrgs(NOT_SAVED_ORGS+",MID_UNIVERSAL");
        envVariables.setCommaSeparatedNotSavedLocations("Halmeu,Satu Mare,United States");
        assertThat(envVariables.shouldSaveLocation(getRealPossibleLocationForTest())).isFalse();
        envVariables.setCommaSeparatedNotSavedOrgs(NOT_SAVED_ORGS);
        envVariables.setCommaSeparatedNotSavedLocations("Halmeu,Satu Mare,United States");
        assertThat(envVariables.shouldSaveLocation(getRealPossibleLocationForTest())).isFalse();
        envVariables.setCommaSeparatedNotSavedOrgs(NOT_SAVED_ORGS+", MID_UNIVERSAL");
        envVariables.setCommaSeparatedNotSavedLocations("Halmeu,United States");
        assertThat(envVariables.shouldSaveLocation(getRealPossibleLocationForTest())).isFalse();
    }

    @Test
    public void shouldSaveOrg_shouldReturnTrue() {
        envVariables.setCommaSeparatedNotSavedOrgs(NOT_SAVED_ORGS);
        envVariables.setCommaSeparatedNotSavedLocations("Halmeu,United States");
        Location realPossibleLocationForTest = getRealPossibleLocationForTest();
        realPossibleLocationForTest.setOrg("MIR_UNIVERSAL");
        assertThat(envVariables.shouldSaveLocation(realPossibleLocationForTest)).isTrue();
    }

    @Test
    public void shouldSaveOrgRealPossibleObject_shouldReturnFalse() {
        envVariables.setCommaSeparatedNotSavedOrgs(NOT_SAVED_ORGS);
        envVariables.setCommaSeparatedNotSavedLocations("Halmeu,United States");
        assertThat(envVariables.shouldSaveLocation(getRealPossibleLocationForTest())).isFalse();
    }

    private Location getRealPossibleLocationForTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        Location location = null;
        try {
            location = objectMapper.readValue("{\"ip\":\"77.88.5.9\"," +
                    "\"version\":\"IPv4\"," +
                    "\"city\":\"Kyiv\"," +
                    "\"region\":\"Kyiv City\"," +
                    "\"region_code\":\"30\"," +
                    "\"country\":\"UA\"," +
                    "\"country_name\":\"Ukraine\"," +
                    "\"country_code\":\"UA\"," +
                    "\"country_code_iso3\":\"UKR\"," +
                    "\"country_capital\":\"Kyiv\"," +
                    "\"country_tld\":\".ua\"," +
                    "\"continent_code\":\"EU\"," +
                    "\"in_eu\":false," +
                    "\"postal\":\"02000\"," +
                    "\"latitude\":\"50.4501\"," +
                    "\"longitude\":\"30.5234\"," +
                    "\"timezone\":\"Europe/Kiev\"," +
                    "\"utc_offset\":\"+0300\"," +
                    "\"country_calling_code\":\"+380\"," +
                    "\"currency\":\"UAH\"," +
                    "\"currency_name\":\"Hryvnia\"," +
                    "\"languages\":\"uk,ru-UA,rom,pl,hu\"," +
                    "\"country_area\":\"603700.0\"," +
                    "\"country_population\":\"4.4622516E7\"," +
                    "\"asn\":\"AS13238\"," +
                    "\"org\":\"YANDEX LLC\"}", Location.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

}
