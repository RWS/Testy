package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateField extends TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateField.class);

    private WebLocator calendarLayer = new WebLocator("x-layer").withStyle("visibility: visible;");
    private Button monthYearButton = new Button(calendarLayer);
    private WebLocator selectOkButton = new WebLocator("x-date-mp-ok", calendarLayer).withInfoMessage("Ok");
    private WebLocator yearAndMonth = new WebLocator(calendarLayer).setClasses("x-date-mp").setVisibility(true);
    private WebLocator yearContainer = new WebLocator(yearAndMonth).withClasses("x-date-mp-year");
    private WebLocator monthContainer = new WebLocator(yearAndMonth).withClasses("x-date-mp-month");
    private WebLocator dayInner = new WebLocator(calendarLayer).withClasses("x-date-inner");
    private WebLocator dayContainer = new WebLocator(dayInner).withClasses("x-date-active");

    public DateField() {
        withClassName("DateField");
    }

    public DateField(WebLocator container) {
        this();
        withContainer(container);
    }

    public DateField(WebLocator container, String cls) {
        this(container);
        withClasses(cls);
    }

    public DateField(String name, WebLocator container) {
        this(container);
        withName(name);
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
        click();
        String fullDate = monthYearButton.getText().trim();
        if (!fullDate.contains(month) || !fullDate.contains(year)) {
            monthYearButton.click();
            goToYear(year, fullDate);
            WebLocator yearEl = new WebLocator(yearContainer).setText(year, SearchType.EQUALS).withInfoMessage("year " + year);
            yearEl.click();
            WebLocator yearContainer1 = new WebLocator(yearAndMonth).withClasses("x-date-mp-year", "x-date-mp-sel");
            WebLocator yearEl1 = new WebLocator(yearContainer1).setText(year, SearchType.EQUALS);
            if (!yearEl1.ready(1)) {
                yearEl.click();
            }
            WebLocator monthEl = new WebLocator(monthContainer).setText(month, SearchType.EQUALS).withInfoMessage("month " + month);
            monthEl.click();
            selectOkButton.click();
        }
        WebLocator dayEl = new WebLocator(dayContainer).withText(day, SearchType.EQUALS).withInfoMessage("day " + day);
        dayEl.click();
        return true;
    }

    private void goToYear(String year, String fullDate) {
        int currentYear = Integer.parseInt(fullDate.split(" ")[1]);
        int yearInt = Integer.parseInt(year);
        int con = yearInt > currentYear ? -4 : 4;
        int count = (int) Math.ceil((yearInt - currentYear - con) / 10);
        toYear(count);
        WebLocator yearEl = new WebLocator(yearContainer).setText(year, SearchType.EQUALS).withInfoMessage("year " + year);
        if (!yearEl.waitToRender(200)) {
            toYear(count > 0 ? 1 : -1);
        }
        yearEl.click();
    }

    private void toYear(int count) {
        while (count > 0) {
            WebLocator next = new WebLocator(yearAndMonth).setClasses("x-date-mp-next").setVisibility(true);
            next.click();
            count--;
        }

        while (0 > count) {
            WebLocator prev = new WebLocator(yearAndMonth).setClasses("x-date-mp-prev").setVisibility(true);
            prev.click();
            count++;
        }
    }

    /**
     * example new DataField().select("19/05/2013")
     *
     * @param date accept only this format: 'dd/MM/yyyy'
     * @return true if is selected date, false when DataField doesn't exist
     */
    public boolean select(String date) {
        return select(date, "dd/MM/yyyy");
    }

    public boolean select(String date, String format) {
        return select(date, format, Locale.ENGLISH);
    }

    public boolean select(String date, String format, Locale locale) {
        SimpleDateFormat inDateFormat = new SimpleDateFormat(format, locale);
        SimpleDateFormat outDateForm = new SimpleDateFormat("dd/MMM/yyyy", locale);
        Date fromDate;
        try {
            fromDate = inDateFormat.parse(date);
            date = outDateForm.format(fromDate);
        } catch (ParseException e) {
            LOGGER.error("ParseException: {}", e);
        }

        LOGGER.debug("select: " + date);
        String[] dates = date.split("/");
        return setDate(Integer.parseInt(dates[0]) + "", dates[1], dates[2]);
    }

    public boolean select(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/YYYY");
        String dateStr = sdf.format(date);
        return select(dateStr);
    }

    public boolean selectToday() {
        return select(new Date());
    }
}
