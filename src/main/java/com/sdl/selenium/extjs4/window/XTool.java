package com.sdl.selenium.extjs4.window;

import com.sdl.selenium.web.WebLocator;

public interface XTool {

    WebLocator getView();

    /**
     * click on element with class "x-tool-" + suffix
     *
     * @param suffix element
     * @return true | false
     */
    default boolean clickOnTool(String suffix) {
        WebLocator toolElement = getToolEl(suffix).setVisibility(true);
        return toolElement.click();
    }

    default boolean doClickOnTool(String suffix) {
        WebLocator toolElement = getToolEl(suffix).setVisibility(true);
        return toolElement.doClick();
    }

    default WebLocator getToolEl(String suffix) {
        return new WebLocator(getView()).setClasses("x-tool-" + suffix);
    }

    default boolean close() {
        return clickOnTool("close");
    }

    default boolean doClose() {
        return doClickOnTool("close");
    }

    default boolean maximize() {
        return clickOnTool("maximize");
    }

    default boolean doMaximize() {
        return doClickOnTool("maximize");
    }

    default boolean restore() {
        return clickOnTool("restore");
    }

    default boolean doRestore() {
        return doClickOnTool("restore");
    }

    default boolean minimize() {
        return clickOnTool("minimize");
    }

    default boolean doMinimize() {
        return doClickOnTool("minimize");
    }

    default boolean toggle() {
        return clickOnTool("toggle");
    }

    default boolean doToggle() {
        return doClickOnTool("toggle");
    }

    default boolean plus() {
        return clickOnTool("plus");
    }

    default boolean doPlus() {
        return doClickOnTool("plus");
    }

    default boolean collapse() {
        return clickOnTool("collapse");
    }

    default boolean doCollapse() {
        return doClickOnTool("collapse");
    }

    default boolean expand() {
        return clickOnTool("expand");
    }

    default boolean doExpand() {
        return doClickOnTool("expand");
    }
}
