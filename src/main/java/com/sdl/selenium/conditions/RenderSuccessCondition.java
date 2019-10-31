package com.sdl.selenium.conditions;

import com.sdl.selenium.web.WebLocator;

public class RenderSuccessCondition extends SuccessCondition implements RenderCondition {

    private WebLocator component;

    private RenderSuccessCondition(String message) {
        super(message);
    }

    private RenderSuccessCondition(String message, String className) {
        super(message, className);
    }

    public RenderSuccessCondition(WebLocator component) {
        this(component.toString());
        this.component = component;
    }

    public RenderSuccessCondition(WebLocator component, String className) {
        this(component.toString(), className);
        this.component = component;
    }

    public RenderSuccessCondition(WebLocator component, int priority) {
        this(component);
        setPriority(priority);
    }

    public RenderSuccessCondition(WebLocator component, int priority, String className) {
        this(component, className);
        setPriority(priority);
    }

    @Override
    public boolean execute() {
        return component.isPresent();
    }

//    @Override
//    public String toString() {
//        return super.toString() + component;
//    }

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
}