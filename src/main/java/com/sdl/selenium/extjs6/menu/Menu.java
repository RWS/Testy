package com.sdl.selenium.extjs6.menu;

import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.link.WebLink;

import java.util.Arrays;
import java.util.List;

public class Menu extends WebLocator {

    public Menu() {
        setClassName("Menu");
        setBaseCls("x-menu");
        setAttribute("aria-hidden", "false");
        setVisibility(true);
    }

    public Menu(String title, SearchType... searchTypes) {
        this();
        WebLocator titleEL = new WebLocator().setClasses("x-menu-item-plain").setSearchTitleType(SearchType.DEEP_CHILD_NODE_OR_SELF);
        setTemplateTitle(titleEL);
        setTitle(title, searchTypes);
    }

    public void clickOnMenu(String option, SearchType... searchTypes) {
        ready();
        WebLink link = new WebLink(this).setText(option, searchTypes).setSearchTextType(SearchType.DEEP_CHILD_NODE_OR_SELF);
        link.click();
    }

    public boolean doClickOnMenu(String option, SearchType... searchTypes) {
        ready();
        WebLink link = new WebLink(this).setText(option, searchTypes).setSearchTextType(SearchType.DEEP_CHILD_NODE_OR_SELF);
        return link.doClick();
    }

    public List<String> getMenuValues() {
        WebLocator menuList = new WebLocator(this).setClasses("x-menu-body").setVisibility(true).setInfoMessage(this + " -> x-menu-body");
        menuList.assertReady();
        String[] menuValues = menuList.getText().split("\\n");
        return Arrays.asList(menuValues);
    }

    public boolean showMenu(WebLocator parent) {
        final String id = parent.getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function(){var b = Ext.getCmp('" + id + "'); if(b.menu.isHidden()) {b.showMenu();} return !b.menu.isHidden();})()";
            Object object = WebLocatorUtils.doExecuteScript(script);
            return (Boolean) object;
        }
        return false;
    }

    public boolean hideMenu(WebLocator parent) {
        final String id = parent.getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function(){var b = Ext.getCmp('" + id + "'); if(!b.menu.isHidden()) {b.hideMenu();} return b.menu.isHidden();})()";
            Object object = WebLocatorUtils.doExecuteScript(script);
            return (Boolean) object;
        }
        return false;
    }

    public boolean close() {
        final String id = getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function(){var b = Ext.getCmp('" + id + "'); if(!b.isHidden()) {b.colse();} return b.isHidden();})()";
            Object object = WebLocatorUtils.doExecuteScript(script);
            return (Boolean) object;
        }
        return false;
    }
}