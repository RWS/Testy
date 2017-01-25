package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TagField extends ComboBox {
    private static final Logger LOGGER = Logger.getLogger(TagField.class);

    public TagField() {
        withClassName("TagField");
        withTag("div");
    }

    public TagField(WebLocator container) {
        this();
        withContainer(container);
    }

    public TagField(WebLocator container, String label) {
        this(container);
        withLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    @Override
    public boolean select(String value) {
        return select(SearchType.EQUALS, value);
    }

    public boolean select(String... values) {
        return select(SearchType.EQUALS, values);
    }

    public boolean select(boolean holdOpen, String... values) {
        return select(SearchType.EQUALS, holdOpen, values);
    }

    public boolean select(SearchType searchType, String... values) {
        boolean selected = doSelect(searchType, 300, false, values);
        assertThat("Could not selected value on : " + this, selected, is(true));
        return selected;
    }

    public boolean select(SearchType searchType, boolean holdOpen, String... values) {
        boolean selected = doSelect(searchType, 300, holdOpen, values);
        assertThat("Could not selected value on : " + this, selected, is(true));
        return selected;
    }

    /**
     * @param values             value
     * @param searchType         use {@link SearchType}
     * @param optionRenderMillis eg. 300ms
     * @return true if value was selected
     */
    public boolean doSelect(SearchType searchType, long optionRenderMillis, boolean holdOpen, String... values) {
        boolean selected = true;
        String info = toString();
        if (holdOpen) {
            if (clickIcon("trigger")) {
                for (String value : values) {
                    WebLocator option = getComboEl(value, optionRenderMillis, searchType);
                    selected = selected && option.doClick();
                    if (selected) {
                        LOGGER.info("Set value(" + info + "): " + value);
                    } else {
                        LOGGER.debug("(" + info + ") The option '" + value + "' could not be located. " + option.getXPath());
                    }
                }
                clickIcon("trigger"); // to close combo
            }
        } else {
            for (String value : values) {
                clickIcon("trigger");
                WebLocator option = getComboEl(value, optionRenderMillis, searchType);
                selected = selected && option.doClick();
                if (selected) {
                    LOGGER.info("Set value(" + info + "): " + value);
                } else {
                    LOGGER.debug("(" + info + ") The option '" + value + "' could not be located. " + option.getXPath());
                    clickIcon("trigger");
                }
            }
        }
        return selected;
    }

    public boolean remove(String... values) {
        boolean removed = true;
        for (String value : values) {
            WebLocator item = new WebLocator(this).setClasses("x-tagfield-item").setText(value, SearchType.DEEP_CHILD_NODE_OR_SELF);
            WebLocator closeEl = new WebLocator(item).setClasses("x-tagfield-item-close");
            removed = removed && closeEl.click();
        }
        return removed;
    }

    @Override
    public String getValue() {
        return getText();
    }

    public List<String> getAllSelectedValues() {
        String[] comboValues = getText().split("\\n");
        return Arrays.asList(comboValues);
    }
}
