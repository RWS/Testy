package com.extjs.selenium.form;

import com.extjs.selenium.Utils;
import com.extjs.selenium.button.Button;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateField extends TextField {
    private static final Logger logger = Logger.getLogger(DateField.class);

    public DateField() {
        setClassName("DateField");
        //logger.debug(getClassName() + "() constructor");
    }

    public DateField(WebLocator container, String cls) {
        this();
        setContainer(container);
        setCls(cls);
    }

    public DateField(String name, WebLocator container) {
        this();
        setContainer(container);
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
        WebLocator selectOkButton = new WebLocator("x-date-mp-ok", calendarLayer);
        if (click()) {
            monthYearButton.click();
            WebLocator yearEl = new WebLocator(calendarLayer, "//*[contains(@class, 'x-date-mp-year')]//*[text() = '" + year + "']").setInfoMessage("year " + year);
            yearEl.click();
            WebLocator monthEl = new WebLocator(calendarLayer, "//*[contains(@class, 'x-date-mp-month')]//*[text() = '" + month + "']").setInfoMessage("month " + month);
            monthEl.click();
            selectOkButton.click();
            Utils.sleep(100);
            return new WebLocator(calendarLayer, "//*[contains(@class, 'x-date-inner')]//*[contains(@class, 'x-date-active')]//*[text() = '" + Integer.parseInt(day) + "']").click();
        } else {
            logger.warn("The selected month doesn't have the " + day + " day.");
            return false;
        }
    }

    /**
     * example new DataField().setDate("19/05/2013")
     *
     * @param date accept only this format: 'dd/MM/yyyy'
     * @return true if is selected date, false when DataField doesn't exist
     */
    public boolean select(String date) {
        return select(date, "dd/MM/yyyy");
    }

    public boolean select(String date, String format) {
        SimpleDateFormat inDateFormat = new SimpleDateFormat(format);
        SimpleDateFormat outDateForm = new SimpleDateFormat("dd/MMM/yyyy");
        Date fromDate = null;
        try {
            fromDate = inDateFormat.parse(date);
            date = outDateForm.format(fromDate);
        } catch (ParseException e) {
            logger.error(e);
        }

        logger.debug("select: " + date);
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
