package com.sdl.selenium.web;

import com.extjs.selenium.Utils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private List<String> excludeClasses;
    private String name;
    private String text;
    protected List<SearchType> defaultSearchTextType = new ArrayList<SearchType>();
    private List<SearchType> searchTextType = new ArrayList<SearchType>();
    private String style;
    private String elCssSelector;
    private String title;
    private String elPathSuffix;

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

    /**
     * Used for finding element process (to generate xpath address)
     * @param tag (type of DOM element) eg. input or h2
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setTag(final String tag) {
        this.tag = tag;
        return (T) this;
    }

    public String getId() {
        return id;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * @param id eg. id="buttonSubmit"
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setId(final String id) {
        this.id = id;
        return (T) this;
    }

    public String getElPath() {
        return elPath;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * Once used all other attributes will be ignored. Try using this class to a minimum!
     *
     * @param elPath absolute way (xpath) to identify element
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setElPath(final String elPath) {
        this.elPath = elPath;
        return (T) this;
    }

    public String getBaseCls() {
        return baseCls;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * @param baseCls base class
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setBaseCls(final String baseCls) {
        this.baseCls = baseCls;
        return (T) this;
    }

    /**
     * @depricate please use getClasses()
     * @return class
     */
    @Deprecated
    public String getCls() {
        return cls;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * @deprecated please use setClasses("cls")
     * @param cls class of element
     * @return this element
     */
    @Deprecated
    public <T extends WebLocatorAbstractBuilder> T setCls(final String cls) {
        this.cls = cls;
        return (T) this;
    }

    public List<String> getClasses() {
        return classes;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * @param classes list of classes
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setClasses(final String... classes) {
        if (classes != null) {
            this.classes = Arrays.asList(classes);
        }
        return (T) this;
    }

    public List<String> getExcludeClasses() {
        return excludeClasses;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * @param excludeClasses list of class to be excluded
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setExcludeClasses(final String ...excludeClasses) {
        if (excludeClasses != null) {
            this.excludeClasses = Arrays.asList(excludeClasses);
        }
        return (T) this;
    }

    public String getName() {
        return name;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * @param name eg. name="buttonSubmit"
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setName(final String name) {
        this.name = name;
        return (T) this;
    }

    public String getText() {
        return text;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * @param text with which to identify the item
     * @param searchType type search text element: see more details SearchType.java
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setText(final String text, final SearchType ...searchType) {
        this.text = text;
        if(searchType != null){
            setSearchTextType(searchType);
        }
        return (T) this;
    }

    public List<SearchType> getSearchTextType() {
        return searchTextType;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * @param searchTextType accepted values are: SearchType.EQUALS
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setSearchTextType(SearchType... searchTextType) {
        this.searchTextType = new ArrayList<SearchType>();
        if (searchTextType != null) {
            Collections.addAll(this.searchTextType, searchTextType);
        }
        this.searchTextType.addAll(defaultSearchTextType);
        return (T) this;
    }

    public String getStyle() {
        return style;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * @param style of element
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setStyle(final String style) {
        this.style = style;
        return (T) this;
    }

    public String getElCssSelector() {
        return elCssSelector;
    }

    /**
     * Used for finding element process (to generate css selector address)
     * @param elCssSelector cssSelector
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setElCssSelector(final String elCssSelector) {
        this.elCssSelector = elCssSelector;
        return (T) this;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * @param title of element
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setTitle(String title) {
        this.title = title;
        return (T) this;
    }

    public String getElPathSuffix() {
        return elPathSuffix;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * @param elPathSuffix additional identification xpath element that will be added at the end
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setElPathSuffix(String elPathSuffix) {
        this.elPathSuffix = elPathSuffix;
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

    /**
     * Used for finding element process (to generate xpath address)
     * @param container parent containing element.
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setContainer(WebLocator container) {
        this.container = container;
        return (T) this;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * @param label text label element
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setLabel(String label) {
        this.label = label;
        return (T) this;
    }

    public String getLabelTag() {
        return labelTag;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * @param labelTag label tag element
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setLabelTag(String labelTag) {
        this.labelTag = labelTag;
        return (T) this;
    }

    public String getLabelPosition() {
        return labelPosition;
    }

    /**
     * Used for finding element process (to generate xpath address)
     * @param labelPosition label position
     * @return this element
     */
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

    public boolean hasId() {
        return id != null && !id.equals("");
    }

    public boolean hasCls() {
        return cls != null && !cls.equals("");
    }

    public boolean hasClasses() {
        return classes != null && classes.size() > 0;
    }

    public boolean hasExcludeClasses() {
        return excludeClasses != null && excludeClasses.size() > 0;
    }

    public boolean hasBaseCls() {
        return baseCls != null && !baseCls.equals("");
    }

    public boolean hasName() {
        return name != null && !name.equals("");
    }

    public boolean hasText() {
        return text != null && !text.equals("");
    }

    public boolean hasStyle() {
        return style != null && !style.equals("");
    }

    public boolean hasElPath() {
        return elPath != null && !elPath.equals("");
    }

    public boolean hasElCssSelector() {
        return elCssSelector != null && !elCssSelector.equals("");
    }

    public boolean hasLabel() {
        return label != null && !label.equals("");
    }

    public boolean hasTitle() {
        return title != null && !title.equals("");
    }

    public boolean hasElPathSuffix() {
        return elPathSuffix != null && !elPathSuffix.equals("");
    }

    public boolean hasPosition() {
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
    protected String getBasePathSelector() {
        // TODO use disabled
        // TODO verify what need to be equal OR contains
        StringBuilder selector = new StringBuilder();
        if (hasId()) {
            selector.append(" and @id='").append(getId()).append("'");
        }
        if (hasName()) {
            selector.append(" and @name='").append(getName()).append("'");
        }
        if (hasBaseCls()) {
            selector.append(" and contains(@class, '").append(getBaseCls()).append("')");
        }
        if (hasCls()) {
            selector.append(" and contains(@class, '").append(getCls()).append("')");
        }
        if (hasClasses()) {
            for (String cls : getClasses()) {
                // TODO try to use this way : $x("//*[contains(concat(' ', @class, ' '), ' x-panel ')]")
                // so result will be only exact math of class
                selector.append(" and contains(@class, '").append(cls).append("')");
            }
        }
        if (hasExcludeClasses()) {
            for (String excludeClasses : getExcludeClasses()) {
                selector.append(" and not(contains(@class, '").append(excludeClasses).append("'))");
            }
        }
        if (hasElPathSuffix()) {
            selector.append(getElPathSuffix());
        }
        selector.append(getItemPathText());
        if (!WebDriverConfig.isIE()) {
            if (hasStyle()) {
                selector.append(" and contains(@style ,'").append(getStyle()).append("')");
            }
            // TODO make specific for WebLocator
            if (isVisibility()) {
//               TODO selector.append(" and count(ancestor-or-self::*[contains(replace(@style, '\s*:\s*', ':'), 'display:none')]) = 0");
                selector.append(" and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0");
            }
        }

        return Utils.fixPathSelector(selector.toString());
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

    // TODO try to make this function protected
    /**
     * Construct selector if WebLocator has text
     *
     * @return
     */
    protected String getItemPathText() {
        String selector = "";
        if (hasText()) {
            String text = getText();
            text = Utils.getEscapeQuotesText(text);
            selector += " and ";
            String pathText = "text()";

            boolean useChildNodesSearch = searchTextType.contains(SearchType.CHILD_NODE) || searchTextType.contains(SearchType.DEEP_CHILD_NODE);
            if(useChildNodesSearch){
                boolean isDeepSearch = searchTextType.contains(SearchType.DEEP_CHILD_NODE);
                selector += "count(" + (isDeepSearch ? "*//" : "") + "text()[";
                pathText = ".";
            }

            if(searchTextType.contains(SearchType.TRIM)){
                pathText = "normalize-space(" + pathText + ")";
            }

            if (searchTextType.contains(SearchType.EQUALS)) {
                selector += pathText + "=" + text;
            } else if (searchTextType.contains(SearchType.STARTS_WITH)) {
                selector += "starts-with(" + pathText + "," + text + ")";
            } else {
                //searchTextType.contains(SearchType.CONTAINS)  //default
                selector += "contains(" + pathText + "," + text + ")";
            }
            if(useChildNodesSearch){
                selector += "]) > 0";
            }
        }
        return selector;
    }

    private String getBaseItemPath() {
        String selector = getBasePathSelector();
        return Utils.fixPathSelector(selector);
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
        } else if (hasClasses()) {
            info = classes.size() == 1 ? classes.get(0) : classes.toString();
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
        return "//" + getLabelTag() + "[text()=" + Utils.getEscapeQuotesText(getLabel()) + "]"; // new Label(getLabel()).getPath()
    }
}