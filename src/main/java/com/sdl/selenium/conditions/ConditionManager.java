package com.sdl.selenium.conditions;

import com.google.common.base.Strings;
import com.sdl.selenium.web.utils.Utils;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Example how to add conditions to ConditionManager:
 * <pre>
 * ConditionManager manager = new ConditionManager().add(new SuccessCondition() {
 * public boolean execute() {
 * return logoutButton.isElementPresent();
 * }
 * });
 * </pre>
 * OR more specific cases:
 * <pre>
 * ConditionManager manager = new ConditionManager();
 * manager.add(
 * new MessageBoxFailCondition("Wrong email or password.")).add(
 * new RenderSuccessCondition(logoutButton)
 * );
 * Condition condition = manager.execute();
 * logged = condition.isSuccess();
 * </pre>
 */
@Slf4j
public class ConditionManager {
    public static int SLEEP_INTERVAL = 20;

    private long timeout = 10000;
    private Duration duration = Duration.ofSeconds(10);

    private long startTime;

    private List<Condition> conditionList = new ArrayList<>();

    /**
     * default duration is in 10 seconds.
     */
    public ConditionManager() {
        this.add(new FailCondition("TimeoutCondition@") {
            public boolean execute() {
                Duration duration = getDuration().minusMillis(new Date().getTime() - startTime);
                return duration.isZero() || duration.isNegative();
            }

            @Override
            public boolean isTimeout() {
                return true;
            }

            public String toString() {
                return getMessage() + duration.toMillis() + " ms";
            }
        });
    }

    /**
     * @param timeout milliseconds
     * @deprecated use {@link #ConditionManager(Duration)}
     */
    @Deprecated
    public ConditionManager(long timeout) {
        this();
        this.duration = Duration.ofMillis(timeout);
    }

    public ConditionManager(Duration duration) {
        this();
        this.duration = duration;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public ConditionManager add(Condition condition) {
        conditionList.add(condition);
        return this;
    }

    public ConditionManager remove(Condition condition) {
        conditionList.removeIf(i -> i.getMessage().equals(condition.getMessage()));
        return this;
    }

    public ConditionManager removeConditions(String... messages) {
        for (String message : messages) {
            removeCondition(message);
        }
        return this;
    }

    public ConditionManager removeCondition(String message) {
        if (!Strings.isNullOrEmpty(message)) {
            conditionList.removeIf(c -> c.getMessage().equals(message));
        }
        return this;
    }

    public Duration getDuration() {
        return duration;
    }

    /**
     * @param timeout milliseconds
     */
    public void setTimeout(long timeout) {
        this.duration = Duration.ofMillis(timeout);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     * <p>Iterates through conditions list until one of the conditions is met or the method times out</p>
     * <p>in case of timeout a FailCondition("@TimeoutCondition@") will be returned</p>
     *
     * @return the executed Condition, unless the method times out
     */
    public Condition execute() {
        startTime = new Date().getTime();
        Collections.sort(conditionList);
        while (true) {
            Condition condition = findCondition();
            if (condition != null) {
                log.debug("{} - executed in ({}ms)", condition, new Date().getTime() - startTime);
                return condition;
            }
            Utils.sleep(SLEEP_INTERVAL);
        }
    }

    protected Condition findCondition() {
        for (Condition condition : getConditionList()) {
            if (condition.execute()) {
                return condition;
            }
        }
        return null;
    }
}