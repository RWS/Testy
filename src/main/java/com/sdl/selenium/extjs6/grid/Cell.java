package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.RetryUtils;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
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

    /**
     *
     * @param columnIndex column
     * @param columnText text
     * @param searchTypes SearchType.EQUALS
     */
    public Cell(int columnIndex, String columnText, SearchType... searchTypes) {
        super(columnIndex, columnText, searchTypes);
    }

    public Cell(int columnIndex, WebLocator... iconEl) {
        super(columnIndex, iconEl);
    }

    public Cell(int columnIndex, SearchType searchType, WebLocator... iconEl) {
        super(columnIndex, searchType, iconEl);
    }

    public Cell(String header, String cellText, SearchType... searchTypes) {
        super();
        setTag(getPathBuilder().getTag() + "[count(ancestor::*/*[contains(concat(' ', @class, ' '), ' x-grid-header-ct ')]//*[contains(concat(' ', @class, ' '), ' x-column-header ') and count(*//text()[.='" + header + "']) > 0]/preceding-sibling::*[@aria-hidden='false']) + number(boolean(ancestor::*/*[contains(concat(' ', @class, ' '), ' x-grid-header-ct ')]//*[contains(concat(' ', @class, ' '), ' x-column-header ') and count(*//text()[.='" + header + "']) > 0]/preceding-sibling::*[@aria-hidden='false']))]");
        setText(cellText, searchTypes);
    }

    public Cell(WebLocator container, int columnIndex, String columnText, SearchType... searchTypes) {
        this(container, columnIndex);
        setText(columnText, searchTypes);
    }

    @Deprecated
    /**
     * @deprecated use check(boolean checked)
     */
    public void check() {
        check(true);
    }

    public boolean check(boolean checked) {
        scrollInGrid(this);
        return checked == isChecked() || (getCheckBoxCell().click() && (checked == isChecked()));
    }

    public boolean doCheck(boolean checked) {
        scrollInGrid(this);
        return checked == isChecked() || (getCheckBoxCell().doClick() && (checked == isChecked()));
    }

    @Deprecated
    /**
     * @deprecated use check(boolean checked)
     */
    public void unCheck() {
        check(false);
    }

    public Boolean isChecked() {
        String aClass = RetryUtils.retry(4, "isChecked", getCheckBoxCell()::getAttributeClass);
        return aClass != null && aClass.contains("x-grid-checkcolumn-checked");
    }

    private WebLocator getCheckBoxCell() {
        return new WebLocator(this).setBaseCls("x-grid-checkcolumn");
    }

    private void scrollInGrid(Cell cell) {
        while (!cell.waitToRender(Duration.ofMillis(100))) {
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
        WebLocator flagEl = new WebLocator(this).setTag("i").setClasses("flag");
        List<WebElement> elements = flagEl.doFindElements();
        
        if (elements == null || elements.isEmpty()) {
            return "";
        }

        List<String> languages = new ArrayList<>();
        for (WebElement el : elements) {
            String aClass = el.getDomAttribute("class");
            String lang = aClass.replace("flag", "").trim();
            languages.add(lang);
        }

        if (languages.size() == 1) {
            return languages.get(0);
        }

        return String.join(",", languages.subList(0, languages.size() - 1)) + ">" + languages.get(languages.size() - 1);
    }

    public List<String> getTargets() {
        List<String> flags = new ArrayList<>();
        WebLocator flagEl = new WebLocator(this).setTag("i").setClasses("flag");
        List<WebElement> elements = flagEl.doFindElements();
        if (elements != null && !elements.isEmpty()) {
            for (WebElement el : elements) {
                String aClass = el.getDomAttribute("class");
                String lang = aClass.replace("flag", "").trim();
                flags.add(lang);
            }
            return flags;
        }
        return null;
    }
}