package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
 * <p>Example:</p>
 * <pre>{@code
 * <button class="btn" type="button">Save</button>
 * }</pre>
 * <p>In Java write this:</p>
 * <pre>{@code
 * Button saveButton = new Button().setText("Save");
 * saveButton.click();
 * }</pre>
 */
public class Button extends com.sdl.selenium.web.button.Button {

    public Button() {
        setClassName("Button");
        setBaseCls("btn");
        setTag("button");
        setTemplate("icon-cls", "count(.//*[contains(@class, '%s')]) > 0");
    }

    public Button(Locator container) {
        this();
        setContainer(container);
    }

    public Button(Locator container, String text) {
        this(container);
        setText(text);
        setSearchTextType(SearchType.EQUALS);
    }

    /**
     * <pre>{@code
     * <button class="btn" type="button" disabled>DisableBtn</button>
     * <button class="btn disabled" type="button">DisableBtnCls</button>
     * }</pre>
     * <p>Example:</p>
     * <pre>{@code
     * Button disableButton = new Button().setText("DisableBtn");
     * disableButton.isDisabled();
     * }</pre>
     *
     * @return true if element has attribute disabled or class disabled otherwise false
     */
    public boolean isEnabled() {
        String cls = getAttributeClass();
        return (cls != null && !cls.contains("disabled")) || getAttribute("disabled") == null;
    }
}