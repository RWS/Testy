package com.sdl.selenium.extjs6.menu;

import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICheck;
import com.sdl.selenium.web.link.WebLink;
import com.sdl.selenium.web.utils.RetryUtils;
import lombok.*;

import java.util.ArrayList;
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
        WebLink link = getWebLink(this, option, searchTypes);
        boolean click = link.doClick();
        if (!click) {
            String id = getAttributeId();
            scrollDown(id);
            link.click();
        }
    }

    public boolean checkInMenu(String option, SearchType... searchTypes) {
        ready();
        Item item = new Item(this, option, searchTypes);
        return item.check(true);
    }

    public boolean mouseOverOnMenu(String option, SearchType... searchTypes) {
        ready();
        return getWebLink(this, option, searchTypes).mouseOver();
    }

    public WebLink getWebLink(String option, SearchType... searchTypes) {
        return getWebLink(this, option, searchTypes);
    }

    public WebLink getWebLink(WebLocator container, String option, SearchType... searchTypes) {
        return new WebLink(container).setText(option, searchTypes).setSearchTextType(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public boolean doClickOnMenu(String option, SearchType... searchTypes) {
        ready();
        WebLink link = getWebLink(this, option, searchTypes);
        boolean click = link.doClick();
        if (!click) {
            String id = getAttributeId();
            scrollDown(id);
            click = link.doClick();
        }
        return click;
    }

    private boolean scrollDown(String id) {
        if (Strings.isNullOrEmpty(id)) {
            return false;
        }
        String script = "return (function (c) {var top = c.scrollable._scrollElement.dom.scrollTop;c.scrollBy(0,50);return Math.round(top) >= c.scrollable.getMaxPosition().y;})(window.Ext.getCmp('" + id + "'))";
        return (Boolean) WebLocatorUtils.doExecuteScript(script);
    }

    public List<String> getMenuValues() {
        WebLocator menuList = new WebLocator(this).setClasses("x-menu-body").setVisibility(true).setInfoMessage(this + " -> x-menu-body");
        menuList.assertReady();
        String[] menuValues = menuList.getText().split("\\n");
        return Arrays.asList(menuValues);
    }

    public List<String> getMenuValues1() {
        WebLocator menuList = new WebLocator(this).setClasses("x-menu-body").setVisibility(true).setInfoMessage(this + " -> x-menu-body");
        WebLink link = new WebLink(menuList).setClasses("x-menu-item-link");
        int size = link.size();
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            link.setResultIdx(i);
            String value = link.getText().trim();
            list.add(value);
        }
        return list;
    }

    public List<Values> getMenuValuesWithStatus() {
        WebLocator menuList = new WebLocator(this).setClasses("x-menu-body").setVisibility(true).setInfoMessage(this + " -> x-menu-body");
        WebLink item = new WebLink(menuList).setClasses("x-menu-item-link").setVisibility(true);
        int size = item.size();
        List<Values> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            item.setResultIdx(i);
            String disabled = item.getAttribute("aria-disabled");
            String text = item.getText();
            list.add(new Values(text, "true".equalsIgnoreCase(disabled) ? "false" : "true"));
        }
        return list;
    }

    public List<String> getAllCheckedValues() {
        Item item = new Item(this, null);
        int size = item.size();
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            item.setResultIdx(i);
            if (item.isChecked()) {
                String text = item.getText();
                list.add(text);
            }
        }
        return list;
    }

    public List<String> getMenuValuesExtend() {
        WebLocator arrow = new WebLocator().setClasses("x-menu-item-arrow");
        List<String> menuValues = this.getMenuValues1();
        List<String> list = new ArrayList<>();
        for (String value : menuValues) {
            WebLink item = getWebLink(value.trim());
            item.setChildNodes(arrow);
            list.add(value.trim());
            if (item.isPresent()) {
                String id = item.getAttribute("aria-owns");
                Menu menuChild1 = RetryUtils.retry(2, () -> {
                    item.click();
                    Menu m = new Menu().setId(id);
                    if (m.ready()) {
                        return m;
                    } else {
                        return null;
                    }
                });
                List<String> menuChild1Values = menuChild1.getMenuValues1();
                for (String itemText : menuChild1Values) {
                    list.add(">" + itemText);
                    WebLink webLink = menuChild1.getWebLink(itemText.trim());
                    webLink.setChildNodes(arrow);
                    if (webLink.isPresent()) {
                        String id2 = webLink.getAttribute("aria-owns");
                        Menu menuChild2 = RetryUtils.retry(2, () -> {
                            webLink.click();
                            Menu m = new Menu().setId(id2);
                            if (m.ready()) {
                                return m;
                            } else {
                                return null;
                            }
                        });
                        List<String> menuChild2Values = menuChild2.getMenuValues1();
                        menuChild2Values = menuChild2Values.stream().map(h -> ">>" + h).toList();
                        list.addAll(menuChild2Values);
                    }
                }
            }
        }
        return list;
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
        final String parentId = parent.getAttributeId();
        return hideMenu(parentId);
    }

    public boolean hideMenu(String parentId) {
        if (!Strings.isNullOrEmpty(parentId)) {
            String script = "return (function(){var b = Ext.getCmp('" + parentId + "'); if(!b.menu.isHidden()) {b.hideMenu();} return b.menu.isHidden();})()";
            Object object = WebLocatorUtils.doExecuteScript(script);
            return (Boolean) object;
        }
        return false;
    }

    public boolean isHideMenu(WebLocator parent) {
        final String parentId = parent.getAttributeId();
        return hideMenu(parentId);
    }

    public boolean isHideMenu(String parentId) {
        if (!Strings.isNullOrEmpty(parentId)) {
            String script = "return (function(){var b = Ext.getCmp('" + parentId + "'); return b.menu.isHidden();})()";
            Object object = WebLocatorUtils.doExecuteScript(script);
            return (Boolean) object;
        }
        return false;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    @Setter
    @Getter
    public static class Values {
        private String name;
        private String enabled;
    }

    public class Item extends WebLocator implements ICheck {
        WebLink itemLink;

        public Item(WebLocator container, String option, SearchType... searchTypes) {
            WebLink link = getWebLink(null, option, searchTypes);
            setClasses("x-menu-item").setChildNodes(link);
            itemLink = getWebLink(container, option, searchTypes);
            setVisibility(true);
        }

        @Override
        public boolean isChecked() {
            String aClass = getAttributeClass();
            return aClass != null && aClass.contains("x-menu-item-checked");
        }

        @Override
        public boolean click() {
            return itemLink.click();
        }
    }
}