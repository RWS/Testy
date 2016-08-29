package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICombo;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
 * <p>Example:</p>
 * <pre>{@code
 * <label class="control-label">Tech:</label>
 * <p/>
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
 * SelectPicker selectPicker = new SelectPicker().withLabel("Tech:");
 * selectPicker.select("Manual");
 * }</pre>
 */
public class SelectPicker extends WebLocator implements ICombo {

    public SelectPicker() {
        withClassName("SelectPicker");
        withBaseCls("dropdown-toggle");
        withTag("button");
    }

    public SelectPicker(WebLocator container) {
        this();
        withContainer(container);
    }

    public SelectPicker(WebLocator container, String label) {
        this(container);
        withLabel(label);
    }

    public SelectPicker(WebLocator container, String label, boolean isInternationalized) {
        this(container);
        withLabel(label, isInternationalized);
    }

    @Override
    public boolean select(String value) {
        click();
        doSelect(value);
        return true;
    }

    protected void doSelect(String value) {
        WebLocator group = new WebLocator().setClasses("btn-group", "open");
        WebLocator select = new WebLocator(group).setText(value).withInfoMessage("select: '" + value + "'");
        select.click();
    }

    @Override
    public String getValue() {
        return getText().trim();
    }

    public boolean isDisabled() {
        String cls = getAttributeClass();
        return (cls != null && cls.contains("disabled")) || getAttribute("disabled") != null;
    }
}