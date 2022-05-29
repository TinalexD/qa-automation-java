package com.tcs.edu;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.domain.Message;

import java.util.Collection;
import java.util.UUID;

public interface MessageService {
    void process(Message message, Message... massages);

    void process(Doubling doubling, Message message, Message... sortedMessages);

    void process(MessageOrder order, Doubling doubling, Message message, Message... massages);

    Message findByPrimaryKey(UUID key);

    Collection<Message> findAll();

}
