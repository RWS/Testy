package com.sdl.selenium.extjs6.button;

import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

/**
 * ExtJS6 specific button component.
 *
 * <p>This locator targets ExtJS6 button markup (usually rendered as {@code <a>} with
 * {@code x-btn} classes) and extends generic button behavior with ExtJS-specific actions
 * such as opening button menus via component id.</p>
 *
 * <p>Example:</p>
 * <pre>{@code
 * Button save = new Button().setText("Save");
 * save.click();
 * }</pre>
 */
public class Button extends com.sdl.selenium.web.button.Button {

    /**
     * Creates an ExtJS6 button locator with default ExtJS button settings.
     *
     * <p>Defaults:</p>
     * <ul>
     *   <li>base class: {@code x-btn}</li>
     *   <li>tag: {@code a}</li>
     *   <li>text search strategy includes deep child/self matching</li>
     * </ul>
     */
    public Button() {
        setClassName("Button");
        setBaseCls("x-btn");
        setTag("a");
        getPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
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
     * Creates a scoped button locator and applies a text filter.
     *
     * <p>If no {@code searchTypes} are provided, {@link SearchType#EQUALS} is used.</p>
     *
     * @param container parent locator used as search scope
     * @param text button text to match
     * @param searchTypes optional text matching strategies
     */
    public Button(WebLocator container, String text, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.EQUALS};
        }
        setText(text, searchTypes);
    }

    /**
     * Opens the ExtJS menu associated with this button.
     *
     * <p>The method uses {@code Ext.getCmp(id)} and calls {@code showMenu()} on the client side.
     * It returns {@code false} when the button has no id or the component cannot be resolved.</p>
     *
     * @return {@code true} if menu is opened, otherwise {@code false}
     */
    public boolean showMenu() {
        final String id = getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function(){var b = Ext.getCmp('" + id + "'); if(b) {b.showMenu(); return true;} return false;})()";
            Object object = WebLocatorUtils.doExecuteScript(script);
            return (Boolean) object;
        }
        return false;
    }

    /**
     * Checks if the ExtJS button is enabled by class marker.
     *
     * @return {@code true} when button class does not contain {@code x-btn-disabled}
     */
    public boolean isEnabled() {
        String cls = getAttributeClass();
        return cls != null && !cls.contains("x-btn-disabled");
    }

    /**
     * Clicks the button after logging disabled-state information.
     *
     * @return click result from parent implementation
     */
    public boolean click() {
        logIfButtonIsDisabled();
        return super.click();
    }

    /**
     * Clicks the button with optional logging after disabled-state check.
     *
     * @param showLog whether to include click log output
     * @return click result from parent implementation
     */
    public boolean doClick(boolean showLog) {
        logIfButtonIsDisabled();
        return super.doClick(showLog);
    }

    /**
     * Returns a readable representation of the current locator path.
     */
    public String toString() {
        return getPathBuilder().itemToString();
    }
}