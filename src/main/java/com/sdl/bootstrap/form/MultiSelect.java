package com.sdl.bootstrap.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class MultiSelect extends WebLocator {

    public MultiSelect() {
        setClassName("MultiSelect");
        setBaseCls("multiselect dropdown-toggle btn");
        setTag("button");
    }

    public MultiSelect(WebLocator container) {
        this();
        setContainer(container);
    }

    public MultiSelect(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    public boolean select(String... values) {
        boolean selected = false;
        if (click()) {
            WebLocator select = new WebLocator(this, "//following-sibling::*[contains(@class, 'dropdown-menu')]");
            select.ready();
            for (String val : values) {
                WebLocator el = new WebLocator(select).setTag("label").setText(val, SearchType.HTML_NODE);
                CheckBox checkBox = new CheckBox(el).setInfoMessage("check: '" + val + "'");
                selected = checkBox.click();
                if (!selected) {
                    return false;
                }
            }
            click();
        }
        return selected;
    }

    public List<String> getValueSelected() {
        List<String> list = null;
        if (click()) {
            list = new ArrayList<String>();
            WebLocator select = new WebLocator(this, "//following-sibling::*[contains(@class, 'dropdown-menu')]");
            WebLocator li = new WebLocator(select).setTag("li").setCls("active");
            WebLocator el = new WebLocator(li).setTag("label");
            el.ready();
            List<WebElement> elements = WebDriverConfig.getDriver().findElements(By.xpath(el.getPath()));
            for (WebElement element : elements) {
                String text = element.getText();
                list.add(text);
            }
            click();
        }
        return list;
    }
}