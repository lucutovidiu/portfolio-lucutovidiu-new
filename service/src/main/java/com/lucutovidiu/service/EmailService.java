package com.lucutovidiu.service;

import com.lucutovidiu.ip.Location;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    boolean sendEmail(String from, String[] to, String subject, String body);

    void sendLocationEmail(Location location);

    void sendExpiredProductsEmail(String msg, String emailAddress);
}
