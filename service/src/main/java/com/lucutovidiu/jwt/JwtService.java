package com.lucutovidiu.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {

    Boolean isTokenExpired(String token);

    String generateToken(UserDetails userDetails);

    String extractUsername(String token);

    Boolean validateToken(String token, UserDetails userDetails);
}
