package com.tcs.edu.decorator;

import java.util.Arrays;

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
        if (message != null) {
            if (messageCount % pageSize == 0) {
                String preparedMessage = String.format("%s %s", decorate(message), severityMapper(level));
                print(separatePage(preparedMessage));
            } else {
                print(String.format("%s %s", decorate(message), severityMapper(level)));
            }
        }

        for (String currentMessage : massages) {
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
        if (message != null) {
            if (messageCount % pageSize == 0) {
                String preparedMessage = String.format("%s %s", decorate(message), severityMapper(level));
                print(separatePage(preparedMessage));
            } else {
                print(String.format("%s %s", decorate(message), severityMapper(level)));
            }
        }

        String[] sortedMassages = new String[massages.length];
        if (order == MessageOrder.DESC) {
            int decorCounter = 0;
            for (int counter = sortedMassages.length - 1; counter >= 0; counter--) {
                sortedMassages[decorCounter] = massages[counter];
                decorCounter++;
            }
        } else {
            sortedMassages = massages;
        }

        if (doubling == Doubling.DISTINCT) {
            String[] filteredMessage = new String[sortedMassages.length];
            int filterCount = 0;
            for (String currentMessage : sortedMassages) {
                if (currentMessage != null) {
                    if (!Arrays.asList(filteredMessage).contains(currentMessage)) {
                        filteredMessage[filterCount] = currentMessage;
                        filterCount++;
                        if (messageCount % pageSize == 0) {
                            String decoratedCurrentMessage = String.format("%s %s", decorate(currentMessage), severityMapper(level));
                            print(separatePage(decoratedCurrentMessage));
                        } else {
                            print(String.format("%s %s", decorate(currentMessage), severityMapper(level)));
                        }
                    } else {
                        continue;
                    }
                }
            }

        } else {
            for (String currentMessage : sortedMassages) {
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


    }
}
