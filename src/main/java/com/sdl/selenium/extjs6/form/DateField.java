package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.extjs6.button.Button;
import com.sdl.selenium.extjs6.slider.Slider;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.link.WebLink;
import com.sdl.selenium.web.utils.RetryUtils;
import com.sdl.selenium.web.utils.Utils;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DateField extends TextField {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DateField.class);
    private final WebLocator trigger = new WebLocator(this).setRoot("/../../").setClasses("x-form-date-trigger");
    private final WebLocator datePicker = new WebLocator().setClasses("x-datepicker", "x-layer").setAttribute("aria-hidden", "false").setVisibility(true);
    private final Button monthYearButton = new Button(datePicker);
    private final Button selectOkButton = new Button(datePicker, "OK", SearchType.TRIM).setVisibility(true).setInfoMessage("OK");
    private final WebLocator yearAndMonth = new WebLocator(datePicker).setClasses("x-monthpicker").setVisibility(true);
    private final WebLocator nextYears = new WebLocator(yearAndMonth).setClasses("x-monthpicker-yearnav-next").setVisibility(true);
    private final WebLocator prevYears = new WebLocator(yearAndMonth).setClasses("x-monthpicker-yearnav-prev").setVisibility(true);
    private final WebLocator yearContainer = new WebLocator(yearAndMonth).setClasses("x-monthpicker-years");
    private final WebLocator monthContainer = new WebLocator(yearAndMonth).setClasses("x-monthpicker-months");
    private final WebLocator dayContainer = new WebLocator(datePicker).setClasses("x-datepicker-active");

    private final WebLocator hourLayer = new WebLocator().setClasses("x-panel", "x-layer").setVisibility(true);
    private final Slider hourSlider = new Slider(hourLayer).setLabel("Hour", SearchType.DEEP_CHILD_NODE_OR_SELF);
    private final Slider minuteSlider = new Slider(hourLayer).setLabel("Min", SearchType.DEEP_CHILD_NODE_OR_SELF);

    private final WebLocator tooltip = new WebLocator().setClasses("x-tip").setAttribute("aria-hidden", "false");

    public DateField() {
        setClassName("DateField");
    }

    public DateField(WebLocator container) {
        this();
        setContainer(container);
    }

    public DateField(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.DEEP_CHILD_NODE_OR_SELF};
        } else {
            List<SearchType> types = new ArrayList<>(Arrays.asList(searchTypes));
            types.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
            searchTypes = types.toArray(new SearchType[0]);
        }
        setLabel(label, searchTypes);
    }

    /**
     * example new DataField().setDate("19", "05", "2013")
     *
     * @param day   String 'dd'
     * @param month String 'MMM'
     * @param year  String 'yyyy'
     * @return true if is selected date, false when DataField doesn't exist
     */
    private boolean setDate(String day, String month, String year) {
        String fullDate = "";
        try {
            fullDate = RetryUtils.retrySafe(5, monthYearButton::getText).trim();
        } catch (NullPointerException e) {
            Utils.sleep(1);
        }
        if (!fullDate.contains(month) || !fullDate.contains(year)) {
            monthYearButton.click();
            if (!yearAndMonth.ready()) {
                monthYearButton.click();
            }
            goToYear(year, fullDate);
            WebLink monthEl = new WebLink(monthContainer).setText(month, SearchType.EQUALS).setInfoMessage("month " + month);
            monthEl.click();
            selectOkButton.click();
        }
        WebLocator dayEl = new WebLocator(dayContainer).setText(day, SearchType.EQUALS).setVisibility(true).setInfoMessage("day " + day);
        Utils.sleep(50);
        return dayEl.click();
    }

    public boolean setHour(int hour, int minute) {
        return hourSlider.move(hour) &&
                minuteSlider.move(minute, 10);
    }

    private void goToYear(String year, String fullDate) {
        int currentYear = Integer.parseInt(fullDate.split(" ")[1]);
        int yearInt = Integer.parseInt(year);
        boolean goNext = yearInt > currentYear;
        WebLocator btn = goNext ? nextYears : prevYears;
        WebLink yearEl = new WebLink(yearContainer).setText(year, SearchType.EQUALS).setVisibility(true).setInfoMessage("year " + year);
        boolean found;
        do {
            found = yearEl.waitToRender(Duration.ofMillis(150), false);
            if (!found) {
                btn.click();
            }
        } while (!found && !foundYear(yearInt, goNext));
        try {
            RetryUtils.retry(3, () -> {
                yearEl.click();
                return yearEl.getAttributeClass().contains("-selected");
            });
        } catch (WebDriverException e) {
            if (tooltip.waitToRender(Duration.ofMillis(500), false)) {
                WebLocator monthEl = new WebLocator(monthContainer).setText("Jan", SearchType.EQUALS).setInfoMessage("month Jan");
                monthEl.mouseOver();
                Utils.sleep(300);
            }
            yearEl.click();
        }
    }

    private boolean foundYear(int yearInt, boolean goNext) {
        WebLink yearEl = new WebLink(yearContainer).setResultIdx(12);
        int actualYear = Integer.parseInt(yearEl.getText());
        return goNext ? yearInt <= actualYear : yearInt >= actualYear;
    }

    /**
     * example new DataField().select("19/05/2013")
     *
     * @param date accept only this format: 'dd/MM/yyyy'
     * @return true if is selected date, false when DataField doesn't exist
     */
    public boolean select(String date) {
        return RetryUtils.retry(2, () -> select(date, "dd/MM/yyyy"));
    }

    public boolean select(String date, String format) {
        return select(date, format, Locale.ENGLISH);
    }

    /**
     * example new DataField().select("19/05/2013", "dd/MM/yyyy", Locale.ENGLISH);
     *
     * @param date   in string format
     * @param format set format date
     * @param locale set locale
     * @return true if is selected date, false when DataField doesn't exist
     */
    public boolean select(String date, String format, Locale locale) {
        boolean hasHour = format.contains("HH:");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, locale);
        if (hasHour) {
            LocalDateTime localDate = LocalDateTime.parse(date, formatter);
            int day = localDate.getDayOfMonth();
            String month = localDate.getMonth().getDisplayName(TextStyle.SHORT, locale);
            int year = localDate.getYear();
            int hour = localDate.getHour();
            int minute = localDate.getMinute();
            ready();
            RetryUtils.retry(2, () -> {
                trigger.click();
                return datePicker.ready(Duration.ofSeconds(1));
            });
            log.debug("select: " + date);
            return setHour(hour, minute) && setDate(day + "", month, year + "");
        } else {
            LocalDate localDate = LocalDate.parse(date, formatter);
            int day = localDate.getDayOfMonth();
            String month = localDate.getMonth().getDisplayName(TextStyle.SHORT, locale);
            int year = localDate.getYear();
            ready();
            RetryUtils.retry(2, () -> {
                trigger.click();
                return datePicker.ready(Duration.ofSeconds(1));
            });
            log.debug("select: " + date);
            return setDate(day + "", month, year + "");
        }
    }
}