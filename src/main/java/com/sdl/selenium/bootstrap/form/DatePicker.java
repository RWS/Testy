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
 * DatePicker datePicker = new DatePicker().withId("dp3");
 * datePicker.select("19/02/2016");
 * }</pre>
 */
public class DatePicker extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatePicker.class);

    private WebLocator input = new WebLocator(this).withClasses("icon-calendar").withInfoMessage("Open Calendar");
    private WebLocator dataPicker = new WebLocator().withClasses("datepicker-dropdown dropdown-menu").withStyle("display: block;");
    private WebLocator dataPickerDays = new WebLocator(dataPicker).withClasses("datepicker-days").withStyle("display: block;");
    private WebLocator dataPickerMonths = new WebLocator(dataPicker).withClasses("datepicker-months").withStyle("display: block;");
    private WebLocator dataPickerYear = new WebLocator(dataPicker).withClasses("datepicker-years").withStyle("display: block;");
    private WebLocator switchDay = new WebLocator(dataPickerDays).withClasses("switch").withInfoMessage("switchMonth");
    private WebLocator switchMonth = new WebLocator(dataPickerMonths).withClasses("switch").withInfoMessage("switchYear");

    private WebLocator monthSelect = new WebLocator(dataPickerMonths).withClasses("month");
    private WebLocator yearSelect = new WebLocator(dataPickerYear).withClasses("year");
    private WebLocator daySelect = new WebLocator(dataPickerDays).withClasses("day").setExcludeClasses("old", "new");

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
        withClassName("DatePicker");
        withClasses("date");
    }

    public DatePicker(WebLocator container) {
        this();
        withContainer(container);
    }

    public DatePicker(WebLocator container, String id) {
        this(container);
        withId(id);
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
            getMonthSelect().withText(month);
            String fullDate = getSwitchDay().getText();
            if (!fullDate.contains(year)) {
                getSwitchDay().click();
                getSwitchMonth().click();
                goToYear(year, fullDate);
                getYearSelect().withText(year, SearchType.EQUALS);
                ok = getYearSelect().click() &&
                        getMonthSelect().click();
            } else if (!fullDate.contains(month)) {
                getSwitchDay().click();
                ok = getMonthSelect().click();
            }
            getDaySelect().withText(day, SearchType.EQUALS);
            return ok && getDaySelect().click();
        }
        return false;
    }

    private void goToYear(String year, String fullDate) {
        int currentYear = Integer.parseInt(fullDate.split(" ")[1]);
        int yearInt = Integer.parseInt(year);
        int count = (int) Math.ceil((yearInt - currentYear)/10.0);

        while (count > 0){
            WebLocator next = new WebLocator(dataPickerYear).setClasses("next");
            next.click();
           count--;
        }

        while (0 > count){
            WebLocator prev = new WebLocator(dataPickerYear).setClasses("prev");
            prev.click();
            count++;
        }
    }

    public String getDate() {
        WebLocator webLocator = new WebLocator(this).withTag("input");
        return webLocator.getAttribute("value");
    }
}