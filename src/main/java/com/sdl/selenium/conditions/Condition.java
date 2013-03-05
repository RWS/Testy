package com.sdl.selenium.conditions;

public abstract class Condition implements Comparable<Condition>{
    private String message;
    public abstract boolean isSuccess();
    public abstract boolean execute();

    private int priority = 5;

    @Override
    public int compareTo(Condition condition) {
        return getPriority() - condition.getPriority();
    }

    public boolean isTimeout(){
        return false;
    }

    public boolean isFail(){
        return !isSuccess();
    }

    public Condition(){

    }

    public Condition(String message){
        this.message = message;
    }

    public Condition(String message, int priority){
        this(message);
        this.priority = priority;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@";
    }

    public boolean equals(String message){
        return message != null && message.equals(getMessage());
    }

    public String getMessage() {
        return message;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}