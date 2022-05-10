package com.tcs.edu.decorator;

import com.tcs.edu.MessageDecorator;
import com.tcs.edu.Printer;
import com.tcs.edu.MessageService;
import com.tcs.edu.Separator;
import com.tcs.edu.domain.Message;

import java.util.Arrays;

import java.util.ArrayList;
import java.util.Collections;

import static com.tcs.edu.decorator.SeverityMapper.severityMapper;
import static com.tcs.edu.decorator.TimestampMessageDecorator.messageCount;
import static com.tcs.edu.decorator.TimestampMessageDecorator.pageSize;


/**
 * Класс-ядро, который выполняет необходимые действия с ообщением
 *
 * @author a.v.demchenko
 */
public class OrderedDistinctedMessageService implements MessageService {
    private Printer printer;
    private MessageDecorator time;
    private Separator page;

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
            if (currentMessage != null && currentMessage.getMessage() != null) {
                if (messageCount % pageSize == 0) {
                    String decoratedCurrentMessage = String.format("%s %s", time.decorate(currentMessage), severityMapper(currentMessage.getLevel()));
                    printer.print(page.separatePage(decoratedCurrentMessage));
                } else {
                    printer.print(String.format("%s %s", time.decorate(currentMessage), severityMapper(currentMessage.getLevel())));
                }
            }
        }
    }

    /**
     * @param doubling       режим дублирования
     * @param message        получаемое сообщение
     * @param sortedMessages список сообщений
     */
    public void process(Doubling doubling, Message message, Message... sortedMessages) {
        ArrayList<Message> listOfMassages = new ArrayList<>();
        listOfMassages.add(message);
        listOfMassages.addAll(Arrays.asList(sortedMessages));

        Message[] filteredMessages = new Message[sortedMessages.length + 1];
        int filterCount = 0;
        if (doubling == Doubling.DISTINCT) {
            ArrayList<String> listOfDublicates = new ArrayList<>();
            for (Message currentMessage : listOfMassages) {
                if (!listOfDublicates.contains(currentMessage.getMessage())) {
                    listOfDublicates.add(currentMessage.getMessage());
                    filteredMessages[filterCount] = currentMessage;
                    filterCount++;
                }
            }
        } else {
            for (Message currentMessage : listOfMassages) {
                filteredMessages[filterCount] = currentMessage;
                filterCount++;
            }
        }
        process(new Message(), filteredMessages);

    }

    /**
     * @param order    порядок сортировки
     * @param doubling режим дублирования
     * @param message  получаемое сообщение
     * @param massages список сообщений
     */
    public void process(MessageOrder order, Doubling doubling, Message message, Message... massages) {
        Message[] sortedMessages = new Message[massages.length + 1];
        int messageNumber = 0;
        if (order == MessageOrder.DESC) {
            for (int i = massages.length - 1; i >= 0; i--) {
                sortedMessages[messageNumber] = massages[i];
                messageNumber++;
            }
            sortedMessages[sortedMessages.length - 1] = message;
        } else {
            for (Message currentMessage : massages) {
                messageNumber++;
                sortedMessages[messageNumber] = currentMessage;
            }
            sortedMessages[0] = message;
        }
        process(doubling, new Message(), sortedMessages);
    }


}
