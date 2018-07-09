package com.sdl.selenium.web.utils;

import com.google.common.base.Strings;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetryUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RetryUtils.class);

//    private static int RETRY = 3;
//    private static final long DELAY = 1000L;

    @FunctionalInterface
    public interface RunnableWithException {
        void run() throws AssertionError;
    }

    @FunctionalInterface
    public interface WaitIfIsNullOrEmpty {
        String run() throws AssertionError;
    }

    @FunctionalInterface
    public interface RunnableWithSuccess {
        boolean run() throws AssertionError;
    }

//    public static <V> V retry(int maxRetries, Callable<V> callable, Throwable throwable) {
//        RETRY = maxRetries;
//        return retryLogics(callable, throwable);
//    }
//
//    public static boolean retry(RunnableWithException runnable, Throwable throwable) {
//        return retryLogics(() -> {
//            runnable.run();
//            return false;
//        }, throwable);
//    }
//
//    private static <T> T retryLogics(Callable<T> callable, Throwable throwable) {
//        int counter = 0;
//
//        while (counter < RETRY) {
//            try {
//                return callable.call();
//            } catch (Throwable e) {
//                counter++;
//                LOGGER.error("retry {}: {}", counter, RETRY, e);
//
//                try {
//                    Thread.sleep(DELAY);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//
//        throw new RuntimeException(throwable);
//    }

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

    public static boolean retryWithSuccess(int maxRetries, RunnableWithSuccess t) {
        int count = 0;
        boolean isSuccess = false;
        do {
            try {
                isSuccess = t.run();
            } catch (WebDriverException | AssertionError e) {
                if (++count >= maxRetries)
                    throw e;
            }
            LOGGER.info("Retry {}", count - 1);
        } while (!isSuccess && count < maxRetries);
        return isSuccess;
    }

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