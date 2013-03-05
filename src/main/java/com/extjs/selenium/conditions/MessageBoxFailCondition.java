package com.extjs.selenium.conditions;

import com.extjs.selenium.window.MessageBox;
import com.sdl.selenium.conditions.FailCondition;
import org.apache.log4j.Logger;

public class MessageBoxFailCondition extends FailCondition {
    private static final Logger logger = Logger.getLogger(MessageBoxFailCondition.class);
    private boolean contains = false;

    public MessageBoxFailCondition(String message){
        super(message);
    }

    public MessageBoxFailCondition(String message, int priority){
        this(message);
        setPriority(priority);
    }

    public MessageBoxFailCondition(String message, boolean contains){
        this(message);
        this.contains = contains;
    }

    public MessageBoxFailCondition(String message, boolean contains, int priority){
        this(message, contains);
        setPriority(priority);
    }

    public boolean execute() {
        if(contains){
            String messageBox = MessageBox.getMessage();
            if(messageBox != null){
                return messageBox.contains(getMessage());
            }
        }
        return getMessage().equals(MessageBox.getMessage());
    }

    public String toString() {
        return super.toString() + getMessage();
    }
}
