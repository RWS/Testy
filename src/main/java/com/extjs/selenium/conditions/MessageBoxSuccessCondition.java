package com.extjs.selenium.conditions;

import com.extjs.selenium.window.MessageBox;
import com.sdl.selenium.conditions.SuccessCondition;
import org.apache.log4j.Logger;

public class MessageBoxSuccessCondition extends SuccessCondition {
    private static final Logger logger = Logger.getLogger(MessageBoxSuccessCondition.class);
    private boolean contains = false;

    public MessageBoxSuccessCondition(String message){
        super(message);
        setPriority(3);
    }

    public MessageBoxSuccessCondition(String message, int priority){
        this(message);
        setPriority(priority);
    }

    public MessageBoxSuccessCondition(String message, boolean contains){
        this(message);
        this.contains = contains;
    }

    public MessageBoxSuccessCondition(String message, boolean contains, int priority){
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
