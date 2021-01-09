package com.lucutovidiu.pages;

import com.lucutovidiu.domain.configs.EnvVariables;
import com.lucutovidiu.ip.LocationService;
import com.lucutovidiu.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static com.lucutovidiu.util.PageAttributesUtil.ActivePage;
import static com.lucutovidiu.util.Pages.HOME;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HomeImpl implements Home {

    private static final String LOCAL_HOST = "127.0.0.1";
    private static final String LOCAL_HOST_V6 = "0:0:0:0:0:0:0:1";
    private final LocationService locationService;
    private final EmailService emailService;
    private final EnvVariables envVariables;

    @Override
    public String getIndex(Model model, HttpServletRequest request) {
        model.addAttribute(ActivePage, HOME);
        if (envVariables.shouldLocationBeEmailed())
            fetchLocationAndEmail();
        return "home/index";
    }

    @Override
    public byte[] getFavicon() throws IOException {
        return Files.readAllBytes(Paths.get("api-impl/src/main/resources/static/favicon.ico"));
    }

    @Override
    public byte[] getRobots() throws IOException {
        return Files.readAllBytes(Paths.get("api-impl/src/main/resources/static/robots.txt"));
    }

    @SuppressWarnings("ConstantConditions")
    protected String fetchClientIpAddr() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
        if (ip.equals(LOCAL_HOST_V6)) ip = LOCAL_HOST;
        return ip;
    }

    @Async
    public void fetchLocationAndEmail() {
        String clientIp = fetchClientIpAddr();
        if (!clientIp.equals(LOCAL_HOST)) {
            locationService.getLocationByIpAddress(clientIp)
                    .thenAccept(location -> {
                        log.info("fetched location: {}", location);
                        emailService.sendEmail(envVariables.getDefaultGmailEmail(), new String[]{envVariables.getDefaultYahooEmail()},
                                "New Visit from: " + location.getCountry_name(), location.toString());
                    })
                    .exceptionally(exception -> {
                        log.error("Couldn't fetch remote address fo ip: {} and message: {}", clientIp, exception.getMessage());
                        return null;
                    });
        }
    }
}
