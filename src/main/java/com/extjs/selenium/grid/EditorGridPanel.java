package com.extjs.selenium.grid;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class EditorGridPanel extends com.sdl.selenium.extjs3.grid.EditorGridPanel {

    public EditorGridPanel() {
        super();
    }

    public EditorGridPanel(WebLocator container) {
       super(container);
    }

    public EditorGridPanel(String cls) {
        super(cls);
    }

    public EditorGridPanel(String cls, String searchColumnId) {
        super(cls, searchColumnId);
    }

    public EditorGridPanel(WebLocator container, String searchColumnId) {
        super(container, searchColumnId);
    }

    public EditorGridPanel(WebLocator container, String searchColumnId, int clicksToEdit) {
        super(container, searchColumnId, clicksToEdit);
    }

    public EditorGridPanel(WebLocator container, int clicksToEdit) {
        super(container, clicksToEdit);
    }
}