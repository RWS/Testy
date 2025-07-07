package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.Go;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.extjs6.panel.Pagination;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.RetryUtils;
import com.sdl.selenium.web.utils.Utils;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@Getter
public class ComboBox extends Combo {
    private static final Logger log = LogManager.getLogger(ComboBox.class);
    private final Pagination paginationEl = new Pagination(getBoundList()).setRender(Duration.ofMillis(300));

    /**
     * Default constructor for ComboBox. Sets up the class name, base class, and tag for the input element.
     */
    public ComboBox() {
        setClassName("ComboBox");
        setBaseCls("x-form-text");
        setTag("input");
    }

    /**
     * Constructor for ComboBox with a container.
     *
     * @param container the WebLocator container in which the ComboBox is located
     */
    public ComboBox(WebLocator container) {
        this();
        setContainer(container);
    }

    /**
     * Constructor for ComboBox with a container, label, and search types.
     *
     * @param container   the WebLocator container in which the ComboBox is located
     * @param label       the label of the ComboBox
     * @param searchTypes the search strategies to use for finding the label
     */
    public ComboBox(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.DEEP_CHILD_NODE_OR_SELF};
        } else {
            List<SearchType> types = new ArrayList<>(Arrays.asList(searchTypes));
            types.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
            searchTypes = types.toArray(new SearchType[0]);
        }
        setLabel(label, searchTypes);
    }

    /**
     * Selects the specified value in the combo box, with a timeout in milliseconds and optional pagination.
     *
     * @param value              the value to select
     * @param optionRenderMillis the maximum time to wait for the option to render, in milliseconds
     * @param pagination         whether to use pagination when searching for the value
     * @param searchType         the search strategy to use
     * @return true if the value was successfully selected, false otherwise
     */
    public boolean doSelect(String value, long optionRenderMillis, boolean pagination, SearchType... searchType) {
        return doSelect(value, Duration.ofMillis(optionRenderMillis), pagination, searchType);
    }

    /**
     * Selects the specified value in the combo box, with a timeout and optional pagination.
     *
     * @param value      the value to select
     * @param duration   the maximum time to wait for the option to render
     * @param pagination whether to use pagination when searching for the value
     * @param searchType the search strategy to use
     * @return true if the value was successfully selected, false otherwise
     */
    public boolean doSelect(String value, Duration duration, boolean pagination, SearchType... searchType) {
        if (value.equals(getValue())) {
            return true;
        }
        boolean selected;
        String info = toString();
        WebLocator option = getComboEl(value, duration, searchType).setVisibility(true);
        boolean trigger = expand();
        if (trigger) {
            if (pagination) {
                do {
                    selected = option.doClick();
                    if (selected) {
                        break;
                    }
                } while (getPaginationEl().goToNextPage());
            } else {
                selected = RetryUtils.retry(2, () -> {
                    boolean doClick = option.doClick();
                    return doClick && !option.ready(Duration.ofMillis(200));
                });
                if (!selected && option.isPresent()) {
//                    WebLocatorUtils.doExecuteScript("arguments[0].scrollIntoViewIfNeeded(false);", option.getWebElement());
                    option.scrollIntoView(Go.NEAREST);
                    selected = option.doClick();
                }
            }
            if (selected) {
                log.info("Set value({}): {}", info, value);
                Utils.sleep(20);
                return true;
            }
            collapse();
            log.debug("({}) The option '{}' could not be located. {}", info, value, option.getXPath());
        } else {
            log.debug("({}) The combo or arrow could not be located.", info);
        }
        return false;
    }

    /**
     * Scrolls the combo box list up by 50 pixels.
     *
     * @param id the id of the ExtJS component
     * @return true if the scroll position increased (scrolled up), false otherwise
     *
     * <p>Example usage:</p>
     * <pre>
     *     comboBox.scrollBack("boundlist-1234");
     * </pre>
     */
    public boolean scrollBack(String id) {
        String script = "return (function (c) {var top = c.scrollable._scrollElement.dom.scrollTop;c.scrollBy(0,-50);var topTemp = c.scrollable._scrollElement.dom.scrollTop;return top < topTemp;})(window.Ext.getCmp('" + id + "'))";
        return (Boolean) WebLocatorUtils.doExecuteScript(script);
    }

    /**
     * Scrolls the combo box list down by 50 pixels.
     *
     * @param id the id of the ExtJS component
     * @return true if the scroll position reached the bottom, false otherwise
     *
     * <p>Example usage:</p>
     * <pre>
     *     comboBox.scrollDown("boundlist-1234");
     * </pre>
     */
    public boolean scrollDown(String id) {
        String script = "return (function (c) {var top = c.scrollable._scrollElement.dom.scrollTop;c.scrollBy(0,50);return Math.round(top) >= c.scrollable.getMaxPosition().y;})(window.Ext.getCmp('" + id + "'))";
        return (Boolean) WebLocatorUtils.doExecuteScript(script);
    }

    /**
     * Selects the specified value in the combo box with a timeout and search type(s).
     *
     * @param value      the value to select
     * @param duration   the maximum time to wait for the option to render
     * @param searchType the search strategy to use
     * @return true if the value was successfully selected, false otherwise
     */
    public boolean doSelect(String value, Duration duration, SearchType... searchType) {
        return doSelect(value, duration, false, searchType);
    }

    /**
     * Selects the specified value in the combo box using the provided search type(s) and a default timeout of 300ms.
     *
     * @param value      the value to select
     * @param searchType the search strategy to use
     * @return true if the value was successfully selected, false otherwise
     */
    public boolean select(String value, SearchType... searchType) {
        return select(value, Duration.ofMillis(300), searchType);
    }

    /**
     * Selects the specified value in the combo box using a timeout in milliseconds and the default EQUALS search type.
     *
     * @param value              the value to select
     * @param optionRenderMillis the maximum time to wait for the option to render, in milliseconds
     * @return true if the value was successfully selected, false otherwise
     * @deprecated Use {@link #select(String, Duration)} instead.
     */
    @Deprecated
    public boolean select(String value, long optionRenderMillis) {
        return select(value, Duration.ofMillis(optionRenderMillis));
    }

    /**
     * Selects the specified value in the combo box using a timeout and the default EQUALS search type.
     *
     * @param value    the value to select
     * @param duration the maximum time to wait for the option to render
     * @return true if the value was successfully selected, false otherwise
     */
    public boolean select(String value, Duration duration) {
        return select(value, duration, false, SearchType.EQUALS);
    }

    /**
     * Selects the specified value in the combo box using the default timeout (300ms) and optional pagination.
     *
     * @param value      the value to select
     * @param pagination whether to use pagination when searching for the value
     * @return true if the value was successfully selected, false otherwise
     */
    public boolean select(String value, boolean pagination) {
        return select(value, Duration.ofMillis(300), pagination);
    }

    /**
     * Selects the specified value in the combo box using a timeout and optional pagination.
     *
     * @param value      the value to select
     * @param duration   the maximum time to wait for the option to render
     * @param pagination whether to use pagination when searching for the value
     * @return true if the value was successfully selected, false otherwise
     */
    public boolean select(String value, Duration duration, boolean pagination) {
        boolean selected = doSelect(value, duration, pagination, SearchType.EQUALS);
        assertThat("Could not selected value on: " + this, selected);
        return selected;
    }

    /**
     * Selects the specified value in the combo box using a timeout and search type(s).
     *
     * @param value      the value to select
     * @param duration   the maximum time to wait for the option to render
     * @param searchType the search strategy to use
     * @return true if the value was successfully selected, false otherwise
     */
    public boolean select(String value, Duration duration, SearchType... searchType) {
        return select(value, duration, false, searchType);
    }

    /**
     * Selects the specified value in the combo box using a timeout, optional pagination, and search type(s).
     *
     * @param value      the value to select
     * @param duration   the maximum time to wait for the option to render
     * @param pagination whether to use pagination when searching for the value
     * @param searchType the search strategy to use
     * @return true if the value was successfully selected, false otherwise
     *
     * <p>Example usage:</p>
     * <pre>
     *     comboBox.select("Option 1", Duration.ofMillis(500), true, SearchType.EQUALS);
     * </pre>
     */
    public boolean select(String value, Duration duration, boolean pagination, SearchType... searchType) {
        boolean selected = doSelect(value, duration, pagination, searchType);
        assertThat("Could not selected value on: " + this, selected);
        return selected;
    }

    /**
     * Selects the icon element associated with a value in the combo box.
     *
     * @param value     the value to select
     * @param iconClass the CSS class of the icon
     * @param duration  the maximum time to wait for the option to render
     * @param searchType the search strategy to use
     * @return true if the icon was successfully clicked, false otherwise
     *
     * <p>Example usage:</p>
     * <pre>
     *     comboBox.doSelectIcon("Option 1", "icon-delete", Duration.ofMillis(300), SearchType.EQUALS);
     * </pre>
     */
    public boolean doSelectIcon(String value, String iconClass, Duration duration, SearchType... searchType) {
        boolean selected;
        String info = toString();
        WebLocator option = getComboEl(value, duration, searchType).setVisibility(true);
        WebLocator iconEl = new WebLocator(option).setTag("i").setClasses(iconClass);
        if (expand()) {
            selected = iconEl.doClick();
            if (!selected && iconEl.isPresent()) {
                String id = getBoundList().getAttributeId();
                scrollBack(id);
                selected = iconEl.doClick();
            }
            if (selected) {
                log.info("Set value({}): {}", info, value);
                Utils.sleep(20);
                return true;
            }
            collapse();
            log.debug("({}) The option '{}' and '{}' cls could not be located. {}", info, iconClass, value, option.getXPath());
        } else {
            log.debug("({}) The combo or arrow could not be located.", info);
        }
        return false;
    }
}