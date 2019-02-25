package com.sdl.selenium.extjs3.button;

import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SplitButton extends Button {

    public SplitButton() {
        setClassName("SplitButton");
        setBaseCls("x-btn");
        setVisibility(true);
    }

    public SplitButton(Locator container) {
        this();
        setContainer(container);
    }

    public SplitButton(Locator container, String text) {
        this(container);
        setText(text);
    }

    public boolean clickOnMenu(String option) {
        return clickOnMenu(new String[]{option});
    }

    public boolean clickOnMenu(String[] menuOptions) {
        int n = menuOptions.length;
        log.debug("clickOnMenu : " + menuOptions[n - 1]);
        ready();
        String info = toString();
        if (isDisabled()) {
            // waiting period for some buttons to become enabled (monitor valid)
            log.debug("Button is disabled. Waiting ...");
            Utils.sleep(1000);
        }
        if (isEnabled()) {
            log.info("Click on button " + info);
            // TODO try to use Menu class for implementing select item
//            click();
            showMenu();
            WebLocator menu = new WebLocator("x-menu-floating");
            if (WebDriverConfig.isIE()) {
                if (menu.isVisible()) {
                    log.info("In IE is visible");
                }
            } else {
                menu.setStyle("visibility: visible;");
            }
            menu.setInfoMessage("active menu");
            WebLocator option = new WebLocator(menu);
            for (String menuOption : menuOptions) {
                option.setText(menuOption);
                if (!option.mouseOver()) {
                    return false;
                }
            }
            if (option.clickAt()) {
                log.info("Button Select menu option : " + option.getXPathBuilder().getText());
                return true;
            } else {
                log.warn("Could not locate option '" + option.getXPathBuilder().getText() + "'. Performing simple click on button : " + info);
                clickAt();
            }
        } else {
            log.error("(" + info + ") doesn't exists or is disabled. " + getXPath());
        }
        return false;
    }
}
