package com.sdl.selenium.conditions;

import com.google.common.base.Strings;
import com.sdl.selenium.web.WebLocator;

// TODO is possible that ElementRemovedSuccessCondition is executed because some error messages have been arrived
// think about some solutions to fix this.
public class ElementRemovedSuccessCondition extends SuccessCondition implements RenderCondition {

    private WebLocator component;

    private ElementRemovedSuccessCondition(String message) {
        super(message);
    }

    private ElementRemovedSuccessCondition(String message, String className) {
        super(message, className);
    }

    public ElementRemovedSuccessCondition(WebLocator component) {
        this(component.toString());
        this.component = component;
    }

    public ElementRemovedSuccessCondition(WebLocator component, String className) {
        this(component.toString(), className);
        this.component = component;
    }

    public ElementRemovedSuccessCondition(WebLocator component, int priority) {
        this(component);
        setPriority(priority);
    }

    public ElementRemovedSuccessCondition(WebLocator component, int priority, String className) {
        this(component, className);
        setPriority(priority);
    }

    public boolean execute() {
        return !component.waitToRender(0L, false);
    }

    @Override
    public WebLocator getComponent() {
        return component;
    }

    /**
     * @return component.getText()
     */
    @Override
    public String getResultMessage() {
        return component.getText();
    }

    public String toString() {
        String msg = getClassName();
        if (Strings.isNullOrEmpty(msg)) {
            msg = "ElementRemovedSuccessCondition@" + getMessage();
        } else {
            msg += ":ElementRemovedSuccessCondition@" + getMessage();
        }
        return msg;
    }
}