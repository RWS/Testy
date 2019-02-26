package com.sdl.selenium.conditions;

import com.sdl.selenium.web.Locator;

public class RenderSuccessCondition extends SuccessCondition implements RenderCondition {

    private Locator component;

    private RenderSuccessCondition(String message) {
        super(message);
    }

    public RenderSuccessCondition(Locator component){
        this(component.toString());
        this.component = component;
    }

    public RenderSuccessCondition(Locator component, int priority){
        this(component);
        setPriority(priority);
    }

    @Override
    public boolean execute() {
        return component.isElementPresent();
    }

    @Override
    public String toString() {
        return super.toString() + component;
    }

    @Override
    public Locator getComponent(){
         return component;
    }

    /**
     *
     * @return component.getText()
     */
    @Override
    public String getResultMessage() {
        return component.getXPathBuilder().getText();
    }
}