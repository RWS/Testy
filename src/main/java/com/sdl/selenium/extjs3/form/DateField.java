package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.button.Button;
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

    public DateField() {
        setClassName("DateField");
    }

    public DateField(WebLocator container) {
        this();
        setContainer(container);
    }

    public DateField(WebLocator container, String cls) {
        this(container);
        setClasses(cls);
    }

    public DateField(String name, WebLocator container) {
        this(container);
        setName(name);
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
        WebLocator calendarLayer = new WebLocator("x-layer").setStyle("visibility: visible;");
        Button monthYearButton = new Button(calendarLayer);
        WebLocator selectOkButton = new WebLocator("x-date-mp-ok", calendarLayer).setInfoMessage("Ok");
        if (click()) {
            monthYearButton.click();
            Utils.sleep(100);
            // TODO find elements without setElPath
            WebLocator yearEl = new WebLocator(calendarLayer).setElPath("//*[contains(@class, 'x-date-mp-year')]//*[text() = '" + year + "']").setInfoMessage("year " + year);
            yearEl.click();
            WebLocator monthEl = new WebLocator(calendarLayer).setElPath("//*[contains(@class, 'x-date-mp-month')]//*[text() = '" + month + "']").setInfoMessage("month " + month);
            monthEl.click();
            selectOkButton.click();
            Utils.sleep(60); // wait for Chrome
            WebLocator dayEl = new WebLocator(calendarLayer).setElPath("//*[contains(@class, 'x-date-inner')]//*[contains(@class, 'x-date-active')]//*[text() = '" + Integer.parseInt(day) + "']").setInfoMessage("day " + day);
            return dayEl.click();
        } else {
            LOGGER.warn("The selected month doesn't have the " + day + " day.");
            return false;
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
        String day = dates[0];
        String month = dates[1];
        String year = dates[2];
        return setDate(day, month, year);
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
