package com.sdl.selenium.extjs3.conditions;

import com.sdl.selenium.conditions.Condition;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.MessageBoxCondition;
import com.sdl.selenium.extjs3.window.MessageBox;

import java.time.Duration;

/**
 * Example how to add conditions to ConditionManager:
 * <pre>
 * ConditionManager manager = new ConditionManager().add(new SuccessCondition() {
 * public boolean execute() {
 * return logoutButton.isElementPresent();
 * }
 * });
 * </pre>
 * OR more specific cases:
 * <pre>
 * ConditionManager manager = new ConditionManager();
 * manager.add(
 * new MessageBoxFailCondition("Wrong email or password.")).add(
 * new RenderSuccessCondition(logoutButton)
 * );
 * Condition condition = manager.execute();
 * logged = condition.isSuccess();
 * </pre>
 */
public class ExtjsConditionManager extends ConditionManager {

    /**
     * default timeout in milliseconds is 10000.
     */
    public ExtjsConditionManager() {
        super();
    }

    /**
     * @param timeout milliseconds
     *  @deprecated use {@link #ExtjsConditionManager(Duration)}
     */
    public ExtjsConditionManager(long timeout) {
        this();
        setTimeout(timeout);
    }

    public ExtjsConditionManager(Duration duration) {
        this();
        setDuration(duration);
    }

    @Override
    protected Condition findCondition() {
        String boxMessage = null;
        for (Condition condition : getConditionList()) {
            if (condition instanceof MessageBoxCondition) {
                if (boxMessage == null) {
                    boxMessage = MessageBox.getMessage();
                }
                if (((MessageBoxCondition) condition).execute(boxMessage)) {
                    return condition;
                }
            } else {
                if (condition.execute()) {
                    return condition;
                }
            }
        }
        return null;
    }

    /**
     * add each message to MessageBoxFailCondition
     *
     * @param messages messages
     * @return this
     */
    public ExtjsConditionManager addFailConditions(String... messages) {
        for (String message : messages) {
            if (message != null && message.length() > 0) {
                this.add(new MessageBoxFailCondition(message));
            }
        }
        return this;
    }

    /**
     * add each message to MessageBoxSuccessCondition
     *
     * @param messages messages
     * @return this
     */
    public ExtjsConditionManager addSuccessConditions(String... messages) {
        for (String message : messages) {
            if (message != null && message.length() > 0) {
                this.add(new MessageBoxSuccessCondition(message));
            }
        }
        return this;
    }
}