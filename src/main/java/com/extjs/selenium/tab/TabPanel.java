package com.extjs.selenium.tab;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TabPanel extends ExtJsComponent {
    private static final Logger logger = Logger.getLogger(TabPanel.class);

    private TabPanel() {
        setClassName("TabPanel");
        setBaseCls("x-tab-panel");
    }

    public TabPanel(String text) {
        this();
        setText(text, SearchType.EQUALS);
    }

    public TabPanel(WebLocator container, String text) {
        this(text);
        setContainer(container);
    }

    private String getTitlePath() {
        String returnPath = "";
        if (hasText()) {
            returnPath = "//*[contains(@class,'x-tab-panel-header')]//*[" + getItemPathText() + "]";
        }
        return returnPath;
    }

    /**
     * this method return the path of the main TabPanel (that contains also this Tab/Panel)
     *
     * @return the path of the main TabPanel
     */
    private String getBaseTabPanelPath() {
        String selector = getBasePath();
        if (hasText()) {
//            selector += " and count(*[contains(@class,'x-tab-panel-header')]//*[text()='" + getText() + "']) > 0"; //[viorel]
            selector += (selector.length() > 0 ? " and " : "") + "not(contains(@class, 'x-masked')) and count(*[contains(@class,'x-tab-panel-header')]//*[contains(@class, 'x-tab-strip-active')]//*[" + getItemPathText() + "]) > 0";
        }
        return "//*[" + selector + "]";
    }

    /**
     * this method return the path of only one visible div from the main TabPanel
     *
     * @param disabled disabled
     * @return the path of only one visible div from the main TabPanel
     */
    @Override
    public String getItemPath(boolean disabled) {
        String selector = getBaseTabPanelPath();
            selector += "/*/*[contains(@class, 'x-tab-panel-body')]" +  //TODO
                    "/*[not(contains(@class, 'x-hide-display'))]"; // "/" is because is first element after -body
//        }
        return selector;
    }

    /**
     * After the tab is set to active will wait 50ms to make sure tab is rendered
     *
     * @return true or false
     */
    public boolean setActive() {
        String baseTabPath = "//*[" + getBasePath() + "]";
        String titlePath = baseTabPath + getTitlePath();
        WebLocator titleElement = new WebLocator(getContainer(), titlePath).setInfoMessage(getText() + " Tab");
        logger.info("setActive : " + toString());
        boolean activated;
        try {
            activated = titleElement.click();
        } catch (ElementNotVisibleException e) {
            logger.error("setActive Exception: " + e.getMessage());
            WebLocator tabElement = new WebLocator(getContainer(), baseTabPath);
            String id = tabElement.getAttributeId();
            String path = "//*[@id='" + id + "']//*[contains(@class, 'x-tab-strip-inner')]";
            String script = "return Ext.getCmp('" + id + "').setActiveTab(" + getTabCount(getText(), path) + ");";
            logger.warn("force TabPanel setActive with js: " + script);
            WebLocatorUtils.doExecuteScript(script);
            activated = true; // TODO verify when is not executed
        }
        if(activated){
            Utils.sleep(300); // need to make sure this tab is rendered
        }
        return activated;
    }

    public int getTabCount(String nameTab, String path) {
        List<WebElement> element = WebDriverConfig.getDriver().findElements(By.xpath(path));
        int count = 0;
        for (WebElement el : element) {
            if (nameTab.equals(el.getText())) {
                logger.debug(count + " : " + el.getText());
                return count;
            }
            count++;
        }
        return -1;
    }
}
