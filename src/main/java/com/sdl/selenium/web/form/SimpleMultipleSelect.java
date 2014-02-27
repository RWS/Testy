package com.sdl.selenium.web.form;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;

public class SimpleMultipleSelect extends SimpleComboBox {
    private static final Logger logger = Logger.getLogger(SimpleMultipleSelect.class);

    public SimpleMultipleSelect() {
        setClassName("SimpleMultipleSelect");
    }

    public SimpleMultipleSelect(WebLocator container) {
        this();
        setContainer(container);
    }

    public SimpleMultipleSelect(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    public boolean selectRows(String... values) {
        boolean select = false;
        if (ready()) {
            sendKeys(Keys.CONTROL);
            for (String value : values) {
                WebLocator el = new WebLocator(this, "//*[contains(text(),'" + value + "')]");
                select = el.click();
                if (!select) {
                    sendKeys(Keys.UP);
                    return false;
                }
            }
            sendKeys(Keys.UP);
        }
        return select;
    }

    protected String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        selector = Utils.fixPathSelector(selector);
        selector = getTag() + (selector.length() > 0 ? ("[" + selector + "]") : "");
        return selector;
    }

    @Override
    protected String afterItemPathCreated(String itemPath) {
        if (hasLabel()) {
            itemPath = getLabelPath() + "//following-sibling::" + itemPath;
        } else {
            itemPath = "//" + itemPath;
        }
        itemPath = addPositionToPath(itemPath);
        return itemPath;
    }
}
