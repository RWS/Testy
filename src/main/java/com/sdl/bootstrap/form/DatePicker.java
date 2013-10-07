package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * If you want more information about this datepicker visit this site: http://vitalets.github.io/bootstrap-datepicker/
 */
public class DatePicker extends WebLocator {
    private static final Logger logger = Logger.getLogger(DatePicker.class);

    public DatePicker() {
        setClassName("DatePicker");
        setClasses("date");
    }

    public DatePicker(WebLocator container) {
        this();
        setContainer(container);
    }

    public DatePicker(WebLocator container, String id) {
        this(container);
        setId(id);
    }

    /**
     * example new DatePicker().select("19/05/2013")
     *
     * @param date accept only this format: 'dd/MM/yyyy'
     * @return true if is selected date, false when DatePicker doesn't exist
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
        String[] dates = date.split("/");
        String day = dates[0];
        String month = dates[1];
        String year = dates[2];
        return setDate(day, month, year);
    }

    private WebLocator icon = new WebLocator(this).setClasses("icon-calendar").setInfoMessage("Open Calendar");
    private WebLocator dataPicker = new WebLocator().setClasses("datepicker-dropdown dropdown-menu").setStyle("display: block;");
    private WebLocator dataPickerDays = new WebLocator(dataPicker).setClasses("datepicker-days").setStyle("display: block;");
    private WebLocator dataPickerMonths = new WebLocator(dataPicker).setClasses("datepicker-months").setStyle("display: block;");
    private WebLocator dataPickerYear = new WebLocator(dataPicker).setClasses("datepicker-years").setStyle("display: block;");
    private WebLocator switchDay = new WebLocator(dataPickerDays).setClasses("switch").setInfoMessage("switchMonth");
    private WebLocator switchMonth = new WebLocator(dataPickerMonths).setClasses("switch").setInfoMessage("switchYear");

    public boolean setDate(String day, String month, String year) {
        if (icon.click()) {
            switchDay.click();
            switchMonth.click();

            WebLocator yearSelect = new WebLocator(dataPickerYear).setClasses("year").setText(year);
            boolean y = yearSelect.click();

            WebLocator monthSelect = new WebLocator(dataPickerMonths).setClasses("month").setText(month);
            boolean m = monthSelect.click();

            WebLocator daySelect = new WebLocator(dataPickerDays).setClasses("day").setText(day);
            return y && m && daySelect.click();
        }
        return false;
    }

    public String getDate(){
        WebLocator webLocator = new WebLocator(this, "//input");
        return webLocator.getAttribute("value");
    }
}