package com.sdl.selenium.extjs6.form;

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
     * @deprecated use {@link #doSelect(String, SearchType, long)}
     * @param value              value
     * @param searchType         use {@link SearchType}
     * @param optionRenderMillis eg. 300ms
     * @return true if value was selected
     */
    public boolean select(String value, SearchType searchType, long optionRenderMillis) {
        return doSelect(value, searchType, optionRenderMillis);
    }

    /**
     * @param value              value
     * @param searchType         use {@link SearchType}
     * @param optionRenderMillis eg. 300ms
     * @return true if value was selected
     */
    public boolean doSelect(String value, SearchType searchType, long optionRenderMillis) {
        boolean selected;
        String info = toString();
        WebLocator option = getComboEl(value, searchType, optionRenderMillis);

        if (clickIcon("arrow")) {
            selected = option.click();
            if (selected) {
                LOGGER.info("Set value(" + info + "): " + value);
                Utils.sleep(20);
                return true;
            } else {
                clickIcon("arrow"); // to close combo
            }
            LOGGER.debug("(" + info + ") The option '" + value + "' could not be located. " + option.getXPath());
        } else {
            LOGGER.debug("(" + info + ") The combo or arrow could not be located.");
        }
        return false;
    }

    private WebLocator getComboEl(String value, SearchType searchType, long optionRenderMillis) {
        WebLocator comboListElement = new WebLocator(listClass).withAttribute("aria-hidden", "false").withInfoMessage(this + " -> " + listClass);
        return new WebLocator(comboListElement).withText(value, searchType).withRenderMillis(optionRenderMillis).withInfoMessage(value);
    }

    public boolean select(String value, SearchType searchType) {
        boolean selected = select(value, searchType, 300);
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

    public List<String> getAllComboValues() {
        clickIcon("arrow");
        WebLocator comboList = new WebLocator(new WebLocator("x-boundlist")).withClasses(listClass).withVisibility(true);
        String[] comboValues = comboList.getText().split("\\n");
        clickIcon("arrow");
        return Arrays.asList(comboValues);
    }

    @Deprecated
    public boolean assertSelect(String value) {
        return select(value);
    }
}
