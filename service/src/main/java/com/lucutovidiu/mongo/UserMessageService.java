package com.lucutovidiu.mongo;

import com.lucutovidiu.models.UserMessage;
import com.lucutovidiu.pojo.ContactRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserMessageService {

    List<UserMessage> getUserMessages();

    UserMessage saveUserMessage(ContactRequest contactRequest);
}
