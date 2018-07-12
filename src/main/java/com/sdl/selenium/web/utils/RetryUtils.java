package com.sdl.selenium.web.utils;

import com.google.common.base.Strings;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

public class RetryUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RetryUtils.class);

    @FunctionalInterface
    public interface RunnableWithException {
        void run() throws AssertionError;
    }

    @FunctionalInterface
    public interface WaitIfIsNullOrEmpty {
        String run() throws AssertionError;
    }

    @Deprecated
    public static boolean retry(int maxRetries, RunnableWithException t) {
        int count = 0;
        while (count < maxRetries) {
            try {
                t.run();
                return true;
            } catch (WebDriverException | AssertionError e) {
                LOGGER.info("Retry {} for '{}'", count, e.getClass());
                if (++count >= maxRetries)
                    throw e;
            }
        }
        return false;
    }

    public static <V> V retryWithSuccess(int maxRetries, Callable<V> t) {
        int count = 1;
        long wait = 10;
        V execute = null;
        do {
            Utils.sleep(wait);
            try {
                try {
                    execute = t.call();
                } catch (NullPointerException e) {
                    LOGGER.warn("Null: {}", e.getMessage());
                    execute = t.call();
                }
            } catch (Throwable e) {
                if (count >= maxRetries)
                    throw new RuntimeException(e.getMessage(), e);
                LOGGER.warn("Run: {}, {}", e.getMessage(), e);
            }
            if (execute == null) {
                LOGGER.info("Retry {} and wait {} milliseconds", count, wait);
            }
            count++;
            wait = wait * 2;
        } while ((execute == null || isNotExpected(execute)) && count >= maxRetries);
        return execute;
    }

    private static <V> boolean isNotExpected(V execute) {
        if (execute instanceof Boolean) {
            return !(Boolean) execute;
        } else if (execute instanceof String) {
            return Strings.isNullOrEmpty((String) execute);
        } else if (execute instanceof List) {
            return ((List) execute).isEmpty();
        }
        return execute == null;
    }

    @Deprecated
    public static String waitIfIsNullOrEmpty(int maxRetries, WaitIfIsNullOrEmpty t) {
        int count = 0;
        String text;
        do {
            text = t.run();
            count++;
        } while (Strings.isNullOrEmpty(text) && count < maxRetries);
        return text;
    }
}