package com.sdl.selenium.conditions;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.WebLocator;

// TODO is possible that ElementRemovedSuccessCondition is executed because some error messages have been arrived
// think about some solutions to fix this.
public class ElementRemovedSuccessCondition extends SuccessCondition implements RenderCondition {

    private Locator component;

    private ElementRemovedSuccessCondition(String message) {
        super(message);
    }

    public ElementRemovedSuccessCondition(Locator component) {
        this(component.toString());
        this.component = component;
    }

    public ElementRemovedSuccessCondition(WebLocator component, int priority) {
        this(component);
        setPriority(priority);
    }

    public boolean execute() {
        return !component.waitToRender(0L, false);
    }

    @Override
    public Locator getComponent() {
        return component;
    }

    /**
     * @return component.getText()
     */
    @Override
    public String getResultMessage() {
        return component.getXPathBuilder().getText();
    }

    public String toString() {
        return super.toString() + component;
    }
}