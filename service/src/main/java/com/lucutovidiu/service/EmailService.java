package com.lucutovidiu.service;

import com.lucutovidiu.ip.Location;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    boolean sendEmail(String from, String[] to, String subject, String body);

    @Async
    void sendLocationEmail(Location location);
}
