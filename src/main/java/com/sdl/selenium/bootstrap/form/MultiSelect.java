package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
 * <p>Example:</p>
 * <pre>{@code
 * <label class="control-label">Source:</label>
 * <p/>
 * <div class="controls">
 *  <select id="example" class="multiselect" multiple="multiple">
 *      <option value="cheese">Cheese</option>
 *      <option value="tomatoes">Tomatoes</option>
 *      <option value="mozarella">Mozzarella</option>
 *      <option value="onions">Onions</option>
 *      <option value="carrots">Carrots</option>
 *  </select>
 * </div>
 * }</pre>
 * <p>In Java write this:</p>
 * <pre>{@code
 * MultiSelect multiSelect = new MultiSelect().withLabel("Source:");
 * multiSelect.select("Cheese", "Tomatoes");
 * }</pre>
 */
public class MultiSelect extends WebLocator {

    public MultiSelect() {
        withClassName("MultiSelect");
        withBaseCls("multiselect dropdown-toggle btn");
        withTag("button");
    }

    public MultiSelect(WebLocator container) {
        this();
        withContainer(container);
    }

    public MultiSelect(WebLocator container, String label) {
        this(container);
        withLabel(label);
    }

    public boolean select(String... values) {
        boolean selected = false;
        if (click()) {
            WebLocator select = new WebLocator(this).withElxPath("//following-sibling::*[contains(@class, 'dropdown-menu')]");
            select.ready();
            for (String val : values) {
                WebLocator el = new WebLocator(select).withTag("label").withText(val, SearchType.HTML_NODE);
                CheckBox checkBox = new CheckBox(el).withInfoMessage("check: '" + val + "'");
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
            list = new ArrayList<>();
            WebLocator select = new WebLocator(this).withElxPath("//following-sibling::*[contains(@class, 'dropdown-menu')]");
            WebLocator li = new WebLocator(select).withTag("li").withCls("active");
            WebLocator el = new WebLocator(li).withTag("label");
            el.ready();
            List<WebElement> elements = WebDriverConfig.getDriver().findElements(By.xpath(el.getXPath()));
            for (WebElement element : elements) {
                String text = element.getText();
                list.add(text);
            }
            click();
        }
        return list;
    }
}