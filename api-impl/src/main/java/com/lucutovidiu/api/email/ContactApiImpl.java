package com.lucutovidiu.api.email;

import com.lucutovidiu.ip.LocationService;
import com.lucutovidiu.mongo.UserMessageService;
import com.lucutovidiu.pojo.ContactRequest;
import com.lucutovidiu.service.EmailService;
import com.lucutovidiu.util.EnvVariables;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ContactApiImpl implements ContactApi {

    private final EmailService emailService;
    private final EnvVariables envVariables;
    private final UserMessageService userMessageService;
    private final LocationService locationService;

    @Override
    public boolean sendContactEmailAndSave(ContactRequestDto request) {
        saveUserMessage(request);
        return emailService.sendEmail(envVariables.getDefaultGmailEmail(),
                new String[]{envVariables.getDefaultYahooEmail()},
                "New Email from My PortFolio Website",
                createHtmlEmail(request));
    }

    private void saveUserMessage(ContactRequestDto request) {
        ContactRequest contactRequest = request.toContactRequest();
        contactRequest.setLocation(locationService.getUserLocation().orElse(null));
        userMessageService.saveUserMessage(contactRequest);
    }

    private String createHtmlEmail(ContactRequestDto request) {
        return "New Email from: " + request.getSenderName() + "\n" +
                "Email: " + request.getSenderEmail() + "\n\n" +
                "Message:" +
                request.getSenderMessage() + "\n";
    }
}
