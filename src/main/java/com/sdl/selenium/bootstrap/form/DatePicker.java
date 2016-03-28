package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
 * <p>If you want more information about this datepicker visit this site: http://vitalets.github.io/bootstrap-datepicker/</p>
 * <p>Example:</p>
 * <pre>{@code
 * <div class="input-append date datepicker" id="dp3">
 *  <input data-bind="dueDate" size="16" type="text" readonly="" class="span2">
 *  <span class="add-on"><i class="icon-calendar"></i></span>
 * </div>
 * }</pre>
 * <p>In Java write this:</p>
 * <pre>{@code
 * DatePicker datePicker = new DatePicker().setId("dp3");
 * datePicker.select("19/02/2016");
 * }</pre>
 */
public class DatePicker extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatePicker.class);

    private WebLocator input = new WebLocator(this).setClasses("icon-calendar").setInfoMessage("Open Calendar");
    private WebLocator dataPicker = new WebLocator().setClasses("datepicker-dropdown dropdown-menu").setStyle("display: block;");
    private WebLocator dataPickerDays = new WebLocator(dataPicker).setClasses("datepicker-days").setStyle("display: block;");
    private WebLocator dataPickerMonths = new WebLocator(dataPicker).setClasses("datepicker-months").setStyle("display: block;");
    private WebLocator dataPickerYear = new WebLocator(dataPicker).setClasses("datepicker-years").setStyle("display: block;");
    private WebLocator switchDay = new WebLocator(dataPickerDays).setClasses("switch").setInfoMessage("switchMonth");
    private WebLocator switchMonth = new WebLocator(dataPickerMonths).setClasses("switch").setInfoMessage("switchYear");

    private WebLocator monthSelect = new WebLocator(dataPickerMonths).setClasses("month");
    private WebLocator yearSelect = new WebLocator(dataPickerYear).setClasses("year");
    private WebLocator daySelect = new WebLocator(dataPickerDays).setClasses("day");

    public WebLocator getInput() {
        return input;
    }

    public WebLocator getSwitchDay() {
        return switchDay;
    }

    public WebLocator getSwitchMonth() {
        return switchMonth;
    }

    public WebLocator getMonthSelect() {
        return monthSelect;
    }

    public WebLocator getYearSelect() {
        return yearSelect;
    }

    public WebLocator getDaySelect() {
        return daySelect;
    }

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
        return select(date, "dd/MM/yyyy", Locale.ENGLISH);
    }

    public boolean select(String date, String format, Locale locale) {
        SimpleDateFormat inDateFormat = new SimpleDateFormat(format, locale);
        SimpleDateFormat outDateForm = new SimpleDateFormat("dd/MMM/yyyy");
        try {
            Date fromDate = inDateFormat.parse(date);
            date = outDateForm.format(fromDate);
        } catch (ParseException e) {
            LOGGER.error("ParseException: {}", e);
        }
        String[] dates = date.split("/");
        return setDate(Integer.parseInt(dates[0]) + "", dates[1], dates[2]);
    }

    public boolean setDate(String day, String month, String year) {
        if (getInput().click()) {
            boolean ok = true;
            getMonthSelect().setText(month);
            String fullDate = getSwitchDay().getText();
            if (!fullDate.contains(year)) {
                getSwitchDay().click();
                getSwitchMonth().click();
                getYearSelect().setText(year, SearchType.EQUALS);
                ok = getYearSelect().click() &&
                        getMonthSelect().click();
            } else if (!fullDate.contains(month)) {
                getSwitchDay().click();
                ok = getMonthSelect().click();
            }
            getDaySelect().setText(day, SearchType.EQUALS);
            return ok && getDaySelect().click();
        }
        return false;
    }

    public String getDate() {
        WebLocator webLocator = new WebLocator(this).setTag("input");
        return webLocator.getAttribute("value");
    }
}