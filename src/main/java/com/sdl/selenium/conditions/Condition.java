package com.sdl.selenium.conditions;

public abstract class Condition implements Comparable<Condition>, ICondition {

    private String message;
    private String resultMessage;

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

    public Condition() {}

    public Condition(String message) {
        this.message = message;
    }

    public Condition(String message, int priority) {
        this(message);
        this.priority = priority;
    }

    @Override
    public String toString() {
        String msg = getClass().getSimpleName();
        if("".equals(msg)) {
            msg = "Condition@" +  getMessage();
        } else {
            msg += "@";
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