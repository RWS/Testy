package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICombo;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
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

    public SelectPicker(By... bys) {
        getPathBuilder().defaults(By.baseCls("btn dropdown-toggle"), By.tag("button")).init(bys);
    }

    public SelectPicker(WebLocator container) {
        this(By.container(container));
    }

    public SelectPicker(WebLocator container, String label) {
        this(By.container(container), By.label(label));
    }

    @Override
    public boolean select(String value) {
        if (click()) {
            WebLocator select = new WebLocator(By.container(this), By.xpath("//following-sibling::*[contains(@class, 'dropdown-menu')]//span[text()='" + value + "']"),
                    By.infoMessage("select: '" + value + "'"));
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

    public boolean isDisabled() {
        String cls = getAttributeClass();
        return (cls != null && cls.contains("disabled")) || getAttribute("disabled") != null;
    }
}