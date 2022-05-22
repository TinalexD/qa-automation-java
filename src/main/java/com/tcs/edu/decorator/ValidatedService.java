package com.tcs.edu.decorator;

import com.tcs.edu.domain.Message;

public abstract class ValidatedService {
    public boolean isArgsValid(Message currentMessage) {
        if (currentMessage == null) throw new IllegalArgumentException("someArg is null");
        if (currentMessage.getMessage() == null) throw new IllegalArgumentException("otherArg is empty");
        return true;
    }
}
