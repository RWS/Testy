package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import com.extjs.selenium.button.Button;
import org.apache.log4j.Logger;

public class Date extends TextField {
    private static final Logger logger = Logger.getLogger(Date.class);

    // TODO verify where is used and how
    public Date() {
        setClassName("Date");
        //logger.debug(getClassName() + "() constructor");
    }

    public Date(ExtJsComponent container, String cls) {
        this();
        setContainer(container);
        setCls(cls);
    }

    public Date(String name, ExtJsComponent container) {
        this();
        setContainer(container);
        setName(name);
    }

    // TODO more selectors

    // TODO make it not coupled to GetData and BeGlobal
    public boolean setDate(String day, String month, String year, String currentMonth, String currentYear) {
        boolean saved;

        // TODO use this logic in ComboBox
        ExtJsComponent calendarLayer = new ExtJsComponent("x-layer");
        calendarLayer.setStyle("visibility: visible;");
        Button monthYearButton = new Button(calendarLayer);
        Button calendarToday = new Button(calendarLayer, "Today");
        ExtJsComponent selectOkButton = new ExtJsComponent("x-date-mp-ok",calendarLayer);

        // click on trigger or self to open date menu
        saved = click();
        if (saved) {
            calendarToday.click();
            saved = false;
        }

        saved = click();
        if(saved){
            monthYearButton.setText(currentMonth + " " + currentYear);
            monthYearButton.click();
            logger.info("Setting the year and month");
            new ExtJsComponent(calendarLayer, "//*[contains(@class, 'x-date-mp-year')]//*[text() = '" + year + "']").click();
            new ExtJsComponent(calendarLayer, "//*[contains(@class, 'x-date-mp-month')]//*[text() = '" + month.substring(0,3) + "']").click();
            selectOkButton.click();

            new ExtJsComponent(calendarLayer, "//*[contains(@class, 'x-date-inner')]//*[contains(@class, 'x-date-active')]//*[text() = '" + day + "']").click();
            logger.info("Setting the day");
        }
        if(!saved){
            logger.warn("The selected month doesn't have the " + day + " day.");
        }
        return saved;
    }
}
