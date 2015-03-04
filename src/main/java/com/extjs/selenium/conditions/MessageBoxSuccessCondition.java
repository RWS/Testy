package com.extjs.selenium.conditions;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class MessageBoxSuccessCondition extends com.sdl.selenium.extjs3.conditions.MessageBoxSuccessCondition {

    public MessageBoxSuccessCondition(String message) {
        super(message);
    }

    public MessageBoxSuccessCondition(String message, int priority) {
        super(message, priority);
    }

    public MessageBoxSuccessCondition(String message, boolean contains) {
        super(message, contains);
    }

    public MessageBoxSuccessCondition(String message, boolean contains, int priority) {
        super(message, contains, priority);
    }
}