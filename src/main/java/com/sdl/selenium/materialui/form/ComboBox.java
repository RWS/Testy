package com.sdl.selenium.materialui.form;

import com.sdl.selenium.extjs6.form.Combo;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.Selectable;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ComboBox extends Combo implements Selectable {

    public ComboBox() {
        setClassName("ComboBox");
        setBaseCls("MuiSelect-select");
    }

    public ComboBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public ComboBox(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.EQUALS};
        }
        setLabel(label, searchTypes);
    }

    @Override
    public boolean select(String value) {
        if (getValue().equals(value)) {
            return true;
        }
        expand();
        WebLocator option = getComboEl(value, Duration.ofMillis(300));
        return option.click();
    }

    @Override
    public String getValue() {
        return getText();
    }

    @Override
    public List<String> getAllValues() {
        expand();
        WebLocator comboList = new WebLocator(getBoundList()).setClasses("MuiMenu-list").setVisibility(true);
        WebLocator item = new WebLocator(comboList).setTag("li").setClasses("MuiMenuItem-root");
        int size = item.size();
        List<String> result = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            item.setResultIdx(i);
            String text = item.getText();
            result.add(text);
        }
        collapse();
        return result;
    }

    @Override
    public boolean expand() {
        WebLocator svgIcon = new WebLocator(this).setClasses("MuiSvgIcon-root").setExcludeClasses("MuiSelect-iconOpen").setLocalName("svg").setRoot("/following-sibling::");
        Actions builder = new Actions(WebDriverConfig.getDriver());
        builder.click(svgIcon.getWebElement()).build().perform();
        return !svgIcon.isPresent();
    }

    public boolean collapse() {
        WebLocator svgIcon = new WebLocator(this).setClasses("MuiSvgIcon-root", "MuiSelect-iconOpen").setLocalName("svg").setRoot("/following-sibling::");
        Actions builder = new Actions(WebDriverConfig.getDriver());
        builder.click(svgIcon.getWebElement()).build().perform();
        return !svgIcon.isPresent();
    }

    public WebLocator getBoundList() {
        return new WebLocator().setAttribute("id", "menu-", SearchType.CONTAINS);
    }

    protected <T extends WebLocator> T getComboEl(String value, Duration duration, SearchType... searchType) {
        return new WebLocator(getBoundList()).setTag("li").setText(value, searchType).setRender(duration).setInfoMessage(value);
    }
}
