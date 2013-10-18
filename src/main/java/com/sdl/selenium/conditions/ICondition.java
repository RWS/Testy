package com.sdl.selenium.conditions;

public interface ICondition {

    boolean isSuccess();

    boolean isFail();

    boolean execute();

    public boolean equals(String message);

    String getMessage();

    int getPriority();

    void setPriority(int priority);

    String getResultMessage();

}
