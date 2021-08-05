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
        Actions actions = new Actions(WebDriverConfig.getDriver());
        boolean exists = true;
        WebLocator element = new WebLocator(this).setTag("descendant::*").setClasses("x-slider-thumb");
        if (element.ready()) {
            element.mouseOver();
            boolean done = false;
            int distanceTemp = distance;
            do {
                int value = getValue();
                log.debug("distance: {}, curentValue: {}", distance, value);
                if (value > distance) {
                    distanceTemp = -1 * (value - distance) * 2;
                } else if (value < distance) {
                    distanceTemp = (distance - value) * 2;
                } else {
                    done = true;
                }
                if (distanceTemp <= 2 && distanceTemp >= -2) {
                    distanceTemp = distanceTemp * 4;
                }
                if (!done) {
                    actions.dragAndDropBy(element.getWebElement(), distanceTemp, 0).perform();
                    element.mouseOver();
                }
            } while (!done);
        } else {
            log.warn("The slider for " + getPathBuilder().getLabel() + " has not been selected or is missing");
            exists = false;
        }
        return exists;
    }
}