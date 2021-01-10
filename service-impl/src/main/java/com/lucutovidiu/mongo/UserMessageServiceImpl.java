package com.lucutovidiu.mongo;

import com.lucutovidiu.models.UserMessage;
import com.lucutovidiu.pojo.ContactRequest;
import com.lucutovidiu.repos.UserMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserMessageServiceImpl implements UserMessageService {

    private final UserMessageRepository userMessageRepository;

    @Override
    public List<UserMessage> getUserMessages() {
        return userMessageRepository.findAll();
    }

    @Override
    public UserMessage saveUserMessage(ContactRequest contactRequest) {
        UserMessage userMessage = new UserMessage();
        userMessage.setLocation(contactRequest.getLocation());
        userMessage.setSenderName(contactRequest.getSenderName());
        userMessage.setSenderEmail(contactRequest.getSenderEmail());
        userMessage.setSenderMessage(contactRequest.getSenderMessage());
        return userMessageRepository.save(userMessage);
    }
}
