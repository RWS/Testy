package com.sdl.selenium.extjs6.grid;

import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.WebLocator;

public interface Scrollable extends IWebLocator {

    default boolean scrollTop() {
        String id = getAttributeId();
        return !Strings.isNullOrEmpty(id) && scrollTop(id);
    }

    default boolean scrollTop(String id) {
        String script = "return (function(c){var a = c.view.getScrollable()._scrollElement;if(a.dom.scrollTop != 0){a.dom.scrollTop = 0;return true}return false})(window.Ext.getCmp('" + id + "'))";
//        return executeScrollScript("scrollTop", script);
        return (Boolean) WebLocatorUtils.doExecuteScript(script);
    }


    default boolean scrollBottom() {
        String id = getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function(c){var b=c.view.scrollable.getMaxUserPosition().y;c.view.scrollBy(0, b);setTimeout(function(){c.view.scrollBy(0, 1000);},50);return true})(window.Ext.getCmp('" + id + "'))";
            return (Boolean) WebLocatorUtils.doExecuteScript(script);
        }
        return false;
    }

    default boolean isScrollTop() {
        String id = getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function (c){return c.view.scrollable._scrollElement.dom.scrollTop == 0;})(window.Ext.getCmp('" + id + "'))";
            return (Boolean) WebLocatorUtils.doExecuteScript(script);
        }
        return false;
    }

    default boolean isScrollBottom() {
        String id = getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function (c){var a=c.view.scrollable,b=a._scrollElement;return Math.round(b.dom.scrollTop) >= a.getMaxPosition().y;})(window.Ext.getCmp('" + id + "'))";
            return (Boolean) WebLocatorUtils.doExecuteScript(script);
        }
        return false;
    }

    /**
     * Scroll Up one visible page in Grid
     *
     * @return true if scrolled
     */

    default boolean scrollPageUp() {
        String id = getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function(c){var a=c.view,b=a.getScrollable()._scrollElement;if(b.dom.scrollTop>0){b.dom.scrollTop-=a.getHeight()-13;return true}return false})(window.Ext.getCmp('" + id + "'))";
            return (Boolean) WebLocatorUtils.doExecuteScript(script);
        }
        return false;
    }

    /**
     * Scroll Down one visible page in Grid
     *
     * @return true if scrolled
     */
    default boolean scrollPageDown() {
        String id = getAttributeId();
        return !Strings.isNullOrEmpty(id) && scrollPageDown(id);
    }

    default boolean scrollPageDown(String id) {
        String script = "return (function(c){var a=c.view,b=a.scrollable._scrollElement;if(b.dom.scrollTop<a.scrollable.getMaxPosition().y){b.dom.scrollTop+=a.getHeight()-13;return true}return false})(window.Ext.getCmp('" + id + "'))";
        return (Boolean) WebLocatorUtils.doExecuteScript(script);
    }

    default boolean scrollPageDownInTree() {
        String id = getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function(c){var a=c.view,b=a.scrollable._scrollElement,d=a.body.dom.firstChild.scrollHeight;if(b.dom.scrollTop<a.scrollable.getMaxPosition().y){b.dom.scrollTop += (a.body.dom.childElementCount - 2) * d;setTimeout(function(){b.dom.scrollTop += 1;},2);return true}return false})(window.Ext.getCmp('" + id + "'))";
            return (Boolean) WebLocatorUtils.doExecuteScript(script);
        }
        return false;
    }

    @Deprecated
    default boolean scrollTo(WebLocator el) {
        return scrollPageDownTo(el);
    }

    default boolean scrollPageDownTo(WebLocator el) {
        el.setVisibility(true);
        boolean scroll = true;
        while (!el.waitToRender(100)) {
            scroll = scrollPageDown();
            if (!scroll) {
                break;
            }
        }
        return scroll;
    }
}