package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.extjs6.panel.Pagination;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.StaleElementReferenceException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class ComboBox extends Combo {

    private Pagination paginationEl = new Pagination(getBoundList()).setRenderMillis(300);

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
        waitToRender(300L);
        boolean selected;
        String info = toString();
        WebLocator option = getComboEl(value, optionRenderMillis, searchType).setVisibility(true);
        boolean trigger;
        try {
            trigger = expand();
        } catch (StaleElementReferenceException e) {
            log.debug("StaleElementReferenceException1");
            trigger = expand();
        }
        if (trigger) {
            if (pagination) {
                do {
                    if (selected = option.doClick()) {
                        break;
                    }
                } while (paginationEl.goToNextPage());
            } else {
                selected = option.doClick();
            }
            if (selected) {
                log.info("Set value(" + info + "): " + value);
                Utils.sleep(20);
                return true;
            }
            try {
                collapse();
            } catch (StaleElementReferenceException e) {
                log.debug("StaleElementReferenceException2");
            }
            log.debug("(" + info + ") The option '" + value + "' could not be located. " + option.getXPath());
        } else {
            log.debug("(" + info + ") The combo or arrow could not be located.");
        }
        return false;
    }

    public boolean doSelect(String value, long optionRenderMillis, SearchType... searchType) {
        return doSelect(value, optionRenderMillis, false, searchType);
    }

    public boolean select(String value, SearchType... searchType) {
        boolean selected = doSelect(value, 300L, false, searchType);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    public boolean select(String value, long optionRenderMillis) {
        boolean selected = doSelect(value, optionRenderMillis, false, SearchType.EQUALS);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    public boolean select(String value, boolean pagination) {
        boolean selected = doSelect(value, 300L, pagination, SearchType.EQUALS);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    public boolean select(String value, long optionRenderMillis, boolean pagination) {
        boolean selected = doSelect(value, optionRenderMillis, pagination, SearchType.EQUALS);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    public boolean select(String value, long optionRenderMillis, boolean pagination, SearchType... searchType) {
        boolean selected = doSelect(value, optionRenderMillis, pagination, searchType);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    @Override
    public boolean select(String value) {
        return select(value, SearchType.EQUALS);
    }
}