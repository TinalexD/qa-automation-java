package com.tcs.edu;

import static com.tcs.edu.decorator.MessageService.process;
import com.tcs.edu.decorator.Severity;

import com.tcs.edu.decorator.Severity;
import com.tcs.edu.printer.ConsolePrinter;

class Application {
    public static void main(String[] args) {
        process(Severity.NONE,"Hello world!");
        process(Severity.MINOR,"Hello world!");
        process(Severity.MINOR,"Hello world!");
        process(Severity.MAJOR,"Hello world!");
        process(Severity.NONE,"Hello world!");
        process(Severity.MINOR,"Hello world!");
    }
}