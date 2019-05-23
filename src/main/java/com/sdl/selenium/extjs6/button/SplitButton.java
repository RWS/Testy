package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.extjs6.menu.Menu;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * See split button examples <a href="http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#split-buttons">here</a>
 */
@Slf4j
public class SplitButton extends Button {

    /**
     * See split button examples <a href="http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#split-buttons">here</a>
     */
    public SplitButton() {
        setClassName("SplitButton");
    }

    public SplitButton(WebLocator container) {
        this();
        setContainer(container);
    }

    public SplitButton(WebLocator container, String text, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.EQUALS};
        }
        setText(text, searchTypes);
    }

    public void clickOnMenu(String... options) {
        assertReady();
        doClickOnMenu(SearchType.CONTAINS, options);
    }

    public void clickOnMenu(SearchType searchType, String... options) {
        assertReady();
        Menu menu = new Menu();
        if (menu.showMenu(this) || menu.showMenu(this)) {
            for (String option : options) {
                menu.clickOnMenu(option, searchType);
            }
        } else {
            log.debug("(" + toString() + ") The element arrow could not be located.");
        }
    }

    public boolean doClickOnMenu(SearchType searchType, String... options) {
        boolean success = true;
        Menu menu = new Menu();
        if (menu.showMenu(this) || menu.showMenu(this)) {
            for (String option : options) {
                success = success && menu.doClickOnMenu(option, searchType);
            }
        } else {
            log.debug("(" + toString() + ") The element arrow could not be located.");
        }
        return success;
    }

    public List<String> getAllMenuValues() {
        Menu menu = new Menu();
        if (!menu.showMenu(this)) {
            menu.ready();
            menu.showMenu(this);
        }
        List<String> menuValues = menu.getMenuValues();
        menu.hideMenu(this);
        return menuValues;
    }
}