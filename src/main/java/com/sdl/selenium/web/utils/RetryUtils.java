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
        int count = 0;
        long wait = 0;
        V execute = null;
        do {
            count++;
            wait = wait == 0 ? 10 : wait * 2;
            Utils.sleep(wait);
            try {
                execute = t.call();
            } catch (Exception | AssertionError e) {
                if (count >= maxRetries) {
                    LOGGER.error("{}", e);
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        } while ((execute == null || isNotExpected(execute)) && count >= maxRetries);
        LOGGER.info("Retry {} and wait {} milliseconds", count, wait);
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