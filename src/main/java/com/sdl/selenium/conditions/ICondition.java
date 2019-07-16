package com.sdl.selenium.conditions;

public interface ICondition {

    boolean isSuccess();

    boolean isFail();

    boolean execute();

    boolean equals(String message);

    String getMessage();

    String getClassName();

    int getPriority();

    void setPriority(int priority);

    String getResultMessage();
}