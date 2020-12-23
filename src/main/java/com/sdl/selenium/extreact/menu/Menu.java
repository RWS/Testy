package com.sdl.selenium.extreact.menu;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.link.WebLink;

import java.util.ArrayList;
import java.util.List;

public class Menu extends WebLocator {

    public Menu() {
        setClassName("Menu");
        setBaseCls("x-menu");
        setExcludeClasses("x-hidden-display");
    }

    public Menu(String title, SearchType... searchTypes) {
        this();
        WebLocator titleEL = new WebLocator().setClasses("x-menuitem").setSearchTitleType(SearchType.DEEP_CHILD_NODE_OR_SELF);
        setTemplateTitle(titleEL);
        setTitle(title, searchTypes);
    }

    public void clickOnMenu(String option, SearchType... searchTypes) {
        ready();
        WebLocator item = new WebLocator(this).setClasses("x-menuitem").setText(option, searchTypes).setSearchTextType(SearchType.DEEP_CHILD_NODE_OR_SELF);
        item.click();
    }

    public boolean doClickOnMenu(String option, SearchType... searchTypes) {
        ready();
        WebLocator item = new WebLocator(this).setClasses("x-menuitem").setText(option, searchTypes).setSearchTextType(SearchType.DEEP_CHILD_NODE_OR_SELF);
        return item.doClick();
    }

    public List<String> getMenuValues() {
        WebLocator menuList = new WebLocator(this).setClasses("x-menuitem").setVisibility(true).setInfoMessage(this + " -> x-menuitem");
        int size = menuList.size();
        List<String> items = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            menuList.setResultIdx(i);
            items.add(menuList.getText());
        }
        return items;
    }

    public List<Values> getMenuValuesWithStatus() {
        WebLocator menuList = new WebLocator(this).setClasses("x-menuitem").setVisibility(true).setInfoMessage(this + " -> x-menuitem");
        WebLink item = new WebLink(menuList).setClasses("x-menu-item-link");
        int size = item.size();
        List<Values> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            item.setResultIdx(i);
            String disabled = item.getAttribute("aria-disabled");
            String text = item.getText();
            list.add(new Values(text, !"true".equalsIgnoreCase(disabled)));
        }
        return list;
    }

    public boolean showMenu(WebLocator parent) {
        WebLocator splitArrow = new WebLocator(parent).setClasses("x-splitArrow-el").setInfoMessage("Arrow");
        return isShowMenu(parent) || splitArrow.click();
    }

    public boolean hideMenu(WebLocator parent) {
        WebLocator splitArrow = new WebLocator(parent).setClasses("x-splitArrow-el").setInfoMessage("Arrow");
        return isHideMenu(parent) || splitArrow.click();
    }

    public boolean isHideMenu(WebLocator parent) {
        WebLocator splitArrow = new WebLocator(parent).setClasses("x-splitArrow-el").setInfoMessage("Arrow");
        String aClass = splitArrow.getAttributeClass();
        return aClass != null && !aClass.contains("x-pressed");
    }

    public boolean isShowMenu(WebLocator parent) {
        WebLocator splitArrow = new WebLocator(parent).setClasses("x-splitArrow-el").setInfoMessage("Arrow");
        String aClass = splitArrow.getAttributeClass();
        return aClass != null && aClass.contains("x-pressed");
    }

    public static class Values {
        private String name;
        private boolean enabled;

        public Values(String name, boolean enabled) {
            this.name = name;
            this.enabled = enabled;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}