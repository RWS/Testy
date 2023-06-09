package com.sdl.selenium.utils;

import com.google.common.base.Strings;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.containsInAnyOrder;

public class MatcherAssertList {
    private static final AssertUtil assertUtil = new AssertUtil();

    public static <E, T extends List<E>> void assertThatList(T actual, Matcher<? super T> matcher) {
        assertThatList("", true, assertUtil.getFormat(), actual, matcher);
    }

    public static <E, T extends List<E>> void assertThatList(String reason, T actual, Matcher<? super T> matcher) {
        assertThatList(reason, true, assertUtil.getFormat(), actual, matcher);
    }

    public static <E, T extends List<E>> void assertThatList(String reason, boolean transformDate, T actual, Matcher<? super T> matcher) {
        assertThatList(reason, transformDate, assertUtil.getFormat(), actual, matcher);
    }

    public static <E, T extends List<E>> void assertThatList(String reason, Function<String, String> format, T actual, Matcher<? super T> matcher) {
        assertThatList(reason, true, format, actual, matcher);
    }

    public static <E, T extends List<E>> void assertThatList(String reason, boolean transformDate, Function<String, String> format, T actual, Matcher<? super T> matcher) {
        if (!matcher.matches(actual)) {
            Description description = new StringDescription();
            String log = assertUtil.showObjectValues(actual, transformDate, format);
            description.appendText(reason + log)
                    .appendText("\nExpected: ")
                    .appendDescriptionOf(matcher)
                    .appendText("\n     but: ");
            matcher.describeMismatch(actual, description);

            throw new AssertionError(description.toString());
        }
    }

    public static <T> void assertThatObject(T actual, Matcher<? super T> matcher) {
        assertThatObject("", actual, matcher);
    }

    public static <T> void assertThatObject(String reason, T actual, Matcher<? super T> matcher) {
        assertThatObject(reason, true, actual, matcher);
    }

    public static <T> void assertThatObject(String reason, boolean transformDate, T actual, Matcher<? super T> matcher) {
        if (!matcher.matches(actual)) {
            Description description = new StringDescription();
            String log = assertUtil.showObjects(actual, transformDate);
            description.appendText(reason + log)
                    .appendText("\nExpected: ")
                    .appendDescriptionOf(matcher)
                    .appendText("\n     but: ");
            matcher.describeMismatch(actual, description);

            throw new AssertionError(description.toString());
        }
    }

    public static void assertThatList(String reason, boolean assertion) {
        if (!assertion) {
            throw new AssertionError(reason);
        }
    }

    public static void main(String[] args) {
//        TestStorage.set("eng", "eng.txt");
        List<List<String>> actual = new ArrayList<>();
        List<String> lp = List.of("New field Note was created with values Printer edit note for term 04 Oct 2022 | SYSTEM");
        actual.add(lp);
//        actual.add("test2");
        List<List<String>> expected = new ArrayList<>();
        List<String> lpc = List.of("New field Note was created with values Printer edit note for term 04 Oct 2022 | SYSTEM");
        expected.add(lpc);
//        expected.add("test2");
        assertThatList("", getFormat(), actual, containsInAnyOrder(expected.toArray()));
    }

    static Function<String, String> getFormat() {
        return date -> {
            if (date != null) {
                if (!Strings.isNullOrEmpty(date)) {
                    Pattern pattern = Pattern.compile("([a-zA-Z\\s\\d]+(\\d{2,4}[-\\sa-zA-Z0-9]{4,6}\\d{2,4}|(Today))[a-zA-Z0-9:|\\s]+)");
                    // Accept format 'dd MMM yyyy', 'yyyy MMM dd', 'dd MM yyyy', 'dd-MM-yyyy'
                    java.util.regex.Matcher matcher = pattern.matcher(date);
                    if (matcher.find()) {
                        LocalDate now = LocalDate.now();
                        String allValue = matcher.group(1);
                        String group = matcher.group(2);
                        if (group.contains("Today")) {
                            return allValue.replace(group, "today dd MMM yyyy");
                        }
                        String tmpValue = "";
                        try {
                            LocalDate newDate = LocalDate.parse(group, DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH));
                            if (now.minusDays(7L).isEqual(newDate)) {
                                tmpValue = "1WeekAgo dd MMM yyyy";
                            } else if (now.minusDays(3L).isEqual(newDate)) {
                                tmpValue = "3DaysAgo dd MMM yyyy";
                            } else if (now.minusDays(2L).isEqual(newDate)) {
                                tmpValue = "2DaysAgo dd MMM yyyy";
                            } else if (now.minusDays(1L).isEqual(newDate)) {
                                tmpValue = "yesterday dd MMM yyyy";
                            } else if (now.isEqual(newDate)) {
                                tmpValue = "today dd MMM yyyy";
                            } else if (now.plusDays(1L).isEqual(newDate)) {
                                tmpValue = "tomorrow dd MMM yyyy";
                            } else if (now.plusDays(2L).isEqual(newDate)) {
                                tmpValue = "in2Days dd MMM yyyy";
                            } else if (now.plusDays(3L).isEqual(newDate)) {
                                tmpValue = "in3Days dd MMM yyyy";
                            } else if (now.plusDays(4L).isEqual(newDate)) {
                                tmpValue = "in4Days dd MMM yyyy";
                            } else if (now.plusDays(5L).isEqual(newDate)) {
                                tmpValue = "in5Days dd MMM yyyy";
                            } else if (now.plusDays(6L).isEqual(newDate)) {
                                tmpValue = "in6Days dd MMM yyyy";
                            } else if (now.plusDays(7L).isEqual(newDate)) {
                                tmpValue = "nextWeek dd MMM yyyy";
                            } else if (now.plusDays(8L).isEqual(newDate)) {
                                tmpValue = "nextWeekAnd1Day dd MMM yyyy";
                            } else if (now.plusDays(9L).isEqual(newDate)) {
                                tmpValue = "nextWeekAnd2Days dd MMM yyyy";
                            } else if (now.plusDays(10L).isEqual(newDate)) {
                                tmpValue = "nextWeekAnd3Days dd MMM yyyy";
                            } else if (now.plusDays(11L).isEqual(newDate)) {
                                tmpValue = "nextWeekAnd4Days dd MMM yyyy";
                            } else if (now.plusDays(12L).isEqual(newDate)) {
                                tmpValue = "nextWeekAnd5Days dd MMM yyyy";
                            } else if (now.plusDays(13L).isEqual(newDate)) {
                                tmpValue = "nextWeekAnd6Days dd MMM yyyy";
                            } else if (now.plusDays(14L).isEqual(newDate)) {
                                tmpValue = "next2Weeks dd MMM yyyy";
                            } else if (now.plusDays(21L).isEqual(newDate)) {
                                tmpValue = "next3Weeks dd MMM yyyy";
                            } else if (now.plusDays(28L).isEqual(newDate)) {
                                tmpValue = "next4Weeks dd MMM yyyy";
                            } else if (now.plusMonths(1L).minusDays(1L).isEqual(newDate)) {
                                tmpValue = "nextMonth1DayAgo dd MMM yyyy";
                            } else if (now.plusMonths(1L).minusDays(2L).isEqual(newDate)) {
                                tmpValue = "nextMonth2DaysAgo dd MMM yyyy";
                            } else if (now.plusMonths(1L).minusDays(3L).isEqual(newDate)) {
                                tmpValue = "nextMonth3DaysAgo dd MMM yyyy";
                            } else if (now.plusMonths(1L).plusDays(1L).isEqual(newDate)) {
                                tmpValue = "nextMonthAnd1Day dd MMM yyyy";
                            } else if (now.plusMonths(1L).plusDays(2L).isEqual(newDate)) {
                                tmpValue = "nextMonthAnd2Days dd MMM yyyy";
                            } else if (now.plusMonths(1L).isEqual(newDate)) {
                                tmpValue = "nextMonth dd MMM yyyy";
                            } else if (now.plusMonths(6L).minusDays(1L).isEqual(newDate)) {
                                tmpValue = "next6Months1DayAgo dd MMM yyyy";
                            } else if (now.plusMonths(6L).minusDays(2L).isEqual(newDate)) {
                                tmpValue = "next6Months2DaysAgo dd MMM yyyy";
                            } else if (now.plusYears(1L).minusDays(1L).isEqual(newDate)) {
                                tmpValue = "nextYear1DayAgo dd MMM yyyy";
                            } else if (now.plusYears(1L).isEqual(newDate)) {
                                tmpValue = "nextYear dd MMM yyyy";
                            }
                            return allValue.replace(group, tmpValue);
                        } catch (DateTimeParseException e) {
                            String format = assertUtil.getKeyFromStorage(date);
                            return format;
                        }
                    } else {
                        String format = assertUtil.getKeyFromStorage(date);
                        return format;
                    }
                }
            }
            return date;
        };
    }
}