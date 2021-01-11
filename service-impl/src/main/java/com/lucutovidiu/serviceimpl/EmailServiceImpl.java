package com.lucutovidiu.serviceimpl;

import com.lucutovidiu.ip.Location;
import com.lucutovidiu.service.EmailService;
import com.lucutovidiu.util.EnvVariables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final EnvVariables envVariables;

    @Override
    public boolean sendEmail(String from, String[] to, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (MailException exception) {
            log.error("error sending email: {}", exception.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void sendLocationEmail(Location location) {
        if (envVariables.shouldLocationBeEmailed())
            sendEmail(envVariables.getDefaultGmailEmail(), new String[]{envVariables.getDefaultYahooEmail()},
                    "New Visit from: " + location.getCountry_name(), location.toString());
    }

    @Override
    public void sendExpiredProductsEmail() {
        if (envVariables.shouldExpiredProductsBeEmailed()) {
            sendEmail(envVariables.getDefaultGmailEmail(), new String[]{envVariables.getDefaultYahooEmail()},
                    "Expired Products Email", "this is just for test");
        }
    }
}
