package com.extjs.selenium.conditions;

import com.extjs.selenium.window.MessageBox;
import com.sdl.selenium.conditions.Condition;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.MessageBoxCondition;
import org.apache.log4j.Logger;

/**
 * Example how to add conditions to ConditionManager:
 * <pre>
ConditionManager manager = new ConditionManager().add(new SuccessCondition() {
    public boolean execute() {
        return logoutButton.isElementPresent();
    }
});
 * </pre>
 * OR more specific cases:
 * <pre>
 ConditionManager manager = new ConditionManager();
 manager.add(
     new MessageBoxFailCondition("Wrong email or password.")).add(
     new RenderSuccessCondition(logoutButton)
 );
 Condition condition = manager.execute();
 logged = condition.isSuccess();
 </pre>
 */
public class ExtjsConditionManager extends ConditionManager {

    private static final Logger logger = Logger.getLogger(ExtjsConditionManager.class);

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
     * @param messages
     * @return
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
     * @param messages
     * @return
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
