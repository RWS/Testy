package com.sdl.selenium.conditions;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO is possible that ElementRemovedSuccessCondition is executed because some error messages have been arrived
// think about some solutions to fix this.
public class ElementRemovedSuccessCondition extends SuccessCondition implements RenderCondition {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElementRemovedSuccessCondition.class);

    private WebLocator component;

    private ElementRemovedSuccessCondition(String message) {
        super(message);
    }

    public ElementRemovedSuccessCondition(WebLocator component) {
        this(component.toString());
        this.component = component;
    }

    public ElementRemovedSuccessCondition(WebLocator component, int priority) {
        this(component);
        setPriority(priority);
    }

    public boolean execute() {
        return !component.isElementPresent();
    }

    @Override
    public WebLocator getComponent() {
        return component;
    }

    /**
     *
     * @return component.getText()
     */
    @Override
    public String getResultMessage() {
        return component.getText();
    }

    public String toString() {
        return super.toString() + component;
    }
}
