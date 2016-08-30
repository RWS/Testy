package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.utils.config.WebDriverConfig;
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
public class MultiSelect extends SelectPicker {

    public MultiSelect() {
        super.withClassName("MultiSelect");
    }

    public MultiSelect(WebLocator container) {
        this();
        withContainer(container);
    }

    public MultiSelect(WebLocator container, String label) {
        this(container);
        withLabel(label);
    }

    @Override
    public boolean select(String value) {
        super.select(value);
        click();
        return true;
    }

    public boolean select(String... values) {
        click();
        for (String value : values) {
            super.doSelect(value);
        }
//        WebLocator shadow = new WebLocator().setClasses("dropdown-backdrop");
        click();
        return true;
    }

    public List<String> getValueSelected() {
        click();
        List<String> list = new ArrayList<>();
        WebLocator group = new WebLocator().setClasses("btn-group", "open");
        WebLocator li = new WebLocator(group).withTag("li").withCls("active");
        WebLocator label = new WebLocator(li).withTag("label");
        label.ready();
        List<WebElement> elements = WebDriverConfig.getDriver().findElements(By.xpath(label.getXPath()));
        for (WebElement element : elements) {
            String text = element.getText();
            list.add(text);
        }
        click();
        return list;
    }
}