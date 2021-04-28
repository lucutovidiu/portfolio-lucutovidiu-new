package com.lucutovidiu.mongo.country;

public enum CountryEnum {
    UNITED_KINGDOM("United Kingdom");

    private String countryName;

    CountryEnum(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }
}
