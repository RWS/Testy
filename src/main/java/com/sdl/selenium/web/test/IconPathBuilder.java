package com.sdl.selenium.web.test;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.XPathBuilder;


public class IconPathBuilder extends XPathBuilder {

    public IconPathBuilder() {
    }

    public IconPathBuilder(By... bys) {
        super(bys);
    }

    private String icon;

    public String icon() {
        return icon;
    }

    public <T extends IconPathBuilder> T icon(final String icon) {
        this.icon = icon;
        super.addToTemplate("icon", "count(.//*[contains(@class, '" + icon + "')]) > 0");
//        customProperty.put("tooltip", "count(.//*[contains(@class, '"+icon+"')]) > 0");
        return (T) this;
    }

    public Boolean hasIcon() {
        return icon != null && !icon.equals("");
    }

//    @Override
//    protected String getItemPathText() {
//        String selector = hasText() ? super.getItemPathText() : "";
//        if (hasIcon()) {
//            selector += (selector.length() > 0 ? " and " : "") + "count(.//*[contains(@class, '" + icon() + "')]) > 0";
//        }
//        return selector.length() == 0 ? null : selector;
//    }
}
