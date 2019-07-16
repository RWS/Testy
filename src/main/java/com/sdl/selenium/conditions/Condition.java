package com.sdl.selenium.conditions;

import com.google.common.base.Strings;

public abstract class Condition implements Comparable<Condition>, ICondition {

    private String message;
    private String resultMessage;
    private String className;

    private int priority = 5;

    @Override
    public int compareTo(Condition condition) {
        return getPriority() - condition.getPriority();
    }

    public boolean isTimeout() {
        return false;
    }

    @Override
    public boolean isFail() {
        return !isSuccess();
    }

    public Condition() {
    }

    public Condition(String message) {
        this.message = message;
    }

    public Condition(String message, String className) {
        this.message = message;
        this.className = className;
    }

    public Condition(String message, int priority) {
        this(message);
        this.priority = priority;
    }

    public Condition(String message, int priority, String className) {
        this(message);
        this.priority = priority;
        this.className = className;
    }

    @Override
    public String toString() {
        String msg = getClassName();
        if (Strings.isNullOrEmpty(msg)) {
            msg = "Condition@" + getMessage();
        } else {
            msg += ":Condition@" + getMessage();
        }
        return msg;
    }

    @Override
    public boolean equals(String message) {
        return message != null && message.equals(getMessage());
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String getResultMessage() {
        return resultMessage;
    }

    protected void setResultMessage(final String resultMessage) {
        this.resultMessage = resultMessage;
    }
}