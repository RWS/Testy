package com.sdl.selenium.web.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MultipleSelect extends ComboBox {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultipleSelect.class);

    public MultipleSelect() {
        setClassName("MultipleSelect");
    }

    public MultipleSelect(WebLocator container) {
        this();
        setContainer(container);
    }

    public MultipleSelect(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    public boolean selectRows(String... values) {
        return selectRows(SearchType.EQUALS, values);
    }


    public boolean selectRows(SearchType searchType, String... values) {
        boolean select = false;
        if (ready()) {
            for (String value : values) {
                WebLocator el = new WebLocator(this).setText(value, searchType);
                select = el.click();
            }
        }
        return select;
    }

    /**
     *
     * @return if return null, then component is not ready
     */
    public List<String> getValues() {
        List<String> list = null;
        if (ready()) {
            list = new ArrayList<>();
            for (WebElement element : findElements()) {
                list.add(element.getText());
            }
        }
        return list;
    }
}
