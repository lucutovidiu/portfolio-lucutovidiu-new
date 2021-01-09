package com.lucutovidiu.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    boolean sendEmail(String from, String[] to, String subject, String body);
}
