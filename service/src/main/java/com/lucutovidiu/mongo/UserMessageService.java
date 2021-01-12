package com.lucutovidiu.mongo;

import com.lucutovidiu.models.UserMessageEntity;
import com.lucutovidiu.pojo.ContactRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserMessageService {

    List<UserMessageEntity> getUserMessages();

    UserMessageEntity saveUserMessage(ContactRequest contactRequest);
}
