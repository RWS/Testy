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
    private String labelPosition = "//following-sibling::*//";

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
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setTag(String)}
     *         <p>tag (type of DOM element)</p>
     *         <pre>default to "*"</pre>
     */
    public String getTag() {
        return tag;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param tag (type of DOM element) eg. input or h2
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setTag(final String tag) {
        this.tag = tag;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setId(String)}
     */
    public String getId() {
        return id;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param id eg. id="buttonSubmit"
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setId(final String id) {
        this.id = id;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setElPath(String)}
     *         <p>returned value does not include containers path</p>
     */
    public String getElPath() {
        return elPath;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     * Once used all other attributes will be ignored. Try using this class to a minimum!
     *
     * @param elPath absolute way (xpath) to identify element
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setElPath(final String elPath) {
        this.elPath = elPath;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setBaseCls(String)}
     */
    public String getBaseCls() {
        return baseCls;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param baseCls base class
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setBaseCls(final String baseCls) {
        this.baseCls = baseCls;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setCls(String)}
     */
    public String getCls() {
        return cls;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     * <p>Find element with <b>exact math</b> of specified class (equals)</p>
     *
     * @param cls class of element
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setCls(final String cls) {
        this.cls = cls;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setClasses(String...)}
     */
    public List<String> getClasses() {
        return classes;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p>Use it when element must have all specified css classes (order is not important).</p>
     * <ul>
     * <li>Provided classes must be conform css rules.</li>
     * </ul>
     *
     * @param classes list of classes
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setClasses(final String... classes) {
        if (classes != null) {
            this.classes = Arrays.asList(classes);
        }
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setExcludeClasses(String...)}
     */
    public List<String> getExcludeClasses() {
        return excludeClasses;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param excludeClasses list of class to be excluded
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setExcludeClasses(final String... excludeClasses) {
        if (excludeClasses != null) {
            this.excludeClasses = Arrays.asList(excludeClasses);
        }
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setName(String)}
     */
    public String getName() {
        return name;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param name eg. name="buttonSubmit"
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setName(final String name) {
        this.name = name;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setText(String, SearchType...)}
     */
    public String getText() {
        return text;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param text       with which to identify the item
     * @param searchType type search text element: see more details see {@link SearchType}
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setText(final String text, final SearchType... searchType) {
        this.text = text;
        if (searchType != null) {
            setSearchTextType(searchType);
        }
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setSearchTextType(SearchType...)}
     */
    public List<SearchType> getSearchTextType() {
        return searchTextType;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param searchTextType accepted values are: {@link SearchType#EQUALS}
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

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setStyle(String)}
     */
    public String getStyle() {
        return style;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param style of element
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setStyle(final String style) {
        this.style = style;
        return (T) this;
    }

    /**
     * <p><b>not implemented yet</b></p>
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setElCssSelector(String)}
     */
    public String getElCssSelector() {
        return elCssSelector;
    }

    /**
     * <p><b>not implemented yet</b></p>
     * <p><b>Used for finding element process (to generate css address)<b></p>
     *
     * @param elCssSelector cssSelector
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setElCssSelector(final String elCssSelector) {
        this.elCssSelector = elCssSelector;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setTitle(String)}
     */
    public String getTitle() {
        return title;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param title of element
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setTitle(String title) {
        this.title = title;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setElPathSuffix(String)}
     */
    public String getElPathSuffix() {
        return elPathSuffix;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     * <p>Example:</p>
     * <pre>
     *     TODO
     * </pre>
     *
     * @param elPathSuffix additional identification xpath element that will be added at the end
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setElPathSuffix(String elPathSuffix) {
        this.elPathSuffix = elPathSuffix;
        return (T) this;
    }

    /**
     * <p><b><i>Used in logging process</i><b></p>
     *
     * @return value that has been set in {@link #setInfoMessage(String)}
     */
    public String getInfoMessage() {
        return infoMessage;
    }

    /**
     * <p><b><i>Used in logging process</i><b></p>
     *
     * @param infoMessage
     * @param <T>
     * @return
     */
    public <T extends WebLocatorAbstractBuilder> T setInfoMessage(final String infoMessage) {
        this.infoMessage = infoMessage;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setVisibility(boolean)}
     */
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
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param container parent containing element.
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setContainer(WebLocator container) {
        this.container = container;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setLabel(String)}
     */
    public String getLabel() {
        return label;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param label text label element
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setLabel(String label) {
        this.label = label;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setLabel(String)}
     */
    public String getLabelTag() {
        return labelTag;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param labelTag label tag element
     * @return this element
     */
    public <T extends WebLocatorAbstractBuilder> T setLabelTag(String labelTag) {
        this.labelTag = labelTag;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setLabelPosition(String)}
     */
    public String getLabelPosition() {
        return labelPosition;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     *
     * @param labelPosition position of this element reported to label
     * @return this element
     * @see <a href="http://www.w3schools.com/xpath/xpath_axes.asp">http://www.w3schools.com/xpath/xpath_axes.asp"</a>
     */
    public <T extends WebLocatorAbstractBuilder> T setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
     *
     * @return value that has been set in {@link #setPosition(int)}
     */
    public int getPosition() {
        return position;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)<b></p>
     * <p>Result Example:</p>
     * <pre>
     *     //*[contains(@class, 'x-grid-panel')][position() = 1]
     * </pre>
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

    /**
     * <p>Used only to identify class type of current object</p>
     * <p> Not used for css class!</p>
     *
     * @return
     */
    public String getClassName() {
        return className;
    }

    protected void setClassName(final String className) {
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
            selector.append(" and contains(concat(' ', @class, ' '), ' ").append(getBaseCls()).append(" ')");
        }
        if (hasCls()) {
            selector.append(" and @class='").append(getCls()).append("'");
        }
        if (hasClasses()) {
            for (String cls : getClasses()) {
//                selector.append(" and contains(@class, '").append(cls).append("')");
                selector.append(" and contains(concat(' ', @class, ' '), ' ").append(cls).append(" ')");
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
    protected String getItemPath(boolean disabled) {
        String selector = getBaseItemPath();
        selector = "//" + getTag() + (selector.length() > 0 ? ("[" + selector + "]") : "");
        return selector;
    }

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
            if (useChildNodesSearch) {
                boolean isDeepSearch = searchTextType.contains(SearchType.DEEP_CHILD_NODE);
                selector += "count(" + (isDeepSearch ? "*//" : "") + "text()[";
                pathText = ".";
            }

            if (searchTextType.contains(SearchType.TRIM)) {
                pathText = "normalize-space(" + pathText + ")";
            }

            if (searchTextType.contains(SearchType.EQUALS)) {
                selector += pathText + "=" + text;
            } else if (searchTextType.contains(SearchType.STARTS_WITH)) {
                selector += "starts-with(" + pathText + "," + text + ")";
            } else if (searchTextType.contains(SearchType.MORE_CONTAINS)) {
                String[] strings = text.split("/");
                String textTmp = "";
                for (String str : strings) {
                    textTmp += "contains(" + pathText + "," + str + ") and ";
                }
                if (textTmp.endsWith(" and ")) {
                    textTmp = textTmp.substring(0, textTmp.length() - 5);
                }
                selector += textTmp;
            } else {
                //searchTextType.contains(SearchType.CONTAINS)  //default
                selector += "contains(" + pathText + "," + text + ")";
            }
            if (useChildNodesSearch) {
                selector += "]) > 0";
            }

            if (searchTextType.contains(SearchType.HTML_NODE)) {
                String a = "normalize-space(concat(./*[1]//text(), ' ', text()[1], ' ', ./*[2]//text(), ' ', text()[2], ' ', ./*[3]//text(), ' ', text()[3], ' ', ./*[4]//text(), ' ', text()[4], ' ', ./*[5]//text(), ' ', text()[5]))=" + text;
                String b = "normalize-space(concat(text()[1], ' ', ./*[1]//text(), ' ', text()[2], ' ', ./*[2]//text(), ' ', text()[3], ' ', ./*[3]//text(), ' ', text()[4], ' ', ./*[4]//text(), ' ', text()[5], ' ', ./*[5]//text()))=" + text;

                selector = " and (" + a + " or " + b + ")";
            }
        }
        return selector;
    }

    private String getBaseItemPath() {
        String selector = getBasePathSelector();
        return Utils.fixPathSelector(selector);
    }

    /**
     * @return final xpath (including containers xpath), used for interacting with browser
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


    protected String afterItemPathCreated(String itemPath) {
        if (hasLabel()) {
            // remove '//' because labelPath already has and include
            if (itemPath.indexOf("//") == 0) {
                itemPath = itemPath.substring(2);
            }
            itemPath = getLabelPath() + getLabelPosition() + itemPath;
        }
        itemPath = addPositionToPath(itemPath);
        return itemPath;
    }

    protected String addPositionToPath(String itemPath) {
        if (hasPosition()) {
            itemPath += "[position() = " + getPosition() + "]";
        }
        return itemPath;
    }

    protected String getLabelPath() {
        return "//" + getLabelTag() + "[text()=" + Utils.getEscapeQuotesText(getLabel()) + "]"; // new Label(getLabel()).getPath()
    }
}