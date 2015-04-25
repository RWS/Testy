package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.web.By;
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

    public DateField(By...bys) {
        super(bys);
    }

    public DateField(WebLocator container) {
        this(By.container(container));
    }

    public DateField(WebLocator container, String cls) {
        this(By.container(container), By.classes(cls));
    }

    public DateField(String name, WebLocator container) {
        this(By.container(container), By.name(name));
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
        WebLocator calendarLayer = new WebLocator(By.classes("x-layer"), By.style("visibility: visible;"));
        Button monthYearButton = new Button(By.container(calendarLayer));
        WebLocator selectOkButton = new WebLocator(By.container(calendarLayer), By.classes("x-date-mp-ok"), By.infoMessage("Ok"));
        if (click()) {
            monthYearButton.click();
            Utils.sleep(100);
            // TODO find elements without setElPath
            WebLocator yearEl = new WebLocator(By.container(calendarLayer), By.xpath("//*[contains(@class, 'x-date-mp-year')]//*[text() = '" + year + "']"), By.infoMessage("year " + year));
            yearEl.click();
            WebLocator monthEl = new WebLocator(By.container(calendarLayer), By.xpath("//*[contains(@class, 'x-date-mp-month')]//*[text() = '" + month + "']"), By.infoMessage("month " + month));
            monthEl.click();
            selectOkButton.click();
            Utils.sleep(60); // wait for Chrome
            WebLocator dayEl = new WebLocator(By.container(calendarLayer), By.xpath("//*[contains(@class, 'x-date-inner')]//*[contains(@class, 'x-date-active')]//*[text() = '" + Integer.parseInt(day) + "']"), By.infoMessage("day " + day));
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
