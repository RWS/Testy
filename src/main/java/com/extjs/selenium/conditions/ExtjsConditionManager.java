package com.extjs.selenium.conditions;

import com.extjs.selenium.window.MessageBox;
import com.sdl.selenium.conditions.Condition;
import com.sdl.selenium.conditions.MessageBoxCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class ExtjsConditionManager extends com.sdl.selenium.extjs3.conditions.ExtjsConditionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtjsConditionManager.class);

    /**
     * default timeout in milliseconds is 10000.
     */
    public ExtjsConditionManager() {

    }

    /**
     * @param timeout milliseconds
     */
    public ExtjsConditionManager(long timeout) {
        this();
        setTimeout(timeout);
    }

    @Override
    protected Condition findCondition(){
        String boxMessage = null;
        for (Condition condition : getConditionList()) {
            if(condition instanceof MessageBoxCondition){
                if(boxMessage == null){
                    boxMessage = MessageBox.getMessage();
                }
                if (((MessageBoxCondition)condition).execute(boxMessage)) {
                    return condition;
                }
            } else {
                if (condition.execute()) {
                    return condition;
                }
            }
            //logger.debug(condition + " is false");
        }
        return null;
    }

    /**
     * add each message to MessageBoxFailCondition
     * @param messages messages
     * @return this
     */
    public ExtjsConditionManager addFailConditions(String... messages){
        for (String message : messages){
            if(message != null && message.length() > 0){
                this.add(new MessageBoxFailCondition(message));
            }
        }
        return this;
    }

    /**
     * add each message to MessageBoxSuccessCondition
     * @param messages messages
     * @return this
     */
    public ExtjsConditionManager addSuccessConditions(String... messages){
        for (String message : messages){
            if(message != null && message.length() > 0){
                this.add(new MessageBoxSuccessCondition(message));
            }
        }
        return this;
    }
}
