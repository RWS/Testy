package com.extjs.selenium.grid;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class GridPanel extends com.sdl.selenium.extjs3.grid.GridPanel {

    public GridPanel() {
    }

    public GridPanel(String cls) {
        super(cls);
    }

    public GridPanel(WebLocator container) {
        super(container);
    }

    public GridPanel(String cls, String searchColumnId) {
        super(cls, searchColumnId);
    }

    public GridPanel(WebLocator container, String searchColumnId) {
        super(container, searchColumnId);
    }

    public GridPanel(WebLocator container, String cls, String searchColumnId) {
        super(container, cls, searchColumnId);
    }
}