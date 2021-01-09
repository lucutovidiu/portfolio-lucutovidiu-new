package com.lucutovidiu.api.email;

import com.lucutovidiu.domain.configs.EnvVariables;
import com.lucutovidiu.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ContactApiImpl implements ContactApi {

    private final EmailService emailService;
    private final EnvVariables envVariables;

    @Override
    public boolean sendContactEmail(ContactRequest request) {
        return emailService.sendEmail(envVariables.getDefaultGmailEmail(),
                new String[]{envVariables.getDefaultYahooEmail()},
                "New Email from My PortFolio Website",
                createHtmlEmail(request));
    }

    private String createHtmlEmail(ContactRequest request) {
        return "New Email from: " + request.getSenderName() + "\n" +
                "Email: " + request.getSenderEmail() + "\n\n" +
                "Message:" +
                request.getSenderMessage() + "\n";
    }
}
