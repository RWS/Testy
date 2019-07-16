package com.sdl.selenium.conditions;

public abstract class SuccessCondition extends Condition {

    public SuccessCondition() {
    }

    public SuccessCondition(String message) {
        super(message);
    }

    public SuccessCondition(String message, String className) {
        super(message, className);
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}
