package com.tcs.edu;

import static com.tcs.edu.decorator.MessageService.process;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.decorator.Severity;


class Application {
    public static void main(String[] args) {
        process(Severity.MINOR,
                MessageOrder.ASC,
                Doubling.DOUBLES,
                "Hello world 1!",
                "Hello world 1!",
                "Hello world 2!",
                null,
                "Hello world 3!",
                "Hello world 4!",
                "Hello world 4!",
                "Hello world 5!",
                "Hello world 6!");
    }
}