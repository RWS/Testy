package com.sdl.selenium.extjs4.window;

import com.sdl.selenium.extjs6.button.Button;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;

public class Window extends WebLocator implements XTool {

    public Window() {
        setClassName("Window");
        setBaseCls("x-window");
        WebLocator header = new WebLocator().setTag("*[contains(@class,'x-window-header') or contains(@class, '-tl')]");
        setTemplateTitle(new WebLocator(header));
//        setTemplate("title", "count(*[contains(@class,'x-window-header') or contains(@class, '-tl')]//*[text()='%s']) > 0");
    }

    public Window(String title, SearchType... searchTypes) {
        this();
        setTitle(title, searchTypes.length == 0 ? new SearchType[]{SearchType.EQUALS} : searchTypes);
    }

    @Override
    public WebLocator getView() {
        return this;
    }

    public String getTitleWindow() {
        ready();
        WebLocator locator = new WebLocator(this).setClasses("x-window-header-text");
        return locator.getText();
    }

    public boolean close() {
        boolean closed = XTool.super.close();
        if (closed) {
            Utils.sleep(50);
        }
        return closed;
    }

    public void cancel() {
        Button close = new Button(this, "Cancel");
        close.click();
    }

    public boolean maximize() {
        boolean isMaximized = isMaximized();
        boolean maximized = isMaximized || XTool.super.maximize();
        if (!isMaximized && maximized) {
            Utils.sleep(50);
        }
        return maximized;
    }

    public boolean isMaximized() {
        WebLocator maximizeTool = getToolEl("maximize");
        return !maximizeTool.isPresent();
    }
}