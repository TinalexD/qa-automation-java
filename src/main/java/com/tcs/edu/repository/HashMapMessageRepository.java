package com.tcs.edu.repository;

import com.tcs.edu.decorator.Severity;
import com.tcs.edu.repository.MessageRepository;
import com.tcs.edu.domain.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class HashMapMessageRepository implements MessageRepository {
    private Map<UUID, Message> messages = new HashMap();

    @Override
    public UUID create(Message message) {
        message.setId(UUID.randomUUID());
        messages.put(message.getId(), message);
        return message.getId();
    }

    @Override
    public Message findByPrimaryKey(UUID key) {
        return messages.get(key);
    }

    @Override
    public Collection<Message> findAll() {
        return messages.values();
    }

    @Override
    public Collection<Message> findBySeverity(Severity by) {
        return messages
                .values()
                .stream()
                .filter(message -> message.getLevel() == by)
                .collect(Collectors.toList());
    }
}
