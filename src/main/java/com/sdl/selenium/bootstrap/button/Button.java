package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.IButton;

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
public class Button extends Locator implements IButton {

    private String iconCls;

    public String getIconCls() {
        return iconCls;
    }

    public <T extends Button> T setIconCls(final String iconCls) {
        this.iconCls = iconCls;
        setTemplateValue("icon-cls", iconCls);
        return (T) this;
    }

    public Button() {
        setClassName("Button");
        setBaseCls("btn");
        setTag("button");
        setTemplate("icon-cls", "count(.//*[contains(@class, '%s')]) > 0");
    }

    public Button(WebLocator container) {
        this();
        setContainer(container);
    }

    public Button(WebLocator container, String text) {
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

    @Override
    public boolean clickAt() {
        return false;
    }

    @Override
    public boolean doClickAt() {
        return false;
    }

    @Override
    public boolean click() {
        return false;
    }

    @Override
    public boolean doClick() {
        return false;
    }

    @Override
    public boolean doubleClickAt() {
        return false;
    }

    @Override
    public boolean doDoubleClickAt() {
        return false;
    }
}