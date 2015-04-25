package com.sdl.selenium.extjs3.button;

import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.IButton;
import com.sdl.selenium.web.utils.Utils;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Button extends ExtJsComponent implements IButton {
    private static final Logger LOGGER = LoggerFactory.getLogger(Button.class);

    public String getIconCls() {
        return iconCls;
    }

    public <T extends Button> T setIconCls(final String iconCls) {
        this.iconCls = iconCls;
        String key = "icon-cls";
        setTemplateValue(key, iconCls);
        return (T) this;
    }

    private String iconCls;

    public Button(By... bys) {
        getPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE);
        getPathBuilder().defaults(By.baseCls("x-btn"), By.tag("table"), By.text("", SearchType.EQUALS),
                By.template("visibility", "count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0"),
                By.template("icon-cls", "count(.//*[contains(@class, '%s')]) > 0"),
                By.template("enabled", "not(contains(@class, 'x-item-disabled'))"),
                By.visibility(true)).init(bys);
    }

    /**
     * @param container parent
     */
    public Button(WebLocator container) {
        this(By.container(container));
    }

    public Button(WebLocator container, String text) {
        this(By.container(container), By.text(text, SearchType.EQUALS));
    }

    @Override
    public boolean click() {
        // to scroll to this element (if element is not visible)
        WebLocator buttonEl = new WebLocator(By.container(this), By.tag("button"), By.infoMessage(this.itemToString() + "//button"));
        // TODO try to click on button that has mask - with first solution is not saying that has mask
        //ExtJsComponent buttonEl = new ExtJsComponent(this, "//button").setInfoMessage(this + "//button");
        buttonEl.setRenderMillis(getRenderMillis());
        boolean buttonExist;
        buttonEl.sendKeys(Keys.TAB);
        buttonExist = buttonEl.currentElement != null;
        boolean clicked = buttonExist && isElementPresent() && super.doClick();
        if (clicked) {
            LOGGER.info("Click on {} ", toString());
            Utils.sleep(50);
        } else {
            LOGGER.error("({}) doesn't exists or is disabled {}.", toString(), getPath());
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
            LOGGER.info("clickWithExtJS on {}; result: {}", toString(), object);
            return (Boolean) object;
        }
        LOGGER.debug("id is: " + id);
        return false;
    }

    /**
     * @return true or false
     */
    public boolean isDisabled() {
        WebLocator disabledLocator = new WebLocator().setElPath(getPath(true));
        return disabledLocator.exists();
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
        return exists();
    }

    public boolean hasId(String id) {
        return id != null && !"".equals(id);
    }

    public boolean showMenu() {
        // TODO try to find solution without runScript
        final String id = getAttributeId();
        if (hasId(id)) {
            String script = "return (function(){var b = Ext.getCmp('" + id + "'); if(b) {b.showMenu(); return true;} return false;})()";
            Object object = WebLocatorUtils.doExecuteScript(script);
            LOGGER.info("showMenu for {}; result: {}", toString(), object);
            Utils.sleep(200);
            return (Boolean) object;
        }
        LOGGER.debug("id is: " + id);
        return false;
    }

    /**
     * new String[]{"option1", "option2", "option3-click"}
     *
     * @param menuOptions new String[]{"option1", "option2", "option3-click"}
     * @return true or false
     */
    public boolean clickOnMenu(String[] menuOptions) {
        LOGGER.debug("clickOnMenu : " + menuOptions[menuOptions.length - 1]);
        if (click()) {
            String info = toString();
//            LOGGER.info("Click on button " + info);
            // TODO try to use Menu class for implementing select item
            WebLocator menu = new WebLocator(By.classes("x-menu-floating"));
            if (WebDriverConfig.isIE()) {
                // menu.isVisible is not considered but is executed and is just consuming time.
//                if(menu.isVisible()){
//                    LOGGER.info("In IE is visible");
//                }
            } else {
                menu.setStyle("visibility: visible;");
            }
            menu.setInfoMessage("active menu");
            ExtJsComponent option = new ExtJsComponent(By.container(menu));
            for (String menuOption : menuOptions) {
                option.setText(menuOption);
                if (!option.mouseOver()) {
                    return false;
                }
            }
            if (option.clickAt()) {
                return true;
            } else {
                LOGGER.warn("Could not locate option '" + option.getText() + "'. Performing simple click on button : " + info);
                doClickAt();
            }
        }
        return false;
    }


    /**
     * @param option new String[]{"option1", "option2", "option3-click"}
     * @return true or false
     */
    public boolean clickOnMenu(String option) {
        return clickOnMenu(new String[]{option});
    }
}