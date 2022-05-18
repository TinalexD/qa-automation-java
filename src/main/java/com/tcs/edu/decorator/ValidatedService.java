package com.tcs.edu.decorator;

import com.tcs.edu.domain.Message;

public abstract class ValidatedService {
    public boolean isArgsValid(Message currentMessage) {
        if (currentMessage == null) return false;
        return currentMessage.getMessage() != null;
    }
}
