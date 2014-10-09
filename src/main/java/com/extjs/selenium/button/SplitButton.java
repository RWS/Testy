package com.extjs.selenium.button;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SplitButton extends Button {
    private static final Logger logger = LoggerFactory.getLogger(Button.class);

    public SplitButton() {
        setClassName("SplitButton");
        setBaseCls("x-btn");
        //logger.debug(getClassName() + "() constructor");
        setVisibility(true);
    }

    public SplitButton(ExtJsComponent container) {
        this();
        setContainer(container);
    }

    public SplitButton(ExtJsComponent container, String text) {
        this(container);
        setText(text);
    }

    public boolean clickOnMenu(String option) {
        return clickOnMenu(new String[]{option});
    }

    public boolean clickOnMenu(String[] menuOptions) {
        int n = menuOptions.length;
        logger.debug("clickOnMenu : " + menuOptions[n - 1]);
        ready();
        String info = toString();
        if (isDisabled()) {
            // waiting period for some buttons to become enabled (monitor valid)
            logger.debug("Button is disabled. Waiting ...");
            Utils.sleep(1000);
        }
        if (isEnabled()) {
            logger.info("Click on button " + info);
            // TODO try to use Menu class for implementing select item
//            click();
            showMenu();
            WebLocator menu = new WebLocator("x-menu-floating");
            if (WebDriverConfig.isIE()) {
                if (menu.isVisible()) {
                    logger.info("In IE is visible");
                }
            } else {
                menu.setStyle("visibility: visible;");
            }
            menu.setInfoMessage("active menu");
            ExtJsComponent option = new ExtJsComponent(menu);
            for (int i = 0; i < n; i++) {
                option.setText(menuOptions[i]);
                if (!option.mouseOver()) {
                    return false;
                }
            }
            if (option.clickAt()) {
                logger.info("Button Select menu option : " + option.getText());
                return true;
            } else {
                logger.warn("Could not locate option '" + option.getText() + "'. Performing simple click on button : " + info);
                clickAt();
            }
        } else {
            logger.error("(" + info + ") doesn't exists or is disabled. " + getPath());
        }
        return false;
    }
}
