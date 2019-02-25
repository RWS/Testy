package com.sdl.selenium.extjs6.button;

import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.Clickable;
import com.sdl.selenium.web.IActions;
import com.sdl.selenium.web.ILocator;
import com.sdl.selenium.web.Locator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AButtonNew extends Locator implements ILocator, IActions, Clickable {

    private String iconCls;

    public String getIconCls() {
        return iconCls;
    }

    public <T extends AButtonNew> T setIconCls(final String iconCls) {
        this.iconCls = iconCls;
        setTemplateValue("icon-cls", iconCls);
        return (T) this;
    }

    @Override
    public boolean clickAt() {
        return executor().clickAt(this);
    }

    @Override
    public boolean doClickAt() {
        return executor().clickAt(this);
    }

    public boolean click() {
        return executor().click(this);
    }

    @Override
    public boolean doClick() {
        return executor().click(this);
    }

    @Override
    public boolean doubleClickAt() {
        return executor().doubleClickAt(this);
    }

    @Override
    public boolean doDoubleClickAt() {
        return executor().doubleClickAt(this);
    }

    public boolean showMenu() {
        final String id = executor().getAttributeId(this);
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function(){var b = Ext.getCmp('" + id + "'); if(b) {b.showMenu(); return true;} return false;})()";
            Object object = WebLocatorUtils.doExecuteScript(script);
            return (Boolean) object;
        }
        return false;
    }

    public boolean isEnabled() {
        String cls = executor().getAttribute(this, "class");
        return cls != null && !cls.contains("x-btn-disabled");
    }
}