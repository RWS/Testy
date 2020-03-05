package com.sdl.selenium.toolsqa;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.link.WebLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static java.time.format.TextStyle.FULL;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
 * <p>If you want more information about this datepicker visit this site: https://demoqa.com/datepicker/</p>
 * <p>Example:</p>
 * <pre>{@code
 * <p>
 *     "Date: "
 *  <input type="text" id="datepicker" class="hasDatepicker">
 * </p>
 * }</pre>
 * <p>In Java write this:</p>
 * <pre>{@code
 * DatePicker datePicker = new DatePicker().setId("dp3");
 * datePicker.select("19/02/2016");
 * }</pre>
 */
public class DatePicker extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatePicker.class);

    private LocalDate dateToSelect;
    private WebLocator dataPicker = new WebLocator().setClasses("ui-datepicker").setStyle("display: block;");
    private WebLocator dataPickerMonth = new WebLocator(dataPicker).setClasses("ui-datepicker-month");
    private WebLocator dataPickerYear = new WebLocator(dataPicker).setClasses("ui-datepicker-year");
    private WebLink daySelect = new WebLink(dataPicker).setClasses("ui-state-default");

    public DatePicker() {
        setClassName("DatePicker");
        setTag("input");
        setType("text");
    }

    public DatePicker(WebLocator container) {
        this();
        setContainer(container);
    }

    public DatePicker(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        setLabelTag("p");
        setLabelPosition("//");
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.CONTAINS};
        } else {
            List<SearchType> types = new ArrayList<>(Arrays.asList(searchTypes));
            types.add(SearchType.CONTAINS);
            searchTypes = types.toArray(new SearchType[0]);
        }
        setLabel(label, searchTypes);
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

    /**
     * example new DatePicker().select("19/05/2013", "dd/MM/yyyy", Locale.ENGLISH)
     *
     * @param date   "19/05/2013"
     * @param format "dd/MMM/yyyy"
     * @param locale Locale.ENGLISH
     * @return true if is selected date, false when DatePicker doesn't exist
     */
    public boolean select(String date, String format, Locale locale) {
        dateToSelect = LocalDate.parse(date, DateTimeFormatter.ofPattern(format, locale));
        return setDate(dateToSelect.getDayOfMonth() + "", dateToSelect.getYear() + "");
    }

    private boolean setDate(String day, String year) {
        if (this.click()) {
            goToYearAndMonth(year);
            daySelect.setText(day, SearchType.EQUALS);
            return daySelect.click();
        }
        return false;
    }

    private void goToYearAndMonth(String year) {
        String currentYear = dataPickerYear.getText();
        String currentMonth = dataPickerMonth.getText();
        if (!year.equals(currentYear) || !dateToSelect.getMonth().getDisplayName(FULL, Locale.ENGLISH).equals(currentMonth)) {
            int currentYearInt = Integer.parseInt(currentYear);
            LocalDate of = LocalDate.of(currentYearInt, Month.valueOf(currentMonth.toUpperCase()), 1);
            int currentMonthValue = of.getMonthValue();
            int monthCount = dateToSelect.getMonthValue() - currentMonthValue;
            int count = ((dateToSelect.getYear() - currentYearInt) * 12) + monthCount;

            WebLink next = new WebLink(dataPicker).setClasses("ui-datepicker-next");
            WebLink prev = new WebLink(dataPicker).setClasses("ui-datepicker-prev");
            while (count > 0) {
                next.click();
                count--;
            }

            while (0 > count) {
                prev.click();
                count++;
            }
        }
    }

    public String getDate() {
        return getAttribute("value");
    }

    /**
     * example new DatePicker().setValue("05/19/2020")
     * @param date "05/19/2020"
     * @return true if is selected date, false when DatePicker doesn't exist
     */
    public boolean setValue(String date) {
        return executor.setValue(this, date);
    }
}