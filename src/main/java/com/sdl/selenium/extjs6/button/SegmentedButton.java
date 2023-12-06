package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SegmentedButton extends WebLocator {

    public SegmentedButton() {
        setClassName("SegmentedButton");
        setBaseCls("x-segmented-button");
    }

    public SegmentedButton(WebLocator container) {
        this();
        setContainer(container);
    }

    public List<String> getButtons() {
        List<String> buttons = new ArrayList<>();
        Button button = new Button(this).setClasses("x-segmented-button-item");
        int size = button.size();
        for (int i = 1; i <= size; i++) {
            button.setResultIdx(i);
            String text = button.getText();
            buttons.add(text);
        }
        return buttons;
    }

    public boolean press(String name) {
        ToggleButton button = new ToggleButton(this, name).setClasses("x-segmented-button-item");
        return button.press(true);
    }
}
