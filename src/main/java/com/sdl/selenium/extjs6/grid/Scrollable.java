package com.sdl.selenium.extjs6.grid;

import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.RetryUtils;

import java.time.Duration;

public interface Scrollable extends IWebLocator {

    default boolean isGridLocked() {
        return false;
    }

    default boolean scrollTop() {
        String id = getAttributeId();
        return !Strings.isNullOrEmpty(id) && scrollTop(id);
    }

    default boolean scrollTop(String id) {
        String script = "return (function(c){var a = c." + getView() + ".getScrollable()._scrollElement;if(a.dom.scrollTop != 0){a.dom.scrollTop = 0;return true}return false})(window.Ext.getCmp('" + id + "'))";
        return (Boolean) WebLocatorUtils.doExecuteScript(script);
    }

    private String getView() {
        String view = "view";
        if (isGridLocked()) {
            view = "ownerGrid";
        }
        return view;
    }


    default boolean scrollBottom() {
        String id = getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function(c){var b=c." + getView() + ".scrollable.getMaxUserPosition().y;c.view.scrollBy(0, b);setTimeout(function(){c.view.scrollBy(0, 1000);},50);return true})(window.Ext.getCmp('" + id + "'))";
            return (Boolean) WebLocatorUtils.doExecuteScript(script);
        }
        return false;
    }

    default boolean canItScroll() {
        String id = getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function(c){var b=c." + getView() + ".scrollable.getMaxUserPosition().y;return b>0 })(window.Ext.getCmp('" + id + "'))";
            return (Boolean) WebLocatorUtils.doExecuteScript(script);
        }
        return false;
    }

    default boolean isScrollTop() {
        String id = getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function (c){return c." + getView() + ".scrollable._scrollElement.dom.scrollTop == 0;})(window.Ext.getCmp('" + id + "'))";
            return (Boolean) WebLocatorUtils.doExecuteScript(script);
        }
        return false;
    }

    default boolean isScrollBottom() {
        String id = getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function (c){var a=c." + getView() + ".scrollable,b=a._scrollElement;return Math.round(b.dom.scrollTop) >= a.getMaxPosition().y;})(window.Ext.getCmp('" + id + "'))";
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
            String script = "return (function(c){var a=c." + getView() + ",b=a.getScrollable()._scrollElement;if(b.dom.scrollTop>0){b.dom.scrollTop-=a.getHeight()-13;return true}return false})(window.Ext.getCmp('" + id + "'))";
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
        String script = "return (function(c){var a=c." + getView() + ",b=a.scrollable._scrollElement;if(b.dom.scrollTop<a.scrollable.getMaxPosition().y){b.dom.scrollTop += a.getHeight() - 13;return true}return false})(window.Ext.getCmp('" + id + "'))";
        return (Boolean) WebLocatorUtils.doExecuteScript(script);
    }

    default boolean scrollPageDownInTree() {
        String id = getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function(c){var a=c." + getView() + ",b=a.scrollable._scrollElement,d=a.body.dom.firstChild.scrollHeight;if(b.dom.scrollTop<a.scrollable.getMaxPosition().y){b.dom.scrollTop += (a.body.dom.childElementCount - 3) * d;setTimeout(function(){b.dom.scrollTop += 1;},2);return true}return false})(window.Ext.getCmp('" + id + "'))";
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
        return RetryUtils.retry(50, () -> {
            boolean isPresent = el.waitToRender(Duration.ofMillis(100), false);
            if (!isPresent && !isScrollBottom()) {
                scrollPageDown();
            }
            return isPresent;
        });
    }
}