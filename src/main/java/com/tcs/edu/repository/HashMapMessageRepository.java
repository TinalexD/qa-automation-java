package com.tcs.edu.repository;

import com.tcs.edu.repository.MessageRepository;
import com.tcs.edu.domain.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HashMapMessageRepository implements MessageRepository {
    private Map<UUID, Message> messages = new HashMap();

    public UUID create(Message message){
        message.setId(UUID.randomUUID());
        messages.put(message.getId(), message);
        return message.getId();
    }
}
