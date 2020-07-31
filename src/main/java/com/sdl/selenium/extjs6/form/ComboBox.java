package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.extjs6.panel.Pagination;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class ComboBox extends Combo {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ComboBox.class);
    private Pagination paginationEl = new Pagination(getBoundList()).setRender(Duration.ofMillis(300));

    public ComboBox() {
        setClassName("ComboBox");
        setBaseCls("x-form-text");
        setTag("input");
    }

    public ComboBox(WebLocator container) {
        this();
        setContainer(container);
    }

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
     * @param value              value
     * @param optionRenderMillis eg. 300ms
     * @param pagination         true | false
     * @param searchType         use {@link SearchType}
     * @return true if value was selected
     */
    public boolean doSelect(String value, long optionRenderMillis, boolean pagination, SearchType... searchType) {
        return doSelect(value, Duration.ofMillis(optionRenderMillis), pagination, searchType);
    }

    /**
     * @param value             value
     * @param duration          eg. 300ms
     * @param pagination        true | false
     * @param searchType        use {@link SearchType}
     * @return true if value was selected
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
                    if (selected = option.doClick()) {
                        break;
                    }
                } while (paginationEl.goToNextPage());
            } else {
                selected = option.doClick();
                if (!selected && option.isPresent()) {
                    WebLocatorUtils.scrollToWebLocator(option);
                    String id = getBoundList().getAttributeId();
                    scrollBack(id);
                    selected = option.doClick();
                    if (!selected && option.isPresent()) {
                        scrollDown(id);
                        selected = option.doClick();
                    }
                }
            }
            if (selected) {
                log.info("Set value(" + info + "): " + value);
                Utils.sleep(20);
                return true;
            }
            collapse();
            log.debug("(" + info + ") The option '" + value + "' could not be located. " + option.getXPath());
        } else {
            log.debug("(" + info + ") The combo or arrow could not be located.");
        }
        return false;
    }

    private boolean scrollBack(String id) {
        String script = "return (function (c) {var top = c.scrollable._scrollElement.dom.scrollTop;c.scrollBy(0,-50);var topTemp = c.scrollable._scrollElement.dom.scrollTop;return top < topTemp;})(window.Ext.getCmp('" + id + "'))";
        return (Boolean) WebLocatorUtils.doExecuteScript(script);
    }

    private boolean scrollDown(String id) {
        String script = "return (function (c) {var top = c.scrollable._scrollElement.dom.scrollTop;c.scrollBy(0,50);return Math.round(top) >= c.scrollable.getMaxPosition().y;})(window.Ext.getCmp('" + id + "'))";
        return (Boolean) WebLocatorUtils.doExecuteScript(script);
    }

    @Deprecated
    public boolean doSelect(String value, long optionRenderMillis, SearchType... searchType) {
        return doSelect(value, optionRenderMillis, false, searchType);
    }

    public boolean doSelect(String value, Duration duration, SearchType... searchType) {
        return doSelect(value, duration, false, searchType);
    }

    public boolean select(String value, SearchType... searchType) {
        boolean selected = doSelect(value, Duration.ofMillis(300), false, searchType);
        assertThat("Could not selected value on: " + this, selected);
        return selected;
    }

    @Deprecated
    public boolean select(String value, long optionRenderMillis) {
        boolean selected = doSelect(value, Duration.ofMillis(optionRenderMillis), false, SearchType.EQUALS);
        assertThat("Could not selected value on: " + this, selected);
        return selected;
    }

    public boolean select(String value, Duration duration) {
        boolean selected = doSelect(value, duration, false, SearchType.EQUALS);
        assertThat("Could not selected value on: " + this, selected);
        return selected;
    }

    public boolean select(String value, boolean pagination) {
        boolean selected = doSelect(value, Duration.ofMillis(300), pagination, SearchType.EQUALS);
        assertThat("Could not selected value on: " + this, selected);
        return selected;
    }

    @Deprecated
    public boolean select(String value, long optionRenderMillis, boolean pagination) {
        boolean selected = doSelect(value, optionRenderMillis, pagination, SearchType.EQUALS);
        assertThat("Could not selected value on: " + this, selected);
        return selected;
    }

    public boolean select(String value, Duration duration, boolean pagination) {
        boolean selected = doSelect(value, duration, pagination, SearchType.EQUALS);
        assertThat("Could not selected value on: " + this, selected);
        return selected;
    }

    @Deprecated
    public boolean select(String value, long optionRenderMillis, boolean pagination, SearchType... searchType) {
        boolean selected = doSelect(value, optionRenderMillis, pagination, searchType);
        assertThat("Could not selected value on: " + this, selected);
        return selected;
    }

    public boolean select(String value, Duration duration, boolean pagination, SearchType... searchType) {
        boolean selected = doSelect(value, duration, pagination, searchType);
        assertThat("Could not selected value on: " + this, selected);
        return selected;
    }

    @Override
    public boolean select(String value) {
        return select(value, SearchType.EQUALS);
    }

    public boolean setValue(String value) {
        return executor.setValue(this, value);
    }
}