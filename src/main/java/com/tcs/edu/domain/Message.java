package com.tcs.edu.domain;

import com.tcs.edu.decorator.Severity;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", level=" + level +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(getMessage(), message1.getMessage()) &&
                getLevel() == message1.getLevel();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getLevel());
    }

    public Severity getLevel() {
        return level;
    }
}
