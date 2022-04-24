package com.tcs.edu.printer;

import static com.tcs.edu.decorator.TimestampMessageDecorator.messageCount;

/**
 * Класс предназначен для вывода сообщений на консоль
 *
 * @author e.krivosheyev
 */
public class ConsolePrinter {

    /**
     * Метод выводит переданное сообщение на консоль
     *
     * @param decoratedMessage задекорированное сообщение которое надо вывести на консоль
     */
    public static void print(String decoratedMessage) {
        messageCount++;
        System.out.println(decoratedMessage);
    }
}
