package com.sdl.selenium.web.utils;

import com.google.common.base.Strings;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.Callable;

public class RetryUtils {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RetryUtils.class);

    public static <V> V retry(int maxRetries, Callable<V> t) {
        return retry(maxRetries, t, false);
    }

    public static boolean retryRunnable(int maxRetries, Runnable r) {
        return retryRunnable(maxRetries, r, false);
    }

    public static boolean retryRunnableSafe(int maxRetries, Runnable r) {
        return retryRunnable(maxRetries, r, true);
    }

    private static boolean retryRunnable(int maxRetries, Runnable r, boolean safe) {
        int count = 0;
        long wait = 0;
        long startMs = System.currentTimeMillis();
        do {
            count++;
            wait = wait == 0 ? 5 : count < 9 ? wait * 2 : wait;
            Utils.sleep(wait);
            try {
                r.run();
                return true;
            } catch (Exception | AssertionError e) {
                if (safe) {
                    return false;
                } else {
                    if (count >= maxRetries) {
                        long endMs = System.currentTimeMillis();
                        long duringMs = endMs - startMs;
                        log.error("Retry {} and wait {} milliseconds ->{}", count, duringMs, e);
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        } while (count < maxRetries);
        if (count > 1) {
            long endMs = System.currentTimeMillis();
            long duringMs = endMs - startMs;
            log.info("Retry {} and wait {} milliseconds", count, duringMs);
        }
        return true;
    }

    private static <V> V retry(int maxRetries, Callable<V> t, boolean safe) {
        int count = 0;
        long wait = 0;
        long startMs = System.currentTimeMillis();
        V execute = null;
        do {
            count++;
            wait = wait == 0 ? 5 : count < 9 ? wait * 2 : wait;
            Utils.sleep(wait);
//            log.info("Retry {} and wait {} ->!!!", count, wait);
            try {
                execute = t.call();
            } catch (Exception | AssertionError e) {
                if (!safe) {
                    if (count >= maxRetries) {
                        long endMs = System.currentTimeMillis();
                        long duringMs = endMs - startMs;
                        log.error("Retry {} and wait {} milliseconds ->{}", count, duringMs, e);
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        } while ((execute == null || isNotExpected(execute)) && count < maxRetries);
        if (count > 1) {
            long endMs = System.currentTimeMillis();
            long duringMs = endMs - startMs;
            log.info("Retry {} and wait {} milliseconds", count, duringMs);
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

    public static <V> V retryIfNotSame(int maxRetries, V expected, Callable<V> t) {
        V result = retry(maxRetries, () -> {
            V text = t.call();
            if (text instanceof Integer) {
                return expected == text ? text : null;
            } else {
                return expected.equals(text) ? text : null;
            }
        });
        return result == null ? retry(0, t) : result;
    }

    public static <V> V retryIfNotContains(int maxRetries, String expected, Callable<V> t) {
        V result = retry(maxRetries, () -> {
            V text = t.call();
            return text instanceof String && ((String) text).contains(expected) ? text : null;
        });
        return result == null ? retry(0, t) : result;
    }
}