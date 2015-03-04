package com.extjs.selenium.conditions;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class MessageBoxFailCondition extends com.sdl.selenium.extjs3.conditions.MessageBoxFailCondition {


    public MessageBoxFailCondition(String message) {
        super(message);
    }

    public MessageBoxFailCondition(String message, int priority) {
        super(message, priority);
    }

    public MessageBoxFailCondition(String message, boolean contains) {
        super(message, contains);
    }

    public MessageBoxFailCondition(String message, boolean contains, int priority) {
        super(message, contains, priority);
    }
}