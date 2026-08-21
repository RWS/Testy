package com.sdl.unit;

import com.sdl.selenium.utils.AssertUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AssertUtilTest {
    private final AssertUtil assertUtil = new AssertUtil();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

    @Test
    public void formatShouldTranslateRelativeDates() {
        LocalDate today = LocalDate.now();
        Object[][] cases = new Object[][]{
                {"Today", "today dd MMM yyyy"},
                {"Modified - " + today.format(FORMATTER) + " by Admin User", "Modified - today dd MMM yyyy by Admin User"},
                {"CREATED - 21 AUG 2026 by ADMIN USER", "CREATED - today dd MMM yyyy by ADMIN USER"},
                {today.format(FORMATTER), "today dd MMM yyyy"},
                {today.minusDays(1).format(FORMATTER), "yesterday dd MMM yyyy"},
                {today.minusDays(2).format(FORMATTER), "2DaysAgo dd MMM yyyy"},
                {today.plusDays(1).format(FORMATTER), "tomorrow dd MMM yyyy"},
                {today.plusDays(2).format(FORMATTER), "in2Days dd MMM yyyy"},
                {today.plusDays(7).format(FORMATTER), "nextWeek dd MMM yyyy"},
                {today.plusMonths(1).format(FORMATTER), "nextMonth dd MMM yyyy"},
                {today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 09:15:00",
                        "today yyyy-MM-dd 09:15:00"}
        };

        for (Object[] testCase : cases) {
            String input = (String) testCase[0];
            String expected = (String) testCase[1];
            assertThat(assertUtil.format(input), equalTo(expected));
        }
    }

    @Test
    public void formatShouldLeaveNonDateStringsUnchanged() {
        assertThat(assertUtil.format("not a date"), equalTo("not a date"));
    }

    @Test
    public void formatShouldLeaveNonStringValuesUnchanged() {
        Integer value = 42;
        assertThat(assertUtil.format(value), equalTo(value));
    }
}
