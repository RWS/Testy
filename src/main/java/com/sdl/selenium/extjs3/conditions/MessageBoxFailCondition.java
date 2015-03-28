package com.sdl.selenium.extjs3.conditions;

import com.sdl.selenium.conditions.FailCondition;
import com.sdl.selenium.conditions.MessageBoxCondition;
import com.sdl.selenium.extjs3.window.MessageBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageBoxFailCondition extends FailCondition implements MessageBoxCondition {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageBoxFailCondition.class);
    
    private boolean contains = false;

    public MessageBoxFailCondition(String message) {
        super(message);
    }

    public MessageBoxFailCondition(String message, int priority) {
        this(message);
        setPriority(priority);
    }

    public MessageBoxFailCondition(String message, boolean contains) {
        this(message);
        this.contains = contains;
    }

    public MessageBoxFailCondition(String message, boolean contains, int priority) {
        this(message, contains);
        setPriority(priority);
    }

    @Override
    public boolean execute() {
        return execute(MessageBox.getMessage());
    }


    @Override
    public boolean execute(final String boxMessage){
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