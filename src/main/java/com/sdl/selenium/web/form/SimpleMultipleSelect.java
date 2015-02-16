package com.sdl.selenium.web.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SimpleMultipleSelect extends SimpleComboBox {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMultipleSelect.class);

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

    /**
     *
     * @return if return null, then component is not ready
     */
    public List<String> getValues() {
        List<String> list = null;
        if (ready()) {
            list = new ArrayList<String>();
            List<WebElement> elements = WebDriverConfig.getDriver().findElements(By.xpath(new WebLocator(this).getPath()));
            for (int i = 0; i < elements.size(); i++) {
                String text = elements.get(i).getText();
                LOGGER.debug(text);
                list.add(text);
            }
        }
        return list;
    }
}
