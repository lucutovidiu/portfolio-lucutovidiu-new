package com.lucutovidiu.ip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Component
@Slf4j
public class LocationServiceImpl implements LocationService {

    @Override
    public Optional<Location> getUserLocation() {
        return getUserLocationByIpAddress(LocationService.fetchClientIpAddr());
    }

    private Optional<Location> getUserLocationByIpAddress(String ipAddress) {
        if (ipAddress.equals(LOCAL_HOST)) return Optional.empty();
        try {
            RestTemplate template = new RestTemplate();
            ResponseEntity<Location> result = template.getForEntity(new URI(createUrlFromIp(ipAddress)), Location.class);
            return Optional.ofNullable(result.getBody());
        } catch (URISyntaxException exception) {
            log.error("Couldn't fetch remote address fo ip: {} and message: {}", ipAddress, exception.getMessage());
            return Optional.empty();
        }
    }

    private String createUrlFromIp(String ipAddress) {
        return String.format("https://ipapi.co/%s/json/", ipAddress);
    }
}
