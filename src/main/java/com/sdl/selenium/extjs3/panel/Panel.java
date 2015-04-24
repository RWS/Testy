package com.sdl.selenium.extjs3.panel;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Panel extends ExtJsComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(Panel.class);

    private String headerBaseCls;

    public Panel(By... bys) {
        getPathBuilder().defaults(By.baseCls("x-panel"), By.pathSuffix("exclude-hide-cls", "not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))"),
                By.template("title", "count(*[contains(@class,'x-panel-header') or contains(@class, '-tl')]//*[text()='%s']) > 0")).init(bys);
    }

    public Panel(String title) {
        this(By.title(title));
    }

    public Panel(WebLocator container) {
        this(By.container(container));
    }

    public Panel(WebLocator container, String title) {
        this(By.container(container), By.title(title));
    }

    public Panel(String cls, WebLocator container, String excludeClass) {
        this(By.container(container), By.classes(cls), By.excludeClasses(excludeClass));
    }

    public String getHeaderBaseCls() {
        return headerBaseCls;
    }

    public void setHeaderBaseCls(String headerBaseCls) {
        this.headerBaseCls = headerBaseCls;
    }

    public String itemToString() {
        String info;
        if (hasTitle()) {
            info = getTitle();
        } else {
            info = super.itemToString();
        }
        return info;
    }

    public ExtJsComponent getBodyComponent() {
        return new ExtJsComponent(By.container(this), By.xpath("//*[contains(@class, 'x-panel-body')]"));
    }

    public boolean clickOnTool(String id) {
        WebLocator toolElement = new WebLocator(By.container(this), By.classes("x-tool-" + id),
                By.visibility(true), By.infoMessage("x-tool-" + id));
        return toolElement.click();
    }

    public boolean close() {
        boolean closed = clickOnTool("close");
        if (closed) {
            Utils.sleep(50);
        }
        return closed;
    }

    public boolean maximize() {
        boolean isMaximized = isMaximized();
        boolean maximized = isMaximized || clickOnTool("maximize");
        if (!isMaximized && maximized) {
            Utils.sleep(50);
        }
        return maximized;
    }

    public boolean isMaximized() {
        WebLocator maximizeTool = new WebLocator(By.container(this), By.classes("x-tool-maximize"));
        return !maximizeTool.isVisible();
    }

    public boolean restore() {
        return clickOnTool("restore");
    }

    public boolean minimize() {
        return clickOnTool("minimize");
    }

    public boolean toggle() {
        return clickOnTool("toggle");
    }
}
