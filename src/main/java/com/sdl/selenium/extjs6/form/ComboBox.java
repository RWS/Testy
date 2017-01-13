package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.extjs6.panel.Pagination;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICombo;
import com.sdl.selenium.web.utils.Utils;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class ComboBox extends TextField implements ICombo {
    private static final Logger LOGGER = Logger.getLogger(ComboBox.class);
    private static String listClass = "x-list-plain";
    private WebLocator boundList = new WebLocator("x-boundlist");
    private Pagination paginationEl = new Pagination(boundList).setRenderMillis(300);

    public ComboBox() {
        withClassName("ComboBox");
    }

    public ComboBox(WebLocator container) {
        this();
        withContainer(container);
    }

    public ComboBox(WebLocator container, String label) {
        this(container);
        withLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    /**
     * @param value              value
     * @param searchType         use {@link SearchType}
     * @param optionRenderMillis eg. 300ms
     * @return true if value was selected
     */
    public boolean doSelect(String value, long optionRenderMillis, boolean pagination, SearchType... searchType) {
        boolean selected;
        String info = toString();
        WebLocator option = getComboEl(value, optionRenderMillis, searchType).setVisibility(true);
        if (clickIcon("trigger")) {
            if (pagination) {
                do {
                    if (selected = option.doClick()) {
                        break;
                    }
                } while (paginationEl.goToNextPage());
            } else {
                selected = option.click();
            }
            if (selected) {
                LOGGER.info("Set value(" + info + "): " + value);
                Utils.sleep(20);
                return true;
            } else {
                clickIcon("trigger"); // to close combo
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
        WebLocator comboListElement = new WebLocator(listClass).withAttribute("aria-hidden", "false").withInfoMessage(this + " -> " + listClass);
        return new WebLocator(comboListElement).withText(value, searchType).withRenderMillis(optionRenderMillis).withInfoMessage(value);
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
        clickIcon("trigger");
        WebLocator comboList = new WebLocator(boundList).withClasses(listClass).withVisibility(true);
        String[] comboValues = comboList.getText().split("\\n");
        clickIcon("trigger");
        return Arrays.asList(comboValues);
    }
}
