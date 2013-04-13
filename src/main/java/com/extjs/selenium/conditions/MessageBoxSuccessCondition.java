package com.extjs.selenium.conditions;

import com.extjs.selenium.window.MessageBox;
import com.sdl.selenium.conditions.SuccessCondition;
import org.apache.log4j.Logger;

public class MessageBoxSuccessCondition extends SuccessCondition {
    private static final Logger logger = Logger.getLogger(MessageBoxSuccessCondition.class);
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

    public boolean execute() {
        boolean executed = false;
        String boxMessage = MessageBox.getMessage();
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