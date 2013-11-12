package com.extjs.selenium.form;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class FieldSet extends WebLocator {
    private static final Logger logger = Logger.getLogger(FieldSet.class);

    public FieldSet() {
        setClassName("FieldSet");
        setBaseCls("x-fieldset");
        setTag("fieldset");
    }

    public FieldSet(WebLocator container) {
        this();
        setContainer(container);
    }

    public FieldSet(WebLocator container, String text) {
        this(container);
        setText(text);
    }

    public FieldSet(WebLocator container, String cls, String text) {
        this(container, text);
        setClasses(cls);
    }

    protected String getDefaultExcludePath() {
        return " and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))";
    }

    @Override
    protected String getItemPathText(){
        String selector = "";
        if (hasText()) {
            //selector += " and count(.//*[contains(text(),'" + getText() + "')]) > 0";
            selector += " and count(.//*[normalize-space(text())='" + getText() + "']) > 0";
        }
        return selector;
    }
    /**
     * @param disabled
     * @return
     */
    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        // limit results to fieldset only
        selector += getDefaultExcludePath();
        return "//" + getTag() + "[" + selector + "]";
    }

    // methods
    public boolean isCollapsed() {
        String cls = getAttribute("class");
        return cls != null && cls.contains("x-panel-collapsed");
    }

    public boolean expand() {
        WebLocator legendElement = new WebLocator(this).setText(getText());
        boolean expanded = !isCollapsed() || legendElement.click();
        if(expanded){
            Utils.sleep(500);
        }
        return expanded;
    }
}
