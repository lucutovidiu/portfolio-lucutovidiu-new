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
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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

    public void sendHtmlEmail(String from, String[] to, String subject, String body) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        //mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
        try {
            helper.setText(body, true); // Use this or above line.
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(from);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void sendLocationEmail(Location location) {
        if (envVariables.shouldLocationBeEmailed())
            sendEmail(envVariables.getDefaultGmailEmail(), new String[]{envVariables.getDefaultYahooEmail()},
                    "New Visit from: " + location.getCountry_name(), location.toString());
    }

    @Override
    public void sendExpiredProductsEmail(String emailSubject, String msg, String emailAddress) {
        if (envVariables.shouldExpiredProductsBeEmailed()) {
            String[] emails;
            if (!emailAddress.equalsIgnoreCase(envVariables.getDefaultYahooEmail()))
                emails = new String[]{envVariables.getDefaultYahooEmail(), emailAddress};
            else
                emails = new String[]{emailAddress};
            sendHtmlEmail(envVariables.getDefaultGmailEmail(), emails,
                    emailSubject, msg);
        }
    }
}
