package com.sdl.selenium.extjs6.grid;

import com.google.common.base.Strings;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.RetryUtils;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Cell extends com.sdl.selenium.web.table.Cell {

    public Cell() {
        super();
    }

    public Cell(WebLocator container) {
        super(container);
    }

    public Cell(WebLocator container, int columnIndex) {
        super(container, columnIndex);
    }

    public Cell(String columnText, SearchType... searchTypes) {
        super(columnText, searchTypes);
    }

    public Cell(int columnIndex, String columnText, SearchType... searchTypes) {
        super(columnIndex, columnText, searchTypes);
    }

    public Cell(String header, String cellText, SearchType... searchTypes) {
        super();
        if (!Strings.isNullOrEmpty(cellText)) {
            setTag(getPathBuilder().getTag() + "[count(ancestor::*/*[contains(concat(' ', @class, ' '), ' x-grid-header-ct ')]//*[contains(concat(' ', @class, ' '), ' x-column-header ') and count(*//text()[.='" + header + "']) > 0]/preceding-sibling::*[@aria-hidden='false']) + number(boolean(ancestor::*/*[contains(concat(' ', @class, ' '), ' x-grid-header-ct ')]//*[contains(concat(' ', @class, ' '), ' x-column-header ') and count(*//text()[.='" + header + "']) > 0]/preceding-sibling::*[@aria-hidden='false']))]");
        }
        setText(cellText, searchTypes);
    }

    public Cell(WebLocator container, int columnIndex, String columnText, SearchType... searchTypes) {
        super(container, columnIndex);
        setText(columnText, searchTypes);
    }

    public void check() {
        scrollInGrid(this);
        if (!isChecked()) {
            WebLocator checkBox = new WebLocator(this).setBaseCls("x-grid-checkcolumn");
            checkBox.click();
        }
    }

    public void unCheck() {
        scrollInGrid(this);
        if (isChecked()) {
            WebLocator checkBox = new WebLocator(this).setBaseCls("x-grid-checkcolumn");
            checkBox.click();
        }
    }

    public Boolean isChecked() {
        WebLocator checkBox = new WebLocator(this).setBaseCls("x-grid-checkcolumn");
        String attributeClass = RetryUtils.retry(4, checkBox::getAttributeClass);
        return attributeClass.contains("x-grid-checkcolumn-checked");
    }

    private void scrollInGrid(Cell cell) {
        while (!cell.waitToRender(100)) {
            Grid grid;
            try {
                grid = (Grid) getPathBuilder().getContainer();
            } catch (ClassCastException e) {
                grid = (Grid) getPathBuilder().getContainer().getPathBuilder().getContainer();
            }
            if (!grid.scrollPageDown()) {
                break;
            }
        }
    }

    public String getLanguages() {
        StringBuilder flags = new StringBuilder();
        WebLocator flagEl = new WebLocator(this).setTag("i").setClasses("flag");
        List<WebElement> elements = flagEl.doFindElements();
        if (elements != null && !elements.isEmpty()) {
            int count = 1;
            int sizeLangs = elements.size();
            for (WebElement el : elements) {
                String aClass = el.getAttribute("class");
                String lang = aClass.replace("flag", "").trim();
                flags.append(lang);
                if (count == 1) {
                    if (sizeLangs > 1) {
                        flags.append(">");
                    }
                } else {
                    if (count > 1 && count < sizeLangs) {
                        flags.append(",");
                    }
                }
                count++;
            }
            return flags.toString();
        }
        return "";
    }
}