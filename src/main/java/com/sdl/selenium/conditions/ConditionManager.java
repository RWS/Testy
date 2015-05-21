package com.sdl.selenium.conditions;

import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
public class ConditionManager  {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionManager.class);

    public static int SLEEP_INTERVAL = 50;

    private long timeout = 10000;

    private long startTime;

    private List<Condition> conditionList = new ArrayList<Condition>();

    /**
     * default timeout in milliseconds is 10000.
     */
    public ConditionManager() {
        this.add(new FailCondition("@TimeoutCondition@") {
            public boolean execute() {
                return new Date().getTime() - startTime > timeout;
            }
            @Override
            public boolean isTimeout(){
                return true;
            }
            public String toString() {
                return "TimeoutCondition@" + timeout;
            }
        });
    }

    /**
     * @param timeout milliseconds
     */
    public ConditionManager(long timeout) {
        this();
        this.timeout = timeout;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public ConditionManager add(Condition condition) {
        //LOGGER.debug("ConditionManager add condition : " + condition);
        conditionList.add(condition);
        return this;
    }

    public ConditionManager remove(Condition condition) {
        //LOGGER.debug("ConditionManager remove condition : " + condition);
        conditionList.remove(condition);
        return this;
    }

    public ConditionManager removeConditions(String... messages){
        for (String message : messages){
            removeCondition(message);
        }
        return this;
    }

    public ConditionManager removeCondition(String message){
        if(message != null && message.length() > 0){
            for (Condition condition : conditionList) {
                if(condition.equals(message)){
                    conditionList.remove(condition);
                    break;
                }
            }
        }
        return this;
    }

    public long getTimeout() {
        return timeout;
    }

    /**
     *
     * @param timeout milliseconds
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * <p>Iterates through conditions list until one of the conditions is met or the method times out</p>
     * <p>in case of timeout a FailCondition("@TimeoutCondition@") will be returned</p>
     * @return the executed Condition, unless the method times out
     */
    public Condition execute() {
        startTime = new Date().getTime();
        Collections.sort(conditionList);
        while (true) {
            Condition condition = findCondition();
            if(condition != null){
                LOGGER.debug(condition + " - executed");
                return condition;
            }
            Utils.sleep(SLEEP_INTERVAL);
        }
    }

    protected Condition findCondition(){
        for (Condition condition : getConditionList()) {
            if (condition.execute()) {
                return condition;
            }
            //LOGGER.debug(condition + " is false");
        }
        return null;
    }
}
