package com.extjs.selenium;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.PathBuilder;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.apache.log4j.Logger;

public class ExtJsComponent extends WebLocator {
    private static final Logger logger = Logger.getLogger(ExtJsComponent.class);

    public ExtJsComponent(PathBuilder pathBuilder) {
        super(pathBuilder);
        getPathBuilder().defaults(By.className("ExtJsComponent"));
    }

    public ExtJsComponent(By...bys) {
        this(new PathBuilder(bys));
    }

    /**
     * @param cls css class
     */
    public ExtJsComponent(String cls) {
        this();
        setClasses(cls);
    }

    public ExtJsComponent(WebLocator container) {
        this();
        setContainer(container);
    }

    public ExtJsComponent(WebLocator container, String elPath) {
        this(container);
        setElPath(elPath);
    }

    public ExtJsComponent(String cls, WebLocator container) {
        this(container);
        setClasses(cls);
    }

    public ExtJsComponent(String text, String cls, WebLocator container) {
        this(cls, container);
        setText(text);
    }

    /**
     * Containing baseCls, class, name and style
     *
     * @return baseSelector
     */
    protected String getBasePathSelector() {
        String selector = super.getBasePathSelector();

        if (isVisibility()) {
            selector += " and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0";
        }
        // TODO use also if disabled some parents then can;t click/select some children
        // x-panel x-panel-noborder x-masked-relative x-masked  x-border-panel
        return selector.length() == 0 ? null : selector;
    }

    /**
     * @param disabled disabled
     * @return string
     */
    @Override
    public String getPath(boolean disabled) {
        String returnPath = super.getPath(disabled);

        // TODO this is ok but need test
        // TODO make more specific for WebLocators in general
        // TODO x-masked is used in hasMask
        if (disabled) {
            returnPath += "/ancestor-or-self::*[contains(@class, 'x-masked') or contains(@class, 'x-item-disabled')]";
//            returnPath = "+ [@class*='x-masked' or class=*'x-item-disabled'] " + returnPath + "+ [@class*='x-masked' or class=*'x-item-disabled']";
        }
        return returnPath;
    }

    @Override
    public boolean isVisible() {
        boolean visible = super.isVisible();
        if (visible) {
            String cls = getAttributeClass();
            if (cls != null && cls.contains("x-hide-display")) {
                visible = false;
            }
        }
        return visible;
    }

    /**
     * @return true if element has mask or some parent container has mask
     *         has mask = element contains class 'x-masked'
     */
    public boolean hasMask() {
        // to make sure mask is for this element get his ID and start from it
//        String id = getAttributeId();
        String id = getCurrentElementAttribute("id");
        WebLocator mask = getMaskElement();
        if ((id == null || id.equals("")) && getContainer() != null) {
            id = getContainer().getAttributeId();
            if(logger.isDebugEnabled()){
                logger.debug(this + " does not have ID, try to look at container ID : " + id + " > " + getContainer().getClassName());
            }
        }
        if (id != null && !id.equals("")) {
            WebLocator thisEl = new WebLocator().setId(id);
            mask.setContainer(thisEl);
        }
        boolean hasMask = mask.isElementPresent();
        if (logger.isDebugEnabled() && hasMask) {
//            logger.debug("MaskXPath=" + mask.getPath());
            logger.debug(this + " masked : " + hasMask);
        }
        return hasMask;
    }

    public WebLocator getMaskElement() {
        String maskXPathSelector = "/ancestor-or-self::*[contains(@class, 'x-masked')]";
        return new WebLocator(this, maskXPathSelector);
    }

    /**
     * Wait for the element to be activated when there is deactivation mask on top of it
     *
     * @param seconds
     */
    public boolean waitToActivate(int seconds) {
        String info = toString();
        //logger.debug("waitToActivate:" + seconds + " sec; " + info);
        int count = 0;
        boolean hasMask;
        while ((hasMask = hasMask()) && (count < seconds)) {
            count++;
            logger.info("waitToActivate:" + (seconds - count) + " seconds; " + info);
            Utils.sleep(1000);
        }
        return !hasMask;
    }
}