package com.sdl.selenium.conditions;

import com.sdl.selenium.web.WebLocator;

public abstract class FailCondition extends Condition {

    public FailCondition() {
    }

    public FailCondition(String message) {
        super(message);
    }

    public FailCondition(String message, String className) {
        super(message, className);
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    public static FailCondition fail() {
        return new FailCondition() {
            @Override
            public boolean execute() {
                return true;
            }
        };
    }

    public static FailCondition fail(String message) {
        return new FailCondition(message) {
            @Override
            public boolean execute() {
                return true;
            }
        };
    }

    /**
     * Return new FailCondition with message: Could not click on {WebLocator}
     *
     * @param element element
     * @return <code>FailCondition</code>
     */
    public static FailCondition clickFail(WebLocator element) {
        return fail("Could not click on " + element);
    }
}
