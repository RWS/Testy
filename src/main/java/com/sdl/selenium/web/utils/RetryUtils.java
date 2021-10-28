package com.sdl.selenium.web.utils;

import com.google.common.base.Strings;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class RetryUtils {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RetryUtils.class);

    public static <V> V retry(int maxRetries, Callable<V> call) {
        return retry(maxRetries, null, call, false);
    }

    /**
     * @param duration example 20 seconds
     * @param call     button.click()
     * @param <V>      type of method
     * @return true or false, throw RuntimeException()
     * <pre>{@code
     * RetryUtils.retry(Duration.ofSeconds(10), ()-> button.click());
     * }</pre>
     */
    public static <V> V retry(Duration duration, Callable<V> call) {
        return retry(duration, null, call, false);
    }

    public static <V> V retry(int maxRetries, String prefixLog, Callable<V> call) {
        return retry(maxRetries, prefixLog, call, false);
    }

    /**
     * @param duration  example 20 seconds
     * @param prefixLog class name
     * @param call      button.click()
     * @param <V>       type of method
     * @return true or false, throw RuntimeException()
     * <pre>{@code
     * RetryUtils.retry(Duration.ofSeconds(10), "LoginButton", ()-> button.click());
     * }</pre>
     */
    public static <V> V retry(Duration duration, String prefixLog, Callable<V> call) {
        return retry(duration, prefixLog, call, false);
    }

    public static boolean retryRunnable(int maxRetries, Runnable r) {
        return retryRunnable(maxRetries, null, r, false);
    }

    public static boolean retryRunnable(int maxRetries, String prefixLog, Runnable r) {
        return retryRunnable(maxRetries, prefixLog, r, false);
    }

    public static boolean retryRunnableSafe(int maxRetries, Runnable r) {
        return retryRunnable(maxRetries, null, r, true);
    }

    public static boolean retryRunnableSafe(int maxRetries, String prefixLog, Runnable r) {
        return retryRunnable(maxRetries, prefixLog, r, true);
    }

    private static boolean retryRunnable(int maxRetries, String prefixLog, Runnable r, boolean safe) {
        int count = 0;
        int wait = 0;
        Fib fib = new Fib();
        long startMs = System.currentTimeMillis();
        do {
            count++;
//            wait = wait == 0 ? 5 : count < 9 ? wait * 2 : wait;
            wait = count < 9 ? fibonacci(wait, fib).getResult() : wait;
            Utils.sleep(wait);
            try {
                r.run();
                return true;
            } catch (Exception | AssertionError e) {
                if (safe) {
                    return false;
                } else {
                    if (count >= maxRetries) {
                        long duringMs = getDuringMillis(startMs);
                        log.error((Strings.isNullOrEmpty(prefixLog) ? "" : prefixLog + ":") + "Retry {} and wait {} milliseconds ->{}", count, duringMs, e);
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        } while (count < maxRetries);
        if (count > 1) {
            long duringMs = getDuringMillis(startMs);
            log.info((Strings.isNullOrEmpty(prefixLog) ? "" : prefixLog + ":") + "Retry {} and wait {} milliseconds", count, duringMs);
        }
        return true;
    }

    private static <V> V retry(int maxRetries, String prefixLog, Callable<V> call, boolean safe) {
        int count = 0;
        int wait = 0;
        Fib fib = new Fib();
        long startMs = System.currentTimeMillis();
        V execute = null;
        do {
            count++;
//            wait = wait == 0 ? 5 : count < 9 ? wait * 2 : wait;
            wait = count < 9 ? fibonacci(wait, fib).getResult() : wait;
            Utils.sleep(wait);
//            log.info("Retry {} and wait {} ->!!!", count, wait);
            try {
                execute = call.call();
            } catch (Exception | AssertionError e) {
                if (!safe) {
                    if (count >= maxRetries) {
                        long duringMs = getDuringMillis(startMs);
                        log.error((Strings.isNullOrEmpty(prefixLog) ? "" : prefixLog + ":") + "Retry {} and wait {} milliseconds ->{}", count, duringMs, e);
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        } while ((execute == null || isNotExpected(execute)) && count < maxRetries);
        if (count > 1) {
            long duringMs = getDuringMillis(startMs);
            log.info((Strings.isNullOrEmpty(prefixLog) ? "" : prefixLog + ":") + "Retry {} and wait {} milliseconds", count, duringMs);
        }
        return execute;
    }

    public static <V> V retryUntilOneIs(int maxRetries, Callable<V> ...calls) {
        int count = 0;
        int wait = 0;
        Fib fib = new Fib();
        long startMs = System.currentTimeMillis();
        V execute = null;
        boolean notExpected = true;
        do {
            count++;
            wait = count < 9 ? fibonacci(wait, fib).getResult() : wait;
            Utils.sleep(wait);
            try {
                for (Callable<V> call : calls) {
                    execute = call.call();
                    notExpected = isNotExpected(execute);
                    if (!notExpected) {
                        break;
                    }
                }
            } catch (Exception | AssertionError e) {
                if (count >= maxRetries) {
                    long duringMs = getDuringMillis(startMs);
                    log.error("Retry {} and wait {} milliseconds ->{}", count, duringMs, e);
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        } while ((execute == null || notExpected) && count < maxRetries);
        if (count > 1) {
            long duringMs = getDuringMillis(startMs);
            log.info("Retry {} and wait {} milliseconds", count, duringMs);
        }
        return execute;
    }

    private static <V> V retry(Duration duration, String prefixLog, Callable<V> call, boolean safe) {
        int count = 0;
        int wait = 0;
        int limit = (int) duration.getSeconds() / 5;
        Fib fib = new Fib(limit);
        long startMillis = System.currentTimeMillis();
        V execute = null;
        do {
            count++;
            wait = fibonacciSinusoidal(wait, fib).getResult();
            Utils.sleep(wait * 1000);
            try {
                execute = call.call();
            } catch (Exception | AssertionError e) {
                if (!safe) {
                    if (timeIsOver(startMillis, duration)) {
                        long duringMillis = getDuringMillis(startMillis);
                        log.error((Strings.isNullOrEmpty(prefixLog) ? "" : prefixLog + ":") + "Retry {} and wait {} milliseconds ->{}", count, duringMillis, e);
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        } while ((execute == null || isNotExpected(execute)) && !timeIsOver(startMillis, duration));
        if (count > 1) {
            long duringMillis = getDuringMillis(startMillis);
            log.info((Strings.isNullOrEmpty(prefixLog) ? "" : prefixLog + ":") + "Retry {} and wait {} milliseconds", count, duringMillis);
        }
        return execute;
    }

    private static boolean timeIsOver(long startMillis, Duration duration) {
        long duringMillis = getDuringMillis(startMillis);
        long toMillis = duration.toMillis();
        return toMillis <= duringMillis;
    }

    private static long getDuringMillis(long startMillis) {
        long endMillis = System.currentTimeMillis();
        return endMillis - startMillis;
    }

    @Deprecated
    public static <V> V retryWithSuccess(int maxRetries, Callable<V> call) {
        return retry(maxRetries, call);
    }

    public static <V> V retrySafe(int maxRetries, Callable<V> call) {
        return retry(maxRetries, null, call, true);
    }

    public static <V> V retrySafe(Duration duration, Callable<V> call) {
        return retry(duration, null, call, true);
    }

    public static <V> V retrySafe(int maxRetries, String prefixLog, Callable<V> call) {
        return retry(maxRetries, prefixLog, call, true);
    }

    public static <V> V retrySafe(Duration duration, String prefixLog, Callable<V> call) {
        return retry(duration, prefixLog, call, true);
    }

    private static <V> boolean isNotExpected(V execute) {
        if (execute instanceof Boolean) {
            return !(Boolean) execute;
        } else if (execute instanceof String) {
            return Strings.isNullOrEmpty((String) execute);
        } else if (execute instanceof List) {
            List<?> list = (List<?>) execute;
            return list.isEmpty() || list.stream().allMatch(Objects::isNull);
        }
        return execute == null;
    }

    /**
     * @param duration Duration.ofSeconds(2)
     * @param expected accept only: <pre>{@code Integer, String, Boolean, List<String> and List<List<String>> }</pre>
     * @param call     getCount();
     * @param <V>      expected Type
     * @return expected value
     */
    public static <V> V retryIfNotSame(Duration duration, V expected, Callable<V> call) {
        V result = retry(duration, () -> doRetryIfNotSame(expected, call));
        return result == null ? retry(0, call) : result;
    }

    private static <V> V doRetryIfNotSame(V expected, Callable<V> call) throws Exception {
        V text = call.call();
        if (text instanceof Integer && expected instanceof Integer) {
            return expected == text ? text : null;
        } else if (text instanceof List && expected instanceof List) {
            List<?> currentList = (List<?>) text;
            List<?> expectedList = (List<?>) expected;
            if (currentList.get(0) instanceof List && expectedList.get(0) instanceof List) {
                List<List<?>> currentListOfList = (List<List<?>>) text;
                List<List<?>> expectedListOfList = (List<List<?>>) expected;
                Boolean compare = null;
                int expectedSize = expectedListOfList.size();
                for (List<?> expectedTmp : expectedListOfList) {
                    int currentFind = 0;
                    Boolean match = null;
                    for (List<?> currentTmp : currentListOfList) {
                        boolean matchTmp = expectedTmp.containsAll(currentTmp);
                        if (matchTmp) {
                            currentFind++;
                            match = true;
                        }
                        if (expectedSize == currentFind) {
                            break;
                        }
                    }
                    compare = compare == null ? Boolean.TRUE.equals(match) : compare && Boolean.TRUE.equals(match);
                }
                return compare ? text : null;
            } else if (currentList.get(0) instanceof String && expectedList.get(0) instanceof String) {
                boolean allMatch = expectedList.stream().allMatch(currentList::contains);
                return allMatch ? text : null;
            } else {
                throw new UnsupportedOperationException("Cannot compare List of object with another List of object!");
            }
        } else if (text instanceof String && expected instanceof String) {
            return expected.equals(text) ? text : null;
        } else if (text instanceof Boolean && expected instanceof Boolean) {
            Boolean bolActual = (Boolean) text;
            Boolean bolExpected = (Boolean) expected;
            return bolExpected.equals(bolActual) ? text : null;
        } else {
            log.error("Expected and actual objects aren't the some type!");
            return null;
        }
    }

    /**
     * @param maxRetries e.g 3
     * @param expected   accept only: <pre>{@code Integer, String, Boolean, List<String> and List<List<String>> }</pre>
     * @param call       getCount();
     * @param <V>        expected Type
     * @return expected value
     */
    public static <V> V retryIfNotSame(int maxRetries, V expected, Callable<V> call) {
        V result = retry(maxRetries, () -> doRetryIfNotSame(expected, call));
        return result == null ? retry(0, call) : result;
    }

    public static <V> V retryIfNotContains(Duration duration, String expected, Callable<V> call) {
        V result = retry(duration, () -> {
            V text = call.call();
            return text instanceof String && ((String) text).contains(expected) ? text : null;
        });
        return result == null ? retry(0, call) : result;
    }

    public static <V> V retryIfNotContains(int maxRetries, String expected, Callable<V> call) {
        V result = retry(maxRetries, () -> {
            V text = call.call();
            return text instanceof String && ((String) text).contains(expected) ? text : null;
        });
        return result == null ? retry(0, call) : result;
    }

    private static Fib fibonacci(int time, Fib fib) {
        int sum = time + fib.getLast();
        fib.setLast(fib.getStart());
        fib.setStart(sum);
        fib.setResult(sum);
//        log.info((Strings.isNullOrEmpty(prefixLog) ? "" : prefixLog + ":") + "time is {}", sum);
        return fib;
    }

    private static Fib fibonacciSinusoidal(int time, Fib fib) {
        int sum = 0;
        if (fib.isPositive() && time >= fib.getLimit()) {
            sum = fib.getLast();
            fib.setStart(fib.getStart() - fib.getLast());
            fib.setLast(sum);
            fib.setPositive(false);
        } else if (!fib.isPositive() && time < fib.getLimit()) {
            sum = fib.getStart();
            fib.setStart(fib.getLast() - fib.getStart());
            fib.setLast(sum);
        } else if (fib.isPositive() && time >= 0) {
            sum = time + fib.getLast();
            fib.setLast(fib.getStart());
            fib.setStart(sum);
        } else {
            log.info("This value is not covered!");
            Utils.sleep(1);
        }
        if (sum <= 0) {
            fib.setPositive(true);
        }
        fib.setResult(sum);
//        log.info("result is: {}", sum);
        return fib;
    }

    public static void main(String[] args) {
        int t = 0;
        for (int i = 0; i < 10; i++) {
            t = RetryUtils.fibonacci(t, new Fib()).getResult();
//            log.info("time is {}", t);
            Utils.sleep(t);
        }
    }

    private static class Fib {
        private int start = 0;
        private int last = 1;
        private int result;
        private int limit = 60;
        private boolean positive = true;

        public Fib() {
        }

        public Fib(int limit) {
            this.limit = limit;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getStart() {
            return start;
        }

        public void setLast(int last) {
            this.last = last;
        }

        public int getLast() {
            return last;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public int getResult() {
            return result;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public boolean isPositive() {
            return positive;
        }

        public void setPositive(boolean positive) {
            this.positive = positive;
        }

        @Override
        public String toString() {
            return "Fib{" +
                    "start=" + start +
                    ", last=" + last +
                    ", result=" + result +
                    ", limit=" + limit +
                    ", positive=" + positive +
                    '}';
        }
    }
}