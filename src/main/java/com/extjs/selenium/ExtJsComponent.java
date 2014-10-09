package com.extjs.selenium;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtJsComponent extends WebLocator {
    private static final Logger logger = LoggerFactory.getLogger(ExtJsComponent.class);

    public ExtJsComponent() {
        setClassName("ExtJsComponent");
        addToTemplate("visibility", "count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0");
        setTemplate("disabled", "/ancestor-or-self::*[contains(@class, 'x-masked') or contains(@class, 'x-item-disabled')]");
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