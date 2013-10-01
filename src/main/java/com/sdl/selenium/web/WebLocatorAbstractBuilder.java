package com.sdl.selenium.web;

import com.extjs.selenium.Utils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to simple construct xpath for WebLocator's
 */
public abstract class WebLocatorAbstractBuilder {
    private static final Logger logger = Logger.getLogger(WebLocatorAbstractBuilder.class);

    private String className = "WebLocator";
    private String tag = "*";
    private String id;
    private String elPath;
    private String baseCls;
    private String cls;
    private List<String> classes;
    private String excludeCls;
    private String name;
    private String text;
    private SearchType searchTextType = SearchType.CONTAINS;
    private String style;
    private String elCssSelector;
    private String title;
    private String deepness;

    private String infoMessage;

    private String label;
    private String labelTag = "label";
    private String labelPosition = "//following-sibling::*";

    private int position = -1;

    //private int elIndex; // TODO try to find how can be used

    private boolean visibility;
    private long renderMillis = WebLocatorConfig.getDefaultRenderMillis();
    private int activateSeconds = 60;

    private WebLocator container;

    // =========================================
    // ========== setters & getters ============
    // =========================================

    /**
     * @return tag (type of DOM element)
     *         default to "*"
     */
    public String getTag() {
        return tag;
    }

    public <T extends WebLocatorAbstractBuilder> T setTag(final String tag) {
        this.tag = tag;
        return (T) this;
    }

    public String getId() {
        return id;
    }

    public <T extends WebLocatorAbstractBuilder> T setId(final String id) {
        this.id = id;
        return (T) this;
    }

    public String getElPath() {
        return elPath;
    }

    /**
     * Once used all other attributes will be ignored. Try using this class to a minimum.
     *
     * @param elPath
     */
    public <T extends WebLocatorAbstractBuilder> T setElPath(final String elPath) {
        this.elPath = elPath;
        return (T) this;
    }

    public String getBaseCls() {
        return baseCls;
    }

    public <T extends WebLocatorAbstractBuilder> T setBaseCls(final String baseCls) {
        this.baseCls = baseCls;
        return (T) this;
    }

    public String getCls() {
        return cls;
    }

    public <T extends WebLocatorAbstractBuilder> T setCls(final String cls) {
        this.cls = cls;
        return (T) this;
    }

    public List<String> getClasses() {
        return classes;
    }

    public <T extends WebLocatorAbstractBuilder> T setClasses(final String... classes) {
        if (classes != null) {
            this.classes = new ArrayList<String>();
            for (String cls : classes) {
                this.classes.add(cls);
            }
        }
        return (T) this;
    }

    public String getExcludeCls() {
        return excludeCls;
    }

    public <T extends WebLocatorAbstractBuilder> T setExcludeCls(final String excludeCls) {
        this.excludeCls = excludeCls;
        return (T) this;
    }

    public String getName() {
        return name;
    }

    public <T extends WebLocatorAbstractBuilder> T setName(final String name) {
        this.name = name;
        return (T) this;
    }

    public String getText() {
        return text;
    }

    public <T extends WebLocatorAbstractBuilder> T setText(final String text) {
        this.text = text;
        return (T) this;
    }

    public SearchType getSearchTextType() {
        return searchTextType;
    }

    /**
     * @param searchTextType accepted values are: SearchType.EQUALS
     * @param <T>
     * @return
     */
    public <T extends WebLocatorAbstractBuilder> T setSearchTextType(SearchType searchTextType) {
        this.searchTextType = searchTextType;
        return (T) this;
    }

    public String getStyle() {
        return style;
    }

    public <T extends WebLocatorAbstractBuilder> T setStyle(final String style) {
        this.style = style;
        return (T) this;
    }

    public String getElCssSelector() {
        return elCssSelector;
    }

    public <T extends WebLocatorAbstractBuilder> T setElCssSelector(final String elCssSelector) {
        this.elCssSelector = elCssSelector;
        return (T) this;
    }

    public String getTitle() {
        return title;
    }

    public <T extends WebLocatorAbstractBuilder> T setTitle(String title) {
        this.title = title;
        return (T) this;
    }

    public String getDeepness() {
        return deepness;
    }

    public <T extends WebLocatorAbstractBuilder> T setDeepness(String deepness) {
        this.deepness = deepness;
        return (T) this;
    }

    public String getInfoMessage() {
        return infoMessage;
    }

    public <T extends WebLocatorAbstractBuilder> T setInfoMessage(final String infoMessage) {
        this.infoMessage = infoMessage;
        return (T) this;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public <T extends WebLocatorAbstractBuilder> T setVisibility(final boolean visibility) {
        this.visibility = visibility;
        return (T) this;
    }

    public long getRenderMillis() {
        return renderMillis;
    }

    public <T extends WebLocatorAbstractBuilder> T setRenderMillis(final long renderMillis) {
        this.renderMillis = renderMillis;
        return (T) this;
    }

    public <T extends WebLocatorAbstractBuilder> T setRenderSeconds(final int renderSeconds) {
        setRenderMillis(renderSeconds * 1000);
        return (T) this;
    }

    public int getActivateSeconds() {
        return activateSeconds;
    }

    public <T extends WebLocatorAbstractBuilder> T setActivateSeconds(final int activateSeconds) {
        this.activateSeconds = activateSeconds;
        return (T) this;
    }

    // TODO verify what type must return
    public WebLocator getContainer() {
        return container;
    }

    public <T extends WebLocatorAbstractBuilder> T setContainer(WebLocator container) {
        this.container = container;
        return (T) this;
    }

    public String getLabel() {
        return label;
    }

    public <T extends WebLocatorAbstractBuilder> T setLabel(String label) {
        this.label = label;
        return (T) this;
    }

    public String getLabelTag() {
        return labelTag;
    }

    public <T extends WebLocatorAbstractBuilder> T setLabelTag(String labelTag) {
        this.labelTag = labelTag;
        return (T) this;
    }

    public String getLabelPosition() {
        return labelPosition;
    }

    public <T extends WebLocatorAbstractBuilder> T setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
        return (T) this;
    }

    public int getPosition() {
        return position;
    }

    /**
     * example: //*[contains(@class, 'x-grid-panel') and not(starts-with(@id, 'ext-gen')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))][position() = 1]
     *
     * @param position starting index = 1
     * @param <T>
     * @return
     */

    public <T extends WebLocatorAbstractBuilder> T setPosition(int position) {
        this.position = position;
        return (T) this;
    }

    // =========================================
    // =============== Methods =================
    // =========================================

    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    public Boolean hasId() {
        return id != null && !id.equals("");
    }

    public Boolean hasCls() {
        return cls != null && !cls.equals("");
    }

    public Boolean hasClasses() {
        return classes != null && classes.size() > 0;
    }

    public Boolean hasExcludeCls() {
        return excludeCls != null && !excludeCls.equals("");
    }

    public Boolean hasBaseCls() {
        return baseCls != null && !baseCls.equals("");
    }

    public Boolean hasName() {
        return name != null && !name.equals("");
    }

    public Boolean hasText() {
        return text != null && !text.equals("");
    }

    public Boolean hasStyle() {
        return style != null && !style.equals("");
    }

    public Boolean hasElPath() {
        return elPath != null && !elPath.equals("");
    }

    public Boolean hasElCssSelector() {
        return elCssSelector != null && !elCssSelector.equals("");
    }

    public Boolean hasLabel() {
        return label != null && !label.equals("");
    }

    public Boolean hasTitle() {
        return title != null && !title.equals("");
    }

    public Boolean hasDeepness() {
        return deepness != null && !deepness.equals("");
    }

    public Boolean hasPosition() {
        return position > 0;
    }

    // =========================================
    // ============ XPath Methods ==============
    // =========================================

    /**
     * Containing baseCls, class, name and style
     *
     * @return baseSelector
     */
    public String getBasePathSelector() {
        // TODO use disabled
        // TODO verify what need to be equal OR contains
        String selector = "";
        if (hasId()) {
            selector += " and @id='" + getId() + "'";
        }
        if (hasBaseCls()) {
            selector += " and contains(@class, '" + getBaseCls() + "')";
        }
        if (hasCls()) {
            selector += " and contains(@class, '" + getCls() + "')";
        }
        if (hasClasses()) {
            for (String cls : getClasses()) {
                selector += " and contains(@class, '" + cls + "')";
            }
        }
        if (hasExcludeCls()) {
            selector += " and not(contains(@class, '" + getExcludeCls() + "'))";
        }
        if (hasName()) {
            selector += " and contains(@name,'" + getName() + "')";
        }
        if (hasDeepness()) {
            selector += " and count(" + getDeepness() + ") > 0";
        }
        selector += getItemPathText();
        if (!WebLocator.isIE()) {
            if (hasStyle()) {
                selector += " and contains(@style ,'" + getStyle() + "')";
            }
            // TODO make specific for WebLocator
            if (isVisibility()) {
                selector += " and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0";
            }
        }

        selector = Utils.fixPathSelector(selector);
        return selector;
    }

    /**
     * this method is meant to be overridden by each component
     *
     * @param disabled
     * @return
     */
    public String getItemPath(boolean disabled) {
        String selector = getBaseItemPath();
        selector = "//" + getTag() + (selector.length() > 0 ? ("[" + selector + "]") : "");
        return selector;
    }

    /**
     * Construct selector if WebLocator has text
     *
     * @return
     */
    public String getItemPathText() {
        String selector = "";
        if (hasText()) {
            String text = getText();
            text = Utils.escapeQuotes(text);
            selector += " and ";
            if (SearchType.EQUALS.equals(getSearchTextType())) {
                selector += "text()=" + text;
            } else if (SearchType.CONTAINS.equals(getSearchTextType())) {
                selector += "contains(text()," + text + ")";
            } else if (SearchType.STARTS_WITH.equals(getSearchTextType())) {
                selector += "starts-with(text()," + text + ")";
            } else {
                logger.warn("searchType did not math to any accepted values");
                selector = "";
            }
        }
        return selector;
    }

    private String getBaseItemPath() {
        String selector = getBasePathSelector();
        selector = Utils.fixPathSelector(selector);
        return selector;
    }

    /**
     * disabled is false
     *
     * @return
     */
    public final String getPath() {
        return getPath(false);
    }

    /**
     * @param disabled
     * @return
     */
    public String getPath(boolean disabled) {
        String returnPath;
        if (hasElPath()) {
            returnPath = getElPath();

            String baseItemPath = getBaseItemPath();
            if (baseItemPath != null && !baseItemPath.equals("")) {
                // TODO "inject" baseItemPath to elPath
//                logger.warn("TODO must inject: \"" + baseItemPath + "\" in \"" + returnPath + "\"");
            }
        } else {
            returnPath = getItemPath(disabled);
        }

        returnPath = afterItemPathCreated(returnPath);

        // add container path
        if (getContainer() != null) {
            returnPath = getContainer().getPath() + returnPath;
        }

//        logger.debug(returnPath);
        return returnPath;
    }

    @Override
    public String toString() {
        String info = getInfoMessage();
        if (info == null || "".equals(info)) {
            info = itemToString();
        }
        if (WebLocatorConfig.isLogUseClassName() && !getClassName().equals(info)) {
            info += " - " + getClassName();
        }
        // add container path
        if (WebLocatorConfig.isLogContainers() && getContainer() != null) {
            info = getContainer().toString() + " -> " + info;
        }
        return info;
    }

    public String itemToString() {
        String info;
        if (hasText()) {
            info = getText();
        } else if (hasId()) {
            info = getId();
        } else if (hasName()) {
            info = getName();
        } else if (hasCls()) {
            info = getCls();
        } else if (hasLabel()) {
            info = getLabel();
        } else if (hasTitle()) {
            info = getTitle();
        } else if (hasBaseCls()) {
            info = getBaseCls();
        } else if (hasElPath()) {// TODO verify with / without xpath
            info = getElPath();
        } else {
            info = getClassName();
        }
        return info;
    }


    public String afterItemPathCreated(String itemPath) {
        if (hasLabel()) {
            itemPath = getLabelPath() + getLabelPosition() + itemPath;
        }
        itemPath = addPositionToPath(itemPath);
        return itemPath;
    }

    public String addPositionToPath(String itemPath) {
        if (hasPosition()) {
            itemPath += "[position() = " + getPosition() + "]";
        }
        return itemPath;
    }

    public String getLabelPath() {
        return "//" + getLabelTag() + "[text()=" + Utils.escapeQuotes(getLabel()) + "]"; // new Label(getLabel()).getPath()
    }
}