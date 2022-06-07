package com.sdl.selenium.extjs6.slider;

import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;

public class Slider extends WebLocator {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Slider.class);

    public Slider() {
        setClassName("Slider");
        setBaseCls("x-slider");
    }

    public Slider(WebLocator container) {
        this();
        setContainer(container);
    }

    public Slider(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        setLabel(label, searchTypes);
    }

    public int getValue() {
        return Integer.parseInt(getAttribute("aria-valuenow"));
    }

    public boolean move(int distance) {
        return move(distance, 0);
    }

    public boolean move(int distance, int extra) {
        Actions actions = new Actions(WebDriverConfig.getDriver());
        boolean exists = true;
        boolean isVertical = getAttributeClass().contains("x-slider-vert");
        WebLocator element = new WebLocator(this).setTag("descendant::*").setClasses("x-slider-thumb");
        if (element.ready()) {
            element.mouseOver();
            boolean done = false;
            int distanceTemp = distance;
            int timeout = 50;
            do {
                int value = getValue();
                log.info("distance: {}, currentValue: {}", distance, value);
                if (value > distance) {
                    distanceTemp = -1 * (value - distance) * 2;
                    if (isVertical) {
                        distanceTemp = distanceTemp - extra;
                    }
                } else if (value < distance) {
                    distanceTemp = (distance - value) * 2;
                    if (isVertical) {
                        distanceTemp = distanceTemp + extra;
                    }
                } else {
                    done = true;
                }
                if (distanceTemp <= 2 && distanceTemp >= -2) {
                    distanceTemp = distanceTemp * 4;
                }
                if (!done) {
                    if (isVertical) {
                        actions.dragAndDropBy(element.getWebElement(), 0, -distanceTemp).perform();
                    } else {
                        actions.dragAndDropBy(element.getWebElement(), distanceTemp, 0).perform();
                    }
                    element.mouseOver();
                }
                timeout--;
            } while (!done && timeout > 0);
        } else {
            log.warn("The slider for " + getPathBuilder().getLabel() + " has not been selected or is missing");
            exists = false;
        }
        return exists;
    }
}