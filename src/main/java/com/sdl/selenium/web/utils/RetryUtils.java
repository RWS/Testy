package com.sdl.selenium.web.utils;

public class RetryUtils {
//    private static final Logger LOGGER = LoggerFactory.getLogger(RetryUtils.class);

//    private static int RETRY = 3;
//    private static final long DELAY = 1000L;

    @FunctionalInterface
    public interface RunnableWithException {
        void run() throws AssertionError;
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
            } catch (AssertionError e) {
                if (++count >= maxRetries)
                    return false;
            }
        }
        return false;
    }
}