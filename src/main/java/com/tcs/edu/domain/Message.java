package com.tcs.edu.domain;

import com.tcs.edu.decorator.Severity;
import static com.tcs.edu.decorator.TimestampMessageDecorator.messageCount;

import java.util.Objects;
import java.util.UUID;

public class Message {
    private String message;
    private Severity level;
    private String decoratedMassage;
    private UUID id;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDecoratedMassage() {
        return decoratedMassage;
    }

    public void setDecoratedMassage(String decoratedMassage) {
        messageCount++;
        this.decoratedMassage = decoratedMassage;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", level=" + level +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(getMessage(), message1.getMessage()) &&
                getLevel() == message1.getLevel() &&
                Objects.equals(getId(), message1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getLevel(), getId());
    }

    public Severity getLevel() {
        return level;
    }
}
