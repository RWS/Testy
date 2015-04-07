package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.PathBuilder;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICheck;
import com.sdl.selenium.web.form.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.sdl.selenium.web.form.by.By.type;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
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
public class CheckBox extends TextField implements ICheck {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckBox.class);

    PathBuilder pathBuilder = getPathBuilder();
    public CheckBox(By...bys) {
        pathBuilder.init(bys);
        pathBuilder.defaults(type("checkbox"));
    }

    public CheckBox(WebLocator container) {
        this();
        pathBuilder.setContainer(container);
    }

    public CheckBox(WebLocator container, String label) {
        this(By.container(container), By.label(label, SearchType.CONTAINS), By.labelPosition("//"));
    }

    public CheckBox(String boxLabel, WebLocator container) {
        this(By.container(container), By.label(boxLabel));
    }

    @Override
    public boolean isSelected() {
        return isElementPresent() && executor.isSelected(this);
    }

    @Override
    public boolean isDisabled() {
        String cls = getAttributeClass();
        return (cls != null && cls.contains("disabled")) || getAttribute("disabled") != null;
    }
}