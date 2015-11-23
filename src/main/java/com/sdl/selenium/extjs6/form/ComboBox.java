package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICombo;
import com.sdl.selenium.web.utils.Utils;
import org.apache.log4j.Logger;

import static org.hamcrest.MatcherAssert.assertThat;

public class ComboBox extends TextField implements ICombo {
    private static final Logger LOGGER = Logger.getLogger(ComboBox.class);
    private static String listClass = "x-list-plain";

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
     * @param value value
     * @return true if value was selected
     */

    public boolean select(String value, boolean startWith, long optionRenderMillis) {
        boolean selected;
        String info = toString();
        WebLocator option = getComboEl(value, startWith, optionRenderMillis);

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

    private WebLocator getComboEl(String value, boolean startWith, long optionRenderMillis) {
        WebLocator comboListElement = new WebLocator(listClass).setAttribute("aria-hidden", "false").setInfoMessage(this + " -> " + listClass);
        return new WebLocator(comboListElement).setText(value, startWith ? SearchType.STARTS_WITH : SearchType.EQUALS).setRenderMillis(optionRenderMillis).setInfoMessage(value);
    }

    public boolean select(String value, boolean startWith) {
        return select(value, startWith, 300);
    }

    @Override
    public boolean select(String value) {
        return select(value, false);
    }

    @Override
    public String getValue() {
        String value = null;
        if (clickIcon("arrow")) {
            WebLocator option = getComboEl(null, false, 300).setClasses("x-boundlist-selected");
            value = option.getHtmlText();
            clickIcon("arrow"); // to close combo
        } else {
            LOGGER.debug("(" + this + ") The combo or arrow could not be located.");
        }
        return value;
    }

    public boolean assertSelect(String value) {
        boolean selected = select(value);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

   /* public static void main(String[] args) {
        ComboBox comboBox = new ComboBox(null, "[review]Choose Source");
        LOGGER.info(comboBox.getXPath());
    }*/
}
