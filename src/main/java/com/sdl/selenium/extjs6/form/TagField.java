package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TagField extends ComboBox {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagField.class);

    public TagField() {
        setClassName("TagField");
        setBaseCls("x-tagfield-list");
        setTag("ul");
    }

    public TagField(WebLocator container) {
        this();
        setContainer(container);
    }

    public TagField(WebLocator container, String label) {
        this(container);
        setLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
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
        boolean selected = doSelect(searchType, 300, true, values);
        assertThat("Could not selected value on : " + this, selected, is(true));
        return selected;
    }

    public boolean select(SearchType searchType, boolean holdOpen, String... values) {
        boolean selected = doSelect(searchType, 300, holdOpen, values);
        assertThat("Could not selected value on : " + this, selected, is(true));
        return selected;
    }

    /**
     * @param searchType         use {@link SearchType}
     * @param optionRenderMillis eg. 300ms
     * @param holdOpen           true | false
     * @param values             values[]
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

    public boolean doRemove(String... values) {
        boolean removed = true;
        for (String value : values) {
            WebLocator item = new WebLocator(this).setClasses("x-tagfield-item").setText(value, SearchType.DEEP_CHILD_NODE_OR_SELF);
            WebLocator closeEl = new WebLocator(item).setClasses("x-tagfield-item-close");
            try {
                removed = removed && closeEl.doClick();
            } catch (WebDriverException e) {
                Utils.sleep(1000);
                removed = removed && closeEl.doClick();
            }
        }
        return removed;
    }

    public boolean setValue(String value) {
        assertReady(value);
        WebLocator input = new WebLocator(this).setClasses("x-tagfield-input-field ").setTag("input");
        boolean setValue = executor.setValue(input, value);
        Utils.sleep(300);
        return setValue && input.sendKeys(Keys.ENTER) != null;
    }

    @Override
    public String getValue() {
        return getText();
    }

    public List<String> getAllSelectedValues() {
        String text = getText();
        if (text != null) {
            boolean isEmpty = false;

            String[] comboValues = text.split("\\n");
            if (comboValues.length == 1) {
                isEmpty = "".equals(comboValues[0]);
            }
            return isEmpty ? new ArrayList<>() : Arrays.asList(comboValues);
        }
        return new ArrayList<>();
    }

    public WebLocator getTriggerEl(String icon) {
        return new WebLocator(this).setRoot("/").setTag("parent::*/parent::*/parent::*/*").setClasses("x-form-" + icon).setInfoMessage(this + " -> " + icon);
    }
}
