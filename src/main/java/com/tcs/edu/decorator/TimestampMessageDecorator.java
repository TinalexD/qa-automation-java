package com.tcs.edu.decorator;

import com.tcs.edu.domain.Message;

import java.time.Instant;

/**
 * Класс для декорирования текста сообщений временем
 *
 * @author a.v.demchenko
 */
public class TimestampMessageDecorator {
    public static int messageCount = 1;
    public static int pageSize = 2;

    /**
     * Метод для декорирования сообщения временем в формате timestamp
     *
     * @param message сообщение, которое необходимо задекорировать
     * @return переменная с задекорированным сообщением
     */
    public static String decorate(Message message) {
        final var decoratedMessage = String.format("%d %s %s", messageCount, Instant.now(), message.getMessage());
        return decoratedMessage;
    }

}
