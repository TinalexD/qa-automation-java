package com.tcs.edu.domain;

import com.tcs.edu.decorator.Severity;

public class Message {
    private String message;
    private Severity level;

    public Message(String message, Severity level) {
        this.message = message;
        this.level = level;
    }

    public Message() {
        this.message = null;
        this.level = null;
    }

    public Message(String message) {
        this.message = message;
        this.level = Severity.MINOR;
    }


    public String getMessage() {
        return message;
    }

    public Severity getLevel() {
        return level;
    }
}
