package com.tcs.edu.decorator;

import java.time.Instant;

/**
 * Класс для декорирования текста сообщений временем
 * @author a.v.demchenko
 */
public class TimestampMessageDecorator {
    /**
     * Метод для декорирования сообщения временем в формате timestamp
     * @param message сообщение, которое необходимо задекорировать
     * @return задекорированное сообщение
     */
    public static String decorate(String message) {
        return Instant.now() + " " + message;
    }
}
