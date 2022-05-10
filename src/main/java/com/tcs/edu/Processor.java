package com.tcs.edu;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.domain.Message;

public interface Processor {
    void process(Message message, Message... massages);
    void process(Doubling doubling, Message message, Message... sortedMessages);
    void process(MessageOrder order, Doubling doubling, Message message, Message... massages);
}
