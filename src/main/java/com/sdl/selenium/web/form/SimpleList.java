package com.sdl.selenium.web.form;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

public class SimpleList extends WebLocator implements ICombo, IWebLocator {
    private static final Logger logger = Logger.getLogger(SimpleList.class);

    public SimpleList() {
        setClassName("SimpleList");
        setTag("select");
    }

    public SimpleList(WebLocator container) {
        this();
        setContainer(container);
    }

    public SimpleList(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    @Override
    public boolean select(String value) {
        boolean selected = false;
        if (ready()) {
            new Select(currentElement).selectByVisibleText(value);
            selected = true;
        }
        if (selected) {
            logger.info("Set value(" + this + "): " + value);
        }
        return selected;
    }

    @Override
    public boolean setValue(String value) {
        return select(value);
    }

    @Override
    public String getValue() {
        String value = this.getAttribute("value");
        return new WebLocator(this, "//option[contains(@value, '" + value + "')]").getHtmlText();
    }

    public boolean selectRows(String[] values) {
        boolean select = false;
        if (ready()) {
            sendKeys(Keys.CONTROL, Keys.DOWN);
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
