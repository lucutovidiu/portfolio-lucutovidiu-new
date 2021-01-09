package com.lucutovidiu.ip;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface LocationService {

    @Async
    CompletableFuture<Location> getLocationByIpAddress(String ipAddress);
}
