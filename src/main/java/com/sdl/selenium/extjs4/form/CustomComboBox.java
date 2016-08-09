package com.sdl.selenium.extjs4.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICombo;
import com.sdl.selenium.web.utils.Utils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;

import static org.hamcrest.MatcherAssert.assertThat;

public class CustomComboBox extends TextField implements ICombo {
    private static final Logger LOGGER = Logger.getLogger(CustomComboBox.class);

    public CustomComboBox() {
        withClassName("CustomComboBox");
    }

    public CustomComboBox(WebLocator container) {
        this();
        withContainer(container);
    }

    public CustomComboBox(WebLocator container, String label) {
        this(container);
        withLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public CustomComboBox(WebLocator container, String label, boolean isInternationalized) {
        this(container);
        withLabel(label, isInternationalized, SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public String getTriggerPath(String icon) {
        return "/parent::*/parent::*//*[contains(@class,'x-form-trigger-" + icon + "')]";
    }

    public boolean clickIcon(){
        return super.clickIcon("more");
    }

    /**
     * @param value value
     * @param startWith true or false
     * @param optionRenderMillis eg. 300ms
     * @return true if value was selected
     */
    public boolean select(String value, boolean startWith, long optionRenderMillis) {
        boolean selected;
        String info = toString();
        WebLocator option = getComboEl(value, startWith, optionRenderMillis);
        if (click()) {
            selected = option.click();
            if (selected) {
                LOGGER.info("Set value(" + info + "): " + value);
                Utils.sleep(20);
                return true;
            } else {
                sendKeys(Keys.ESCAPE); // to close combo
            }
            LOGGER.debug("(" + info + ") The option '" + value + "' could not be located. " + option.getXPath());
        } else {
            LOGGER.debug("(" + info + ") The combo or arrow could not be located.");
        }
        return false;
    }

    private WebLocator getComboEl(String value, boolean startWith, long optionRenderMillis) {
        String classList = "x-boundlist";
        WebLocator comboListElement = new WebLocator(classList).withInfoMessage(this + " -> " + classList);
        return new WebLocator(comboListElement).withText(value, startWith ? SearchType.STARTS_WITH : SearchType.EQUALS).withRenderMillis(optionRenderMillis).withInfoMessage(value);
    }

    public boolean select(String value, boolean startWith) {
        return select(value, startWith, 500);
    }

    @Override
    public boolean select(String value) {
        return select(value, false);
    }

    @Override
    public String getValue() {
        String value = null;
        if (click()) {
            WebLocator option = getComboEl(null, true, 300).withClasses("x-boundlist-selected");
            value = option.getText();
            sendKeys(Keys.ESCAPE); // to close combo
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
}
