package com.sdl.selenium.web.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.WebElement;

/**
 * Generic web button locator.
 *
 * <p>Use this class to locate and interact with HTML button elements by text,
 * container scope, or icon class template.</p>
 *
 * <p>Examples:</p>
 * <pre>{@code
 * Button save = new Button().setText("Save");
 * Button cancelInDialog = new Button(dialogContainer, "Cancel");
 * Button iconButton = new Button().setIconCls("icon-save");
 * }</pre>
 */
public class Button extends WebLocator implements IButton {

    /**
     * Creates a button locator with default HTML button tag and icon template.
     */
    public Button() {
        setClassName("Button");
        setTag("button");
        setTemplate("icon-cls", "count(.//*[contains(concat(' ', @class, ' '), ' %s ')]) > 0");
    }

    /**
     * Creates a button locator scoped to a parent container.
     *
     * @param container parent locator used as search scope
     */
    public Button(WebLocator container) {
        this();
        setContainer(container);
    }

    /**
     * Wraps an already found Selenium element as a Testy button locator.
     *
     * @param webElement existing Selenium web element
     */
    public Button(WebElement webElement) {
        this();
        setWebElement(webElement);
    }

    /**
     * Creates a scoped button locator and filters by text.
     *
     * <p>If no search type is provided, {@link SearchType#EQUALS} is used.</p>
     *
     * @param container parent locator used as search scope
     * @param text button text to match
     * @param searchTypes optional text matching strategy
     */
    public Button(WebLocator container, String text, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.EQUALS};
        }
        setText(text, searchTypes);
    }

    private String iconCls;

    public String getIconCls() {
        return iconCls;
    }

    /**
     * Adds icon class criteria to the button locator.
     *
     * @param iconCls CSS class name expected for button icon
     * @param <T> fluent return type
     * @return this locator instance for chaining
     */
    public <T extends Button> T setIconCls(final String iconCls) {
        this.iconCls = iconCls;
        setTemplateValue("icon-cls", iconCls);
        return (T) this;
    }
}
