package com.tcs.edu.decorator;

import java.time.Instant;

/**
 * Класс для декорирования текста сообщений временем
 *
 * @author a.v.demchenko
 */
public class TimestampMessageDecorator {
    public static int messageCount = 1;

    /**
     * Метод для декорирования сообщения временем в формате timestamp
     *
     * @param message сообщение, которое необходимо задекорировать
     * @return переменная с задекорированным сообщением
     */
    public static String decorate(String message) {
        final var decoratedMessage = messageCount + " " + Instant.now() + " " + message;
        return decoratedMessage;
    }
}
