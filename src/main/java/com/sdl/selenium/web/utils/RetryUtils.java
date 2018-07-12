package com.sdl.selenium.web.utils;

import com.google.common.base.Strings;
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

    public static <V> V retry(int maxRetries, Callable<V> t) {
        return retry(maxRetries, t, false);
    }

    private static <V> V retry(int maxRetries, Callable<V> t, boolean safe) {
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
                if (!safe) {
                    if (count >= maxRetries) {
                        LOGGER.error("{}", e);
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        } while ((execute == null || isNotExpected(execute)) && count <= maxRetries);
        if (count > 1) {
            LOGGER.info("Retry {} and wait {} milliseconds", count, wait);
        }
        return execute;
    }

    @Deprecated
    public static <V> V retryWithSuccess(int maxRetries, Callable<V> t) {
        return retry(maxRetries, t);
    }

    public static <V> V retrySafe(int maxRetries, Callable<V> t) {
        return retry(maxRetries, t, true);
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