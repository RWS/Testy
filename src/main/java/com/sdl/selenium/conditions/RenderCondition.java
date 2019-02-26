package com.sdl.selenium.conditions;

import com.sdl.selenium.web.Locator;

/**
 * Usage:
 * <pre>
    Condition condition = conditionManager.execute();
    if(condition instanceof RenderCondition){
        RenderCondition renderCondition = (RenderCondition)condition;
        String result = renderCondition.getResultMessage(); //...
    }
 </pre>
 */
public interface RenderCondition extends ICondition {
    Locator getComponent();
}