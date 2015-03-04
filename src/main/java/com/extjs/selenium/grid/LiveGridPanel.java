package com.extjs.selenium.grid;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class LiveGridPanel extends com.sdl.selenium.extjs3.grid.LiveGridPanel {

    public LiveGridPanel() {
    }

    public LiveGridPanel(String cls) {
        super(cls);
    }

    public LiveGridPanel(WebLocator container) {
        super(container);
    }

    public LiveGridPanel(String cls, String searchColumnId) {
        super(cls, searchColumnId);
    }

    public LiveGridPanel(WebLocator container, String searchColumnId) {
        super(container, searchColumnId);
    }

    public LiveGridPanel(WebLocator container, String cls, String searchColumnId) {
        super(container, cls, searchColumnId);
    }
}
