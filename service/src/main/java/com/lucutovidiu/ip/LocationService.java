package com.lucutovidiu.ip;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public interface LocationService {

    String LOCAL_HOST = "127.0.0.1";
    String LOCAL_HOST_V6 = "0:0:0:0:0:0:0:1";

    static String fetchClientIpAddr() {
        String ip = LOCAL_HOST;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) (RequestContextHolder.getRequestAttributes());
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
            ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
            if (ip.equals(LOCAL_HOST_V6))
                ip = LOCAL_HOST;
        }
        return ip;
    }

    Optional<Location> getUserLocation();
}
