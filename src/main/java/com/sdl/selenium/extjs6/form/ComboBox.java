package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.extjs6.panel.Pagination;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICombo;
import com.sdl.selenium.web.utils.Utils;
import org.openqa.selenium.StaleElementReferenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class ComboBox extends TextField implements ICombo {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComboBox.class);
    private static String listClass = "x-list-plain";
    private WebLocator boundList = new WebLocator("x-boundlist").setVisibility(true);
    private Pagination paginationEl = new Pagination(boundList).setRenderMillis(300);

    public ComboBox() {
        setClassName("ComboBox");
    }

    public ComboBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public ComboBox(WebLocator container, String label) {
        this(container);
        setLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    /**
     * @param value              value
     * @param optionRenderMillis eg. 300ms
     * @param pagination         true | false
     * @param searchType         use {@link SearchType}
     * @return true if value was selected
     */
    public boolean doSelect(String value, long optionRenderMillis, boolean pagination, SearchType... searchType) {
        waitToRender(300L);
        boolean selected;
        String info = toString();
        WebLocator option = getComboEl(value, optionRenderMillis, searchType).setVisibility(true);
        boolean trigger;
        try {
            trigger = boundList.isDisplayed() || clickIcon("trigger");
        } catch (StaleElementReferenceException e) {
            LOGGER.debug("StaleElementReferenceException1");
            trigger = clickIcon("trigger");
        }
        if (trigger) {
            if (pagination) {
                do {
                    if (selected = option.doClick()) {
                        break;
                    }
                } while (paginationEl.goToNextPage());
            } else {
                selected = option.doClick();
            }
            if (selected) {
                LOGGER.info("Set value(" + info + "): " + value);
                Utils.sleep(20);
                return true;
            }
            try {
                if (boundList.isDisplayed()) {
                    clickIcon("trigger"); // to close combo
                }
            } catch (StaleElementReferenceException e) {
                LOGGER.debug("StaleElementReferenceException2");
            }
            LOGGER.debug("(" + info + ") The option '" + value + "' could not be located. " + option.getXPath());
        } else {
            LOGGER.debug("(" + info + ") The combo or arrow could not be located.");
        }
        return false;
    }

    public boolean doSelect(String value, long optionRenderMillis, SearchType... searchType) {
        return doSelect(value, optionRenderMillis, false, searchType);
    }

    protected WebLocator getComboEl(String value, long optionRenderMillis, SearchType... searchType) {
        return new WebLocator(boundList).setTag("li").setText(value, searchType).setRenderMillis(optionRenderMillis).setInfoMessage(value);
    }

    public boolean select(String value, SearchType... searchType) {
        boolean selected = doSelect(value, 300L, false, searchType);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    public boolean select(String value, long optionRenderMillis) {
        boolean selected = doSelect(value, optionRenderMillis, false, SearchType.EQUALS);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    public boolean select(String value, boolean pagination) {
        boolean selected = doSelect(value, 300L, pagination, SearchType.EQUALS);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    public boolean select(String value, long optionRenderMillis, boolean pagination) {
        boolean selected = doSelect(value, optionRenderMillis, pagination, SearchType.EQUALS);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    public boolean select(String value, long optionRenderMillis, boolean pagination, SearchType... searchType) {
        boolean selected = doSelect(value, optionRenderMillis, pagination, searchType);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    @Override
    public boolean select(String value) {
        return select(value, SearchType.EQUALS);
    }

    @Override
    public String getValue() {
        ready();
        return executor.getValue(this);
    }

    public List<String> getAllValues() {
        waitToRender(300L);
        try {
            boolean trigger = boundList.isDisplayed() || clickIcon("trigger");
        } catch (StaleElementReferenceException e) {
            LOGGER.debug("StaleElementReferenceException1");
            clickIcon("trigger");
        }
        WebLocator comboList = new WebLocator(boundList).setClasses(listClass).setVisibility(true);
        String text = comboList.getText();
        String[] comboValues = new String[0];
        if (text != null) {
            comboValues = text.split("\\n");
        }
        try {
            if (boundList.isDisplayed()) {
                clickIcon("trigger"); // to close combo
            }
        } catch (StaleElementReferenceException e) {
            LOGGER.debug("StaleElementReferenceException2");
        }
        return Arrays.asList(comboValues);
    }
}
