package com.lucutovidiu.mongo;

import com.lucutovidiu.models.UserMessageEntity;
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
    public List<UserMessageEntity> getUserMessages() {
        return userMessageRepository.findAll();
    }

    @Override
    public UserMessageEntity saveUserMessage(ContactRequest contactRequest) {
        UserMessageEntity userMessageEntity = new UserMessageEntity();
        userMessageEntity.setLocation(contactRequest.getLocation());
        userMessageEntity.setSenderName(contactRequest.getSenderName());
        userMessageEntity.setSenderEmail(contactRequest.getSenderEmail());
        userMessageEntity.setSenderMessage(contactRequest.getSenderMessage());
        return userMessageRepository.save(userMessageEntity);
    }
}
