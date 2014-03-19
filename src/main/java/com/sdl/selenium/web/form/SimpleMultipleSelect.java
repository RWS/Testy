package com.sdl.selenium.web.form;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

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
//            sendKeys(Keys.CONTROL);
            for (String value : values) {
                WebLocator el = new WebLocator(this).setText(value, SearchType.EQUALS);
                select = el.click();
                if (!select) {
                    return false;
                }
            }
        }
        return select;
    }

    public List<String> getValues() {
        List<WebElement> elements = WebLocator.getDriver().findElements(By.xpath(new WebLocator(this).getPath()));
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < elements.size(); i++) {
            String text = elements.get(i).getText();
            logger.debug(text);
            list.add(text);
        }
        return list;
    }


    protected String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        selector = Utils.fixPathSelector(selector);
        selector = getTag() + (selector.length() > 0 ? ("[" + selector + "]") : "");
        return selector;
    }
}
