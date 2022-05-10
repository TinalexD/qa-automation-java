package com.tcs.edu;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.decorator.MessageService;
import com.tcs.edu.decorator.Severity;
import com.tcs.edu.domain.Message;


class Application {
    public static void main(String[] args) {
        Message message1 = new Message("Hello world 1!", Severity.MINOR);
        Message message2 = new Message("Hello world 1!", Severity.MINOR);
        Message message3 = new Message("Hello world 1!");
        Message message4 = new Message("Hello world 2!");
        Message message5 = new Message("Hello world 3!", Severity.MINOR);
        Message message6 = new Message("Hello world 4!", Severity.MAJOR);

        Processor Service = new MessageService();
        Service.process(MessageOrder.ASC, Doubling.DISTINCT,
                message1,
                message2,
                message3,
                message4,
                message5,
                message6
        );
    }
}