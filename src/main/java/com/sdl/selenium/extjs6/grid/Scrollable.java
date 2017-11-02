package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.WebLocator;

public interface Scrollable extends IWebLocator {

    default boolean scrollTop() {
        String id = getAttributeId();
        return scrollTop(id);
    }

    default boolean scrollTop(String id) {
        String script = "return (function(c){var a = c.view.getScrollable()._scrollElement;if(a.dom.scrollTop != 0){a.dom.scrollTop = 0;return true}return false})(window.Ext.getCmp('" + id + "'))";
//        return executeScrollScript("scrollTop", script);
        return (Boolean) WebLocatorUtils.doExecuteScript(script);
    }


    default boolean scrollBottom() {
        String id = getAttributeId();
        String script = "return (function(c){var b=c.view.scrollable.getMaxUserPosition().y;c.view.scrollBy(0, b);setTimeout(function(){c.view.scrollBy(0, 1000);},50);return true})(window.Ext.getCmp('" + id + "'))";
        return (Boolean) WebLocatorUtils.doExecuteScript(script);
    }

    /**
     * Scroll Up one visible page in Grid
     *
     * @return true if scrolled
     */

    default boolean scrollPageUp() {
        String id = getAttributeId();
        String script = "return (function(c){var a=c.view,b=a.getScrollable()._scrollElement;if(b.dom.scrollTop>0){b.dom.scrollTop-=a.getHeight()-13;return true}return false})(window.Ext.getCmp('" + id + "'))";
        return (Boolean) WebLocatorUtils.doExecuteScript(script);
    }

    /**
     * Scroll Down one visible page in Grid
     *
     * @return true if scrolled
     */
    default boolean scrollPageDown() {
        String id = getAttributeId();
        return scrollPageDown(id);
    }

    default boolean scrollPageDown(String id) {
        String script = "return (function(c){var a=c.view,b=a.getScrollable()._scrollElement;if(b.dom.scrollTop<a.scrollable.getMaxPosition().y){b.dom.scrollTop+=a.getHeight()-13;return true}return false})(window.Ext.getCmp('" + id + "'))";
        return (Boolean) WebLocatorUtils.doExecuteScript(script);
    }

    default void scrollTo(WebLocator cell) {
        while (!cell.waitToRender(100) || !cell.isDisplayed()) {
            if (!scrollPageDown()) {
                break;
            }
        }
    }
}
