package com.lucutovidiu.ip;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
    private String ip;
    private String version;
    private String city;
    private String region;
    private String region_code;
    private String country;
    private String country_name;
    private String country_code;
    private String country_code_iso3;
    private String country_capital;
    private String country_tld;
    private String continent_code;
    private boolean in_eu;
    private String postal;
    private float latitude;
    private float longitude;
    private String timezone;
    private String utc_offset;
    private String country_calling_code;
    private String currency;
    private String currency_name;
    private String languages;
    private float country_area;
    private float country_population;
    private String asn;
    private String org;

    @Override
    public String toString() {
        return "Location: " + "\n" +
                "ip='" + ip + '\'' + "\n" +
                "version='" + version + '\'' + "\n" +
                "city='" + city + '\'' + "\n" +
                "region='" + region + '\'' + "\n" +
                "region_code='" + region_code + '\'' + "\n" +
                "country='" + country + '\'' + "\n" +
                "country_name='" + country_name + '\'' + "\n" +
                "country_code='" + country_code + '\'' + "\n" +
                "country_code_iso3='" + country_code_iso3 + '\'' + "\n" +
                "country_capital='" + country_capital + '\'' + "\n" +
                "country_tld='" + country_tld + '\'' + "\n" +
                "continent_code='" + continent_code + '\'' + "\n" +
                "in_eu=" + in_eu + "\n" +
                "postal='" + postal + '\'' + "\n" +
                "latitude=" + latitude + "\n" +
                "longitude=" + longitude + "\n" +
                "timezone='" + timezone + '\'' + "\n" +
                "utc_offset='" + utc_offset + '\'' + "\n" +
                "country_calling_code='" + country_calling_code + '\'' + "\n" +
                "currency='" + currency + '\'' + "\n" +
                "currency_name='" + currency_name + '\'' + "\n" +
                "languages='" + languages + '\'' + "\n" +
                "country_area=" + country_area + "\n" +
                "country_population=" + country_population + "\n" +
                "asn='" + asn + '\'' + "\n" +
                "org='" + org + '\'' + "\n";
    }
}