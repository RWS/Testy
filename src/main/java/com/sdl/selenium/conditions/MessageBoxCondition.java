package com.sdl.selenium.conditions;

public interface MessageBoxCondition extends ICondition {
    boolean execute(String boxMessage);
}