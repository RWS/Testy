package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICombo;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
 * <p>Example:</p>
 * <pre>{@code
 * <label class="control-label">Tech:</label>
 *
 * <div>
 *  <select class="selectpicker">
 *      <option>Auto</option>
 *      <option>Manual</option>
 *      <option>No ADB</option>
 *  </select>
 * </div>
 * }</pre>
 * <p>In Java write this:</p>
 * <pre>{@code
 * SelectPicker selectPicker = new SelectPicker().setLabel("Tech:");
 * selectPicker.select("Manual");
 * }</pre>
 */
public class SelectPicker extends WebLocator implements ICombo {

    public SelectPicker() {
        setClassName("SelectPicker");
        setBaseCls("btn dropdown-toggle");
        setTag("button");
    }

    public SelectPicker(WebLocator container) {
        this();
        setContainer(container);
    }

    public SelectPicker(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    @Override
    public boolean select(String value) {
        if (click()) {
            WebLocator select = new WebLocator(this).setElPath("//following-sibling::*[contains(@class, 'dropdown-menu')]//span[text()='" + value + "']")
                    .setInfoMessage("select: '" + value + "'");
            return select.click();
        }
        return false;
    }

    @Override
    public String getValue() {
        return getHtmlText().trim();
    }

    @Override
    public boolean setValue(String value) {
        return select(value);
    }

    public boolean isDisabled(){
        String cls = getAttributeClass();
        return (cls != null && cls.contains("disabled")) || getAttribute("disabled") != null;
    }
}