package com.sdl.selenium.extjs4.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICombo;
import com.sdl.selenium.web.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;

import java.util.List;

@Slf4j
public class CustomComboBox extends TextField implements ICombo {

    public CustomComboBox() {
        setClassName("CustomComboBox");
    }

    public CustomComboBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public CustomComboBox(WebLocator container, String label) {
        this(container);
        setLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public String getTriggerPath(String icon) {
        return "/parent::*/parent::*//*[contains(@class,'x-form-trigger-" + icon + "')]";
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
                log.info("Set value(" + info + "): " + value);
                Utils.sleep(20);
                return true;
            } else {
                sendKeys(Keys.ESCAPE); // to close combo
            }
            log.debug("(" + info + ") The option '" + value + "' could not be located. " + option.getXPath());
        } else {
            log.debug("(" + info + ") The combo or arrow could not be located.");
        }
        return false;
    }

    private WebLocator getComboEl(String value, boolean startWith, long optionRenderMillis) {
        String classList = "x-boundlist";
        WebLocator comboListElement = new WebLocator(classList).setInfoMessage(this + " -> " + classList);
        return new WebLocator(comboListElement).setText(value, startWith ? SearchType.STARTS_WITH : SearchType.EQUALS).setRenderMillis(optionRenderMillis).setInfoMessage(value);
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
            WebLocator option = getComboEl(null, true, 300).setClasses("x-boundlist-selected");
            value = option.getText();
            sendKeys(Keys.ESCAPE); // to close combo
        } else {
            log.debug("(" + this + ") The combo or arrow could not be located.");
        }
        return value;
    }

    @Override
    public List<String> getAllValues() {
        return null;
    }

    @Override
    public boolean expand() {
        return super.clickIcon("more");
    }

    @Override
    public boolean collapse() {
        return super.clickIcon("more");
    }
}