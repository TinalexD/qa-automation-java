package com.tcs.edu.decorator;

import java.util.Arrays;

import java.util.ArrayList;
import java.util.Collections;

import static com.tcs.edu.printer.ConsolePrinter.print;
import static com.tcs.edu.decorator.TimestampMessageDecorator.decorate;
import static com.tcs.edu.decorator.SeverityMapper.severityMapper;
import static com.tcs.edu.decorator.TimestampMessageDecorator.messageCount;
import static com.tcs.edu.decorator.TimestampMessageDecorator.pageSize;
import static com.tcs.edu.decorator.PageSeparator.separatePage;


/**
 * Класс-ядро, который выполняет необходимые действия с ообщением
 *
 * @author
 */
public class MessageService {

    /**
     * метод, который предназначен для преобразования сообщения в необходимый вид с последующим выводом его на консоль
     *
     * @param level    урочень важности сообщения
     * @param message  получаемое сообщение
     * @param massages список сообщений
     */
    public static void process(Severity level, String message, String... massages) {
        ArrayList<String> listOfMessages = new ArrayList<>();
        listOfMessages.add(message);
        Collections.addAll(listOfMessages, massages);

        for (String currentMessage : listOfMessages) {
            if (currentMessage != null) {
                if (messageCount % pageSize == 0) {
                    String decoratedCurrentMessage = String.format("%s %s", decorate(currentMessage), severityMapper(level));
                    print(separatePage(decoratedCurrentMessage));
                } else {
                    print(String.format("%s %s", decorate(currentMessage), severityMapper(level)));
                }
            }
        }
    }

    /**
     * @param level    урочень важности сообщения
     * @param order    порядок сортировки
     * @param message  получаемое сообщение
     * @param massages список сообщений
     */
    public static void process(Severity level, MessageOrder order, Doubling doubling, String message, String... massages) {
        ArrayList<String> sortedMassages = new ArrayList<>();
        sortedMassages.add(message);
        if (order == MessageOrder.DESC) {
            for (String massage : massages) {
                sortedMassages.add(0, massage);
            }
        } else {
            sortedMassages.addAll(Arrays.asList(massages));
        }

        String[] filteredMessages = new String[massages.length + 1];
        if (doubling == Doubling.DISTINCT) {
            int filterCount = 0;
            for (String currentMessage : sortedMassages) {
                if (!Arrays.asList(filteredMessages).contains(currentMessage)) {
                    filteredMessages[filterCount] = currentMessage;
                    filterCount++;
                }
            }
        } else {
            int filterCount = 0;
            for (String currentMessage : sortedMassages) {
                filteredMessages[filterCount] = currentMessage;
                filterCount++;
            }
        }
        process(level, null, filteredMessages);
    }
}
