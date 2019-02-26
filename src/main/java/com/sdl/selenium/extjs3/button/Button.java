package com.sdl.selenium.extjs3.button;

import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;

@Slf4j
public class Button extends com.sdl.selenium.web.button.Button {

    public Button() {
        setClassName("Button");
        setBaseCls("x-btn");
        setTag("table");
        setVisibility(true);
        setTemplate("enabled", "not(contains(@class, 'x-item-disabled'))");
        setTemplate("icon-cls", "count(.//*[contains(@class, '%s')]) > 0");
        getXPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE);
    }

    /**
     * @param container parent
     */
    public Button(Locator container) {
        this();
        setContainer(container);
    }

    public Button(Locator container, String text) {
        this(container);
        setText(text, SearchType.EQUALS);
    }

    // Methods
    @Override
    public boolean click() {
        // to scroll to this element (if element is not visible)
        WebLocator buttonEl = new WebLocator(this).setTag("button").setInfoMessage(getXPathBuilder().itemToString() + "//button");
        // TODO try to click on button that has mask - with first solution is not saying that has mask
        //ExtJsComponent buttonEl = new ExtJsComponent(this, "//button").setInfoMessage(this + "//button");
        buttonEl.setRenderMillis(getXPathBuilder().getRenderMillis());
        boolean buttonExist;
        buttonEl.sendKeys(Keys.TAB);
        buttonExist = buttonEl.getWebElement() != null;
        boolean clicked = buttonExist && isElementPresent() && super.doClick();
        if (clicked) {
            log.info("Click on {} ", toString());
            Utils.sleep(50);
        } else {
            log.error("({}) doesn't exists or is disabled {}.", toString(), getXPath());
        }
        return clicked;
    }

    public boolean toggle(boolean state) {
        boolean toggled = false;
        if (ready()) {
            String cls = getAttributeClass();
            boolean contains = cls.contains("x-btn-pressed");
            toggled = !(state && !contains || !state && contains) || click();
        }
        return toggled;
    }

    /**
     * TO Be used in extreme cases when simple .click is not working
     *
     * @return true or false
     */
    public boolean clickWithExtJS() {
        String id = getAttributeId();
        if (hasId(id)) {
            String script = "return (function(){var b = Ext.getCmp('" + id + "'); if(b) {b.onClick({preventDefault:function(){},button:0}); return true;} return false;})()";
            Object object = WebLocatorUtils.doExecuteScript(script);
            log.info("clickWithExtJS on {}; result: {}", toString(), object);
            return (Boolean) object;
        }
        log.debug("id is: " + id);
        return false;
    }

    /**
     * @return true or false
     */
    public boolean isDisabled() {
        WebLocator disabledLocator = new WebLocator().setElPath(getXPath());
        return disabledLocator.size() > 0;
    }

    /**
     * @param milliseconds milliseconds
     * @return true or false
     */
    public boolean waitToEnable(long milliseconds) {
        return waitToRender(milliseconds);
    }

    /**
     * @return true or false
     */
    public boolean isEnabled() {
        WebLocator disabledLocator = new WebLocator().setElPath(getXPath());
        return disabledLocator.size() > 0;
    }

    public boolean hasId(String id) {
        return !Strings.isNullOrEmpty(id);
    }

    public boolean showMenu() {
        // TODO try to find solution without runScript
        final String id = getAttributeId();
        if (hasId(id)) {
            String script = "return (function(){var b = Ext.getCmp('" + id + "'); if(b) {b.showMenu(); return true;} return false;})()";
            Object object = WebLocatorUtils.doExecuteScript(script);
            log.info("showMenu for {}; result: {}", toString(), object);
            Utils.sleep(200);
            return (Boolean) object;
        }
        log.debug("id is: " + id);
        return false;
    }

    /**
     * new String[]{"option1", "option2", "option3-click"}
     *
     * @param menuOptions new String[]{"option1", "option2", "option3-click"}
     * @return true or false
     */
    public boolean clickOnMenu(String[] menuOptions) {
        log.debug("clickOnMenu : " + menuOptions[menuOptions.length - 1]);
        if (click()) {
            String info = toString();
//            log.info("Click on button " + info);
            // TODO try to use Menu class for implementing select item
            WebLocator menu = new WebLocator("x-menu-floating");
            if (WebDriverConfig.isIE()) {
                // menu.isVisible is not considered but is executed and is just consuming time.
//                if(menu.isVisible()){
//                    log.info("In IE is visible");
//                }
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
                return true;
            } else {
                log.warn("Could not locate option '" + option.getXPathBuilder().getText() + "'. Performing simple click on button : " + info);
                doClickAt();
            }
        }
        return false;
    }
}