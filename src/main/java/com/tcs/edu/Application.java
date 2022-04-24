package com.tcs.edu;

import static com.tcs.edu.decorator.MessageService.process;

import com.tcs.edu.decorator.Severity;


class Application {
    public static void main(String[] args) {
        process(Severity.MINOR, null, "Hello", null, "world!", "Hello", null, "world!", "Hello", null, "world!");
    }
}