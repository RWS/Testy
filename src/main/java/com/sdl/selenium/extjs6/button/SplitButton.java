package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.extjs6.menu.Menu;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * See split button examples <a href="http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#split-buttons">here</a>
 */
public class SplitButton extends Button {
    private static final Logger LOGGER = LoggerFactory.getLogger(SplitButton.class);

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

    public SplitButton(WebLocator container, String text) {
        this(container);
        setText(text);
    }

    public boolean clickOnMenu(String option) {
        return clickOnMenu(new String[]{option});
    }

    public boolean clickOnMenu(String[] menuOptions) {
        int n = menuOptions.length;
        assertReady();
        LOGGER.debug("clickOnMenu : " + menuOptions[n - 1]);
        boolean selected = true;
        Menu menu = new Menu();
        if (menu.showMenu(this)) {
            for (String val : menuOptions) {
                menu.clickOnMenu(val);
            }
        } else {
            LOGGER.debug("(" + toString() + ") The element arrow could not be located.");
            selected = false;
        }
        return selected;
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
