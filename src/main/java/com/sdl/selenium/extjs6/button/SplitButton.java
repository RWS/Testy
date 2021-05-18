package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.extjs6.menu.Menu;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * See split button examples <a href="http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#split-buttons">here</a>
 */
public class SplitButton extends Button {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SplitButton.class);

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
        clickOnMenu(SearchType.CONTAINS, options);
    }

    public boolean doClickOnMenu(String... options) {
        assertReady();
        return doClickOnMenu(SearchType.CONTAINS, options);
    }

    public void clickOnMenu(SearchType searchType, String... options) {
        assertReady();
        Menu menu = new Menu();
        if (menu.showMenu(this) || menu.showMenu(this)) {
            for (String option : options) {
                menu.clickOnMenu(option, searchType);
            }
        } else {
            log.debug("(" + this + ") The element arrow could not be located.");
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
            log.debug("(" + this + ") The element arrow could not be located.");
            success = false;
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

    public List<String> getAllDisabledValues() {
        Menu menu = new Menu();
        if (!menu.showMenu(this)) {
            menu.ready();
            menu.showMenu(this);
        }
        List<Menu.Values> menuValues = menu.getMenuValuesWithStatus();
        menu.hideMenu(this);
        return menuValues.stream().map(i -> {
            if (i.isEnabled()) {
                return null;
            } else {
                return i.getName();
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<String> getAllEnabledValues() {
        Menu menu = new Menu();
        if (!menu.showMenu(this)) {
            menu.ready();
            menu.showMenu(this);
        }
        List<Menu.Values> menuValues = menu.getMenuValuesWithStatus();
        menu.hideMenu(this);
        return menuValues.stream().map(i -> {
            if (i.isEnabled()) {
                return i.getName();
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}