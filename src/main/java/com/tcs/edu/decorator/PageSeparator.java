package com.tcs.edu.decorator;

/**
 * Класс предназначен для разделения страниц
 */
public class PageSeparator {

    /**
     * метод предназначен для разделения страниц через добавления разделителя с новой строки после сообщения
     *
     * @param message
     * @return строку с разделителем
     */
    public static String separatePage(String message) {
        final var pageSeparator = "---";
        return String.format("%s %n %s", message, pageSeparator);
    }
}
