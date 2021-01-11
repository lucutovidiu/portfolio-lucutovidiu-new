package com.lucutovidiu.automated;

import com.lucutovidiu.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class Scheduler {

    private final EmailService emailService;

    //@Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]"
    //cron every 2 hours
    @Scheduled(cron = "0 0 */2 * * ?")
    public void emailExpiredProducts() {
        emailService.sendExpiredProductsEmail();
    }
}
