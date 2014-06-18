package com.extjs.selenium.button;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.*;
import com.sdl.selenium.web.button.IButton;
import com.sdl.selenium.web.utils.Utils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;

public class Button extends ExtJsComponent implements IButton {
    private static final Logger LOGGER = Logger.getLogger(Button.class);

    public String getIconCls() {
        return iconCls;
    }

    public <T extends Button> T setIconCls(final String iconCls) {
        this.iconCls = iconCls;
        getPathBuilder().setElPathSuffix("iconCls", "count(.//*[contains(@class, '" + iconCls + "')]) > 0");
        return (T) this;
    }

    private String iconCls;

    /*public static void main(String[] args) {
        LOGGER.debug(new Button(By.text("OK"), By.baseCls("s")).setIconCls("dd").getPath());
        LOGGER.debug(new Button().getPath());
    }*/

    private Button(PathBuilder pathBuilder) {
        super(pathBuilder);
        getPathBuilder().defaults(By.className("Button"), By.baseCls("x-btn"), By.tag("table"), By.visibility(true));
    }

    public Button(By... bys) {
        this(new PathBuilder(bys) {
            @Override
            public String getItemPath(boolean disabled) {
                // TODO create iconCls & iconPath for buttons with no text
                String selector = getBasePathSelector();
                if (!disabled) {
                    selector += " and not(contains(@class, 'x-item-disabled'))";
                }
                return "//" + getTag() + "[" + selector + "]";
            }

            protected String getBasePathSelector() {
                String selector = super.getBasePathSelector();

                if (isVisibility()) {
                    selector += " and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0";
                }
                return selector.length() == 0 ? null : selector;
            }
        });

        //defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE);
    }

    /**
     * @param cls css class
     * @deprecated
     */
    public Button(String cls) {
        this(By.classes(cls),By.searchType(SearchType.DEEP_CHILD_NODE));
    }

    /**
     * @param container parent
     */
    public Button(WebLocator container, By ...bys) {
        this(bys);
        getPathBuilder().setContainer(container);
    }

    public Button(WebLocator container, String text) {
        this(container, By.text(text, SearchType.EQUALS, SearchType.DEEP_CHILD_NODE));
    }

    // Methods

    /*@Override
    protected String getItemPathText() {
        String selector = hasText() ? super.getItemPathText() : "";
        if (hasIconCls()) {
            selector += (selector.length() > 0 ? " and " : "") + "count(./*//*[contains(@class, '" + getIconCls() + "')]) > 0";
        }
        return selector.length() == 0 ? null : selector;
    }*/

    /*@Override
    public String getItemPath(boolean disabled) {
        // TODO create iconCls & iconPath for buttons with no text
        String selector = getBasePathSelector();
        if (!disabled) {
            selector += " and not(contains(@class, 'x-item-disabled'))";
        }
        return "//" + getTag() + "[" + selector + "]";
    }*/

    @Override
    public boolean click() {
        // to scroll to this element (if element is not visible)
        WebLocator buttonEl = new WebLocator(this, "//button").setInfoMessage(this.toString() + "//button");
        // TODO try to click on button that has mask - with first solution is not saying that has mask
        //ExtJsComponent buttonEl = new ExtJsComponent(this, "//button").setInfoMessage(this + "//button");
        buttonEl.setRenderMillis(getRenderMillis());
        boolean buttonExist = true;
        if (WebDriverConfig.hasWebDriver()) {
            buttonEl.sendKeys(Keys.TAB);
            buttonExist = buttonEl.currentElement != null;
        } else {
            buttonEl.focus();
        }
        boolean clicked = buttonExist && isElementPresent() && super.doClick();
        if (clicked) {
            Utils.sleep(50);
        } else {
            LOGGER.error("(" + toString() + ") doesn't exists or is disabled. " + getPath());
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
            String script = "return (function(){var b = Ext.getCmp('" + id + "'); if(b) {b.handler.call(b.scope || b, b); return true;} return false;})()";
//        LOGGER.debug("clickWithExtJS: "+ script);
            Object object = WebLocatorUtils.doExecuteScript(script);
            LOGGER.debug("clickWithExtJS result: " + object);
            return (Boolean) object;
        }
        LOGGER.debug("id is: " + id);
        return false;
    }

    /**
     * @return true or false
     */
    public boolean isDisabled() {
        return new WebLocator(null, getPath(true)).exists();
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
            String script = "Ext.getCmp('" + id + "').showMenu()";
            Boolean showMenu = (Boolean) WebLocatorUtils.doExecuteScript(script);
            Utils.sleep(200);
            return showMenu;
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
            WebLocator menu = new WebLocator("x-menu-floating");
            if (WebDriverConfig.isIE()) {
                // menu.isVisible is not considered but is executed and is just consuming time.
//                if(menu.isVisible()){
//                    LOGGER.info("In IE is visible");
//                }
            } else {
                menu.setStyle("visibility: visible;");
            }
            menu.setInfoMessage("active menu");
            ExtJsComponent option = new ExtJsComponent(menu);
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