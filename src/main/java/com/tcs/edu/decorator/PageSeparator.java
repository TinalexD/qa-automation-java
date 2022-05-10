package com.tcs.edu.decorator;

import com.tcs.edu.MessageDecorator;
import com.tcs.edu.Separator;

/**
 * Класс предназначен для разделения страниц
 */
public class PageSeparator implements Separator {

    /**
     * метод предназначен для разделения страниц через добавления разделителя с новой строки после сообщения
     *
     * @param message
     * @return строку с разделителем
     */
    public String separatePage(String message) {
        final var pageSeparator = "---";
        return String.format("%s %n %s", message, pageSeparator);
    }
}
