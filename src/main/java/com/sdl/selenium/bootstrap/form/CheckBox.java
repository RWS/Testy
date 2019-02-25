package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICheck;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
 * <p>Example:</p>
 * <pre>{@code
 * <label class="checkbox"><input type="checkbox" value="">
 * Stop the process?
 * </label>
 * }</pre>
 * <p>In Java write this:</p>
 * <pre>{@code
 * CheckBox checkBox = new CheckBox();
 * checkBox.click();
 * }</pre>
 */
public class CheckBox extends WebLocator implements ICheck {

    public CheckBox() {
        setClassName("CheckBox");
        setTag("input");
        setType("checkbox");
    }

    public CheckBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public CheckBox(WebLocator container, String label) {
        this(container);
        setLabel(label, SearchType.CONTAINS);
        setLabelPosition("//");
    }

    public CheckBox(String boxLabel, WebLocator container) {
        this(container);
        setLabel(boxLabel);
    }

    @Override
    public boolean isSelected() {
        return isElementPresent() && executor().isSelected(this);
    }

    @Override
    public boolean isEnabled() {
        String cls = getAttributeClass();
        return (cls != null && !cls.contains("disabled")) || getAttribute("disabled") == null;
    }
}