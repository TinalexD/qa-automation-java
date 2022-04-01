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
     * @param message сообщение которое надо вывести на консоль
     */
    public static void print(String message) {
        messageCount++;
        System.out.println(message);
    }
}
