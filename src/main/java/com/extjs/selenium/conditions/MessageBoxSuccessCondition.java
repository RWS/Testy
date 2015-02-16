package com.extjs.selenium.conditions;

import com.extjs.selenium.window.MessageBox;
import com.sdl.selenium.conditions.MessageBoxCondition;
import com.sdl.selenium.conditions.SuccessCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageBoxSuccessCondition extends SuccessCondition implements MessageBoxCondition {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageBoxSuccessCondition.class);
    
    private boolean contains = false;

    public MessageBoxSuccessCondition(String message) {
        super(message);
        setPriority(3);
    }

    public MessageBoxSuccessCondition(String message, int priority) {
        this(message);
        setPriority(priority);
    }

    public MessageBoxSuccessCondition(String message, boolean contains) {
        this(message);
        this.contains = contains;
    }

    public MessageBoxSuccessCondition(String message, boolean contains, int priority) {
        this(message, contains);
        setPriority(priority);
    }

    @Override
    public boolean execute() {
        return execute(MessageBox.getMessage());
    }

    @Override
    public boolean execute(final String boxMessage) {
        boolean executed = false;
        if (contains) {
            if (boxMessage != null && boxMessage.contains(getMessage())) {
                executed = true;
            }
        } else if (getMessage().equals(boxMessage)) {
            executed = true;
        }
        if (executed) {
            this.setResultMessage(boxMessage);
        }
        return executed;
    }

    public String toString() {
        return super.toString() + getMessage();
    }
}