package com.lucutovidiu.ip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class LocationServiceImpl implements LocationService {

    @Override
    public CompletableFuture<Location> getLocationByIpAddress(String ipAddress) {
        CompletableFuture<Location> resultFuture = new CompletableFuture<>();
        try {
            URI uri = new URI(createUrlFromIp(ipAddress));
            RestTemplate template = new RestTemplate();
            ResponseEntity<Location> result = template.getForEntity(uri, Location.class);
            resultFuture.complete(result.getBody());
        } catch (URISyntaxException e) {
            resultFuture.completeExceptionally(e);
        }
        return resultFuture;
    }

    private String createUrlFromIp(String ipAddress) {
        return String.format("https://ipapi.co/%s/json/", ipAddress);
    }
}
