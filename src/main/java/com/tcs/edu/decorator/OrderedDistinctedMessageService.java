package com.tcs.edu.decorator;

import com.tcs.edu.LogException;
import com.tcs.edu.MessageDecorator;
import com.tcs.edu.repository.HashMapMessageRepository;
import com.tcs.edu.repository.MessageRepository;
import com.tcs.edu.Printer;
import com.tcs.edu.MessageService;
import com.tcs.edu.Separator;
import com.tcs.edu.domain.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import static com.tcs.edu.decorator.SeverityMapper.severityMapper;
import static com.tcs.edu.decorator.TimestampMessageDecorator.messageCount;
import static com.tcs.edu.decorator.TimestampMessageDecorator.pageSize;


/**
 * Класс-ядро, который выполняет необходимые действия с ообщением
 *
 * @author a.v.demchenko
 */
public class OrderedDistinctedMessageService extends ValidatedService implements MessageService {
    private Printer printer;
    private MessageDecorator time;
    private Separator page;
    private MessageRepository messageRepository = new HashMapMessageRepository();

    public OrderedDistinctedMessageService(Printer printer, MessageDecorator time, Separator page) {
        this.printer = printer;
        this.time = time;
        this.page = page;
    }

    /**
     * метод, который предназначен для преобразования сообщения в необходимый вид с последующим выводом его на консоль
     *
     * @param message  получаемое сообщение
     * @param massages список сообщений
     */
    public void process(Message message, Message... massages) {
        ArrayList<Message> listOfMessages = new ArrayList<>();
        listOfMessages.add(message);
        Collections.addAll(listOfMessages, massages);

        for (Message currentMessage : listOfMessages) {
            try {
                super.isArgsValid(currentMessage);
                if (messageCount % pageSize == 0) {
                    String decoratedCurrentMessage = String.format("%s %s", time.decorate(currentMessage), severityMapper(currentMessage.getLevel()));
                    currentMessage.setDecoratedMassage(page.separatePage(decoratedCurrentMessage));
                    messageRepository.create(currentMessage);
                } else {
                    currentMessage.setDecoratedMassage(
                            String.format("%s %s",
                            time.decorate(currentMessage),
                            severityMapper(currentMessage.getLevel())));
                    messageRepository.create(currentMessage);
                }
            } catch (IllegalArgumentException e) {
                throw new LogException("notValidArgMessage", e);
            }
        }
    }

    /**
     * @param doubling       режим дублирования
     * @param message        получаемое сообщение
     * @param sortedMessages список сообщений
     */
    public void process(Doubling doubling, Message message, Message... sortedMessages) {
        if (doubling == Doubling.DISTINCT) {
            ArrayList<Message> listOfMassages = new ArrayList<>();
            ArrayList<String> listOfDublicates = new ArrayList<>();
            try {
                super.isArgsValid(message);
                listOfDublicates.add(message.getMessage());
                int filterCount = 0;
                for (int i = 0; i < sortedMessages.length - 1; i++) {
                    super.isArgsValid(sortedMessages[i]);
                    if (!listOfDublicates.contains(sortedMessages[i].getMessage())) {
                        listOfDublicates.add(sortedMessages[i].getMessage());
                        listOfMassages.add(sortedMessages[i]);
                    }
                }

                Message[] filteredMessages = listOfMassages.stream().toArray(Message[]::new);
                process(message, filteredMessages);
            } catch (IllegalArgumentException e) {
                throw new LogException("notValidArgMessage", e);
            }
        } else {
            process(message, sortedMessages);
        }


    }

    /**
     * @param order    порядок сортировки
     * @param doubling режим дублирования
     * @param message  получаемое сообщение
     * @param massages список сообщений
     */
    public void process(MessageOrder order, Doubling doubling, Message message, Message... massages) {
        Message[] sortedMessages = new Message[massages.length];
        if (order == MessageOrder.DESC) {
            int messageNumber = 0;
            for (int i = massages.length - 2; i >= 0; i--) {
                sortedMessages[messageNumber] = massages[i];
                messageNumber++;
            }
            sortedMessages[sortedMessages.length - 1] = message;
            process(doubling, massages[massages.length - 1], sortedMessages);
        } else {
            process(doubling, message, massages);
        }
    }


}
