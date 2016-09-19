package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.extjs6.button.Button;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateField extends TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateField.class);

    private WebLocator calendarLayer = new WebLocator("x-layer").withAttribute("aria-hidden", "false").setVisibility(true);
    private Button monthYearButton = new Button(calendarLayer);
    private WebLocator selectOkButton = new WebLocator(calendarLayer).setText("OK").setVisibility(true).withInfoMessage("Ok");
    private WebLocator yearAndMonth = new WebLocator(calendarLayer).setClasses("x-monthpicker").setVisibility(true);
    private WebLocator nextYears = new WebLocator(yearAndMonth).setClasses("x-monthpicker-yearnav-next").setVisibility(true);
    private WebLocator prevYears = new WebLocator(yearAndMonth).setClasses("x-monthpicker-yearnav-prev").setVisibility(true);
    private WebLocator yearContainer = new WebLocator(yearAndMonth).withClasses("x-monthpicker-years");
    private WebLocator monthContainer = new WebLocator(yearAndMonth).withClasses("x-monthpicker-months");
    private WebLocator dayContainer = new WebLocator(calendarLayer).withClasses("x-datepicker-active");

    public DateField() {
        withClassName("DateField");
    }

    public DateField(WebLocator container) {
        this();
        withContainer(container);
    }

    public DateField(WebLocator container, String label) {
        this(container);
        withLabel(label);
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
        WebLocator trigger = new WebLocator(this).setRoot("/../../").setClasses("x-form-date-trigger");
        trigger.click();
        String fullDate = monthYearButton.getText().trim();
        if (!fullDate.contains(month) || !fullDate.contains(year)) {
            monthYearButton.click();
            if (!yearAndMonth.ready()) {
                monthYearButton.click();
            }
            goToYear(year, fullDate);
            WebLocator monthEl = new WebLocator(monthContainer).setText(month, SearchType.EQUALS).withInfoMessage("month " + month);
            monthEl.click();
            selectOkButton.click();
        }
        WebLocator dayEl = new WebLocator(dayContainer).withText(day, SearchType.EQUALS).setVisibility(true).withInfoMessage("day " + day);
        Utils.sleep(50);
        return dayEl.click();
    }

    private void goToYear(String year, String fullDate) {
        int currentYear = Integer.parseInt(fullDate.split(" ")[1]);
        int yearInt = Integer.parseInt(year);
        int con = yearInt > currentYear ? -4 : 4;
        int count = (int) Math.ceil((yearInt - currentYear - con) / 10);
        selectYearPage(count);
        WebLocator yearEl = new WebLocator(yearContainer).setText(year, SearchType.EQUALS).withInfoMessage("year " + year);
        if (!yearEl.waitToRender(200)) {
            selectYearPage(count > 0 ? 1 : -1);
        }
        try {
            yearEl.click();
        } catch (Exception e) {
            Utils.sleep(500);
            yearEl.click();
        }
    }

    private void selectYearPage(int count) {
        WebLocator btn = count > 0 ? nextYears : prevYears;
        count = Math.abs(count);
        while (count > 0) {
            btn.click();
            count--;
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
