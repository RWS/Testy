package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class ToggleButton extends Button {

    public ToggleButton() {
        super();
        setClassName("ToggleButton");
    }

    public ToggleButton(WebLocator container) {
        super(container);
    }

    public ToggleButton(WebLocator container, String text, SearchType... searchTypes) {
        super(container, text, searchTypes);
    }

    public boolean press(boolean press){
        return press == isPressed() || click() && (press == isPressed());
    }

    public boolean isPressed(){
        String aClass = getAttributeClass();
        return aClass != null && aClass.contains("x-btn-pressed");
    }
}