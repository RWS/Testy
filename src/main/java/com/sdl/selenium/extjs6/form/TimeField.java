package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.extjs6.panel.Pagination;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICombo;
import com.sdl.selenium.web.utils.Utils;
import org.apache.log4j.Logger;

public class TimeField extends ComboBox implements ICombo {
    private static final Logger LOGGER = Logger.getLogger(TimeField.class);
    private WebLocator boundList = new WebLocator("x-boundlist");
    private Pagination paginationEl = new Pagination(boundList).setRenderMillis(300);

    public TimeField() {
        withClassName("TimeField");
    }

    public TimeField(WebLocator container) {
        this();
        withContainer(container);
    }

    public TimeField(WebLocator container, String label) {
        this(container);
        withLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    /**
     * @param value              value
     * @param searchType         use {@link SearchType}
     * @param optionRenderMillis eg. 300ms
     * @param pagination eg. true or false
     * @return true if value was selected
     */
    public boolean doSelect(String value, long optionRenderMillis, boolean pagination, SearchType... searchType) {
        boolean selected;
        String info = toString();
        WebLocator option = getComboEl(value, optionRenderMillis, searchType).setVisibility(true);
        if (clickIcon("time")) {
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
                clickIcon("time"); // to close combo
            }
            LOGGER.debug("(" + info + ") The option '" + value + "' could not be located. " + option.getXPath());
        } else {
            LOGGER.debug("(" + info + ") The combo or arrow could not be located.");
        }
        return false;
    }
}
