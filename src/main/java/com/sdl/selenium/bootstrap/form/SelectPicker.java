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
 * SelectPicker selectPicker = new SelectPicker().setLabel("Tech:");
 * selectPicker.select("Manual");
 * }</pre>
 */
public class SelectPicker extends WebLocator implements ICombo {

    public SelectPicker() {
        setClassName("SelectPicker");
        setBaseCls("dropdown-toggle");
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
        click();
        doSelect(value);
        return true;
    }

    protected void doSelect(String value) {
        WebLocator group = new WebLocator().setClasses("btn-group", "open");
        WebLocator select = new WebLocator(group).setText(value).setInfoMessage("select: '" + value + "'");
        select.click();
    }

    @Override
    public String getValue() {
        return getText().trim();
    }

    @Override
    public boolean expand() {
        return false;
    }

    @Override
    public boolean collapse() {
        return false;
    }

    public boolean isDisabled() {
        String cls = getAttributeClass();
        return (cls != null && cls.contains("disabled")) || getAttribute("disabled") != null;
    }

    public boolean isEnabled() {
        String cls = getAttributeClass();
        return (cls != null && !cls.contains("disabled")) || getAttribute("disabled") == null;
    }
}