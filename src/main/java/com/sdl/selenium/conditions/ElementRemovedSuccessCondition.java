package com.sdl.selenium.conditions;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class ElementRemovedSuccessCondition extends SuccessCondition {
    private static final Logger logger = Logger.getLogger(ElementRemovedSuccessCondition.class);

    private WebLocator component;

    public ElementRemovedSuccessCondition(WebLocator component){
        this.component = component;
    }

    public ElementRemovedSuccessCondition(WebLocator component, int priority){
        this(component);
        setPriority(priority);
    }

    public boolean execute() {
        return !component.isElementPresent();
    }

    public String toString() {
        return super.toString() + component;
    }
}
