package com.lucutovidiu.pages;

import com.lucutovidiu.expiredprod.ExpiredProducts;
import com.lucutovidiu.ip.LocationService;
import com.lucutovidiu.mongo.UserVisitService;
import com.lucutovidiu.service.EmailService;
import com.lucutovidiu.util.EnvVariables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.lucutovidiu.util.PageAttributesUtil.ActivePage;
import static com.lucutovidiu.util.Pages.HOME;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HomeImpl implements Home {

    private final LocationService locationService;
    private final EmailService emailService;
    private final UserVisitService userVisitService;
    private final EnvVariables envVariables;
    private final ExpiredProducts expiredProducts;

    @Override
    public String getIndex(Model model, HttpServletRequest request) {
        model.addAttribute(ActivePage, HOME);
        saveUserVisitAndEmail();
        expiredProducts.emailExpiredProducts();
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

    @Async
    public void saveUserVisitAndEmail() {
        locationService.getUserLocation()
                .ifPresent(userLocation -> {
                    if (envVariables.shouldSaveLocation(userLocation) && envVariables.shouldSaveOrg(userLocation.getOrg())) {
                        emailService.sendLocationEmail(userLocation);
                        userVisitService.saveVisit(userLocation);
                    }
                });
    }
}
