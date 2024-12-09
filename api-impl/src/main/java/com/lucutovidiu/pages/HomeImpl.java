package com.lucutovidiu.pages;

import com.lucutovidiu.async.Job;
import com.lucutovidiu.async.JobManager;
import com.lucutovidiu.expiredprod.ExpiredProducts;
import com.lucutovidiu.ip.LocationService;
import com.lucutovidiu.mongo.UserVisitService;
import com.lucutovidiu.service.EmailService;
import com.lucutovidiu.util.EnvVariables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.lucutovidiu.async.Job.JobTypes.ExpiredProductsEmail;
import static com.lucutovidiu.async.Job.JobTypes.UserVisitAndEmail;
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
//    private final JobManager jobManager = JobManager.getInstance();

    @Override
    public String getIndex(Model model, HttpServletRequest request) {
        model.addAttribute(ActivePage, HOME);
//        jobManager.addJobToQueue(new Job(UserVisitAndEmail, this::saveUserVisitAndEmail));
//        jobManager.addJobToQueue(new Job(ExpiredProductsEmail, expiredProducts::emailExpiredProducts));
        saveUserVisitAndEmail();
//        expiredProducts.emailExpiredProducts();
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

    public void saveUserVisitAndEmail() {
        locationService.getUserLocation()
                .filter(userLocation -> userLocation.getCity() != null)
                .ifPresent(userLocation -> {
                    if (envVariables.shouldSaveByOptionAndValue(userLocation)) {
                        emailService.sendLocationEmail(userLocation);
                        userVisitService.saveVisit(userLocation);
                    }
                });
    }
}
