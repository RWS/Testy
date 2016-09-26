package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.*;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class Grid extends Table {
    private static final Logger LOGGER = LoggerFactory.getLogger(Grid.class);

    public Grid() {
        withClassName("Grid");
        withBaseCls("x-grid");
        withTag("*");
    }

    public Grid(WebLocator container) {
        this();
        withContainer(container);
    }

    /**
     * @deprecated use {@link #getRow(int, AbstractCell...)}
     * @param indexRow eg. 1
     * @param byCells TableCell
     * @return TableRow
     */
    public TableRow getRow(int indexRow, TableCell... byCells) {
        return new TableRow(this, indexRow, byCells).withTag("table").withInfoMessage("-Row");
    }

    public Row getRow(int indexRow, AbstractCell... byCells) {
        return new Row(this, indexRow, byCells).withTag("table").withInfoMessage("-Row");
    }

    public boolean waitToActivate(int seconds) {
        String info = toString();
        int count = 0;
        boolean hasMask;
        while ((hasMask = hasMask()) && (count < seconds)) {
            count++;
            LOGGER.info("waitToActivate:" + (seconds - count) + " seconds; " + info);
            Utils.sleep(900);
        }
        return !hasMask;
    }

    private boolean hasMask(){
        WebLocator mask = new WebLocator(this).withClasses("x-mask").withElxPathSuffix("style", "not(contains(@style, 'display: none'))").setAttribute("aria-hidden", "false");
        LOGGER.debug("mask {}", mask.getXPath());
        return mask.waitToRender(500);
    }

    /**
     * Will Fail if id is null
     *
     * @return attribute
     */
    protected String getAttrId() {
        String id = getAttributeId();
        assertThat(MessageFormat.format("{0} id is null. The path is: {1}", getPathBuilder().getClassName(), getXPath()), id, notNullValue());
        return id;
    }

    /**
     * Scroll on the top in Grid
     *
     * @return true if scrolled
     */
    public boolean scrollTop() {
        String id = getAttrId();
        return scrollTop(id);
    }

    protected boolean scrollTop(String id) {
        String script = "return (function(c){var a = c.view.getScrollable()._scrollElement;if(a.dom.scrollTop != 0){a.dom.scrollTop = 0;return true}return false})(window.Ext.getCmp('" + id + "'))";
        return executeScrollScript("scrollTop", script);
    }


    public boolean scrollBottom() {
        String id = getAttrId();
        String script = "return (function(c){var b=c.view.scrollable.getMaxUserPosition().y;c.view.scrollBy(0, b);setTimeout(function(){c.view.scrollBy(0, 1000);},50);return true})(window.Ext.getCmp('" + id + "'))";
        return executeScrollScript("scrollButtom", script);
    }

    /**
     * Scroll Up one visible page in Grid
     *
     * @return true if scrolled
     */

    public boolean scrollPageUp() {
        String id = getAttrId();
        String script = "return (function(c){var a=c.view,b=a.getScrollable()._scrollElement;if(b.dom.scrollTop>0){b.dom.scrollTop-=a.getHeight()-13;return true}return false})(window.Ext.getCmp('" + id + "'))";
        return executeScrollScript("scrollPageUp", script);
    }

    /**
     * Scroll Down one visible page in Grid
     *
     * @return true if scrolled
     */
    public boolean scrollPageDown() {
        String id = getAttrId();
        return scrollPageDown(id);
    }

    protected boolean scrollPageDown(String id) {
        String script = "return (function(c){var a=c.view,b=a.getScrollable()._scrollElement;if(b.dom.scrollTop<a.scrollable.getMaxPosition().y){b.dom.scrollTop+=a.getHeight()-13;return true}return false})(window.Ext.getCmp('" + id + "'))";
        return executeScrollScript("scrollPageDown", script);
    }

    protected boolean executeScrollScript(String info, String script) {
        Boolean scrolled = (Boolean) WebLocatorUtils.doExecuteScript(script);
        LOGGER.info(this + " - " + info + " > " + scrolled);
        return scrolled;
    }
}
