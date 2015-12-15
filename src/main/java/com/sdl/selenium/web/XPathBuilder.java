package com.sdl.selenium.web;

import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.utils.Utils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

/**
 * This class is used to simple construct xpath for WebLocator's
 */
public class XPathBuilder implements Cloneable {
    private static final Logger LOGGER = LoggerFactory.getLogger(XPathBuilder.class);
    public List<SearchType> defaultSearchTextType = new ArrayList<>();
    private String className = "WebLocator";
    private String root = "//";
    private String tag = "*";
    private String id;
    private String elPath;
    private String elCssSelector;
    private String baseCls;
    private String cls;
    private List<String> classes;
    private List<String> excludeClasses;
    private String name;
    private String text;
    private List<SearchType> searchTextType = WebLocatorConfig.getSearchTextType();
    private List<SearchType> searchTitleType = new ArrayList<>();
    private List<SearchType> searchLabelType = new ArrayList<>();
    private String style;
    private String title;
    private Map<String, String> templates = new LinkedHashMap<>();
    private Map<String, WebLocator> templateTitle = new LinkedHashMap<>();
    private Map<String, String> templatesValues = new LinkedHashMap<>();
    private Map<String, String> elPathSuffix = new LinkedHashMap<>();

    private String infoMessage;

    private String label;
    private String labelTag = "label";
    private String labelPosition = WebLocatorConfig.getDefaultLabelPosition();

    private int position = -1;
    private int resultIdx = -1;
    private String type;
    private Map<String, SearchText> attribute = new LinkedHashMap<>();

    //private int elIndex; // TODO try to find how can be used

    private boolean visibility;
    private long renderMillis = WebLocatorConfig.getDefaultRenderMillis();
    private int activateSeconds = 60;

    private WebLocator container;
    private List<WebLocator> childNodes;

    protected XPathBuilder() {
        setTemplate("visibility", "count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0");
        setTemplate("id", "@id='%s'");
        setTemplate("name", "@name='%s'");
        setTemplate("class", "contains(concat(' ', @class, ' '), ' %s ')");
        setTemplate("excludeClass", "not(contains(@class, '%s'))");
        setTemplate("cls", "@class='%s'");
        setTemplate("type", "@type='%s'");
        setTemplate("title", "@title='%s'");
    }

    // =========================================
    // ========== setters & getters ============
    // =========================================

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setRoot(String)}
     * <p>root </p>
     * <pre>default to "//"</pre>
     */
    public String getRoot() {
        return root;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param root If the path starts with // then all elements in the document which fulfill following criteria are selected. eg. // or /
     * @param <T>  the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setRoot(final String root) {
        this.root = root;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setTag(String)}
     * <p>tag (type of DOM element)</p>
     * <pre>default to "*"</pre>
     */
    public String getTag() {
        return tag;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param tag (type of DOM element) eg. input or h2
     * @param <T> the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setTag(final String tag) {
        this.tag = tag;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setId(String)}
     */
    public String getId() {
        return id;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param id  eg. id="buttonSubmit"
     * @param <T> the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setId(final String id) {
        this.id = id;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setElPath(String)}
     * <p>returned value does not include containers path</p>
     */
    public String getElPath() {
        return elPath;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * Once used all other attributes will be ignored. Try using this class to a minimum!
     *
     * @param elPath absolute way (xpath) to identify element
     * @param <T>    the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setElPath(final String elPath) {
        this.elPath = elPath;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate css selectors address)</i></b></p>
     *
     * @return value that has been set in {@link #setElCssSelector(String)}
     * <p>returned value does not include containers path</p>
     */
    public String getElCssSelector() {
        return elCssSelector;
    }

    /**
     * <p><b>Used for finding element process (to generate css selectors address)</b></p>
     * Once used all other attributes will be ignored. Try using this class to a minimum!
     *
     * @param elCssSelector absolute way (css selectors) to identify element
     * @param <T>           the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setElCssSelector(final String elCssSelector) {
        this.elCssSelector = elCssSelector;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setBaseCls(String)}
     */
    public String getBaseCls() {
        return baseCls;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param baseCls base class
     * @param <T>     the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setBaseCls(final String baseCls) {
        this.baseCls = baseCls;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setCls(String)}
     */
    public String getCls() {
        return cls;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p>Find element with <b>exact math</b> of specified class (equals)</p>
     *
     * @param cls class of element
     * @param <T> the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setCls(final String cls) {
        this.cls = cls;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     * <p>Example:</p>
     * <pre>
     *     WebLocator element = new WebLocator().setClasses("bg-btn", "new-btn");
     * </pre>
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
     * @param <T>     the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setClasses(final String... classes) {
        if (classes != null) {
            this.classes = Arrays.asList(classes);
        }
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setExcludeClasses(String...)}
     */
    public List<String> getExcludeClasses() {
        return excludeClasses;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param excludeClasses list of class to be excluded
     * @param <T>            the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setExcludeClasses(final String... excludeClasses) {
        if (excludeClasses != null) {
            this.excludeClasses = Arrays.asList(excludeClasses);
        }
        return (T) this;
    }

    public List<WebLocator> getChildNodes() {
        return childNodes;
    }

    public <T extends XPathBuilder> T setChildNodes(final WebLocator... childNodes) {
        if (childNodes != null) {
            this.childNodes = Arrays.asList(childNodes);
        }
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setName(String)}
     */
    public String getName() {
        return name;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param name eg. name="buttonSubmit"
     * @param <T>  the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setName(final String name) {
        this.name = name;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setText(String, SearchType...)}
     */
    public String getText() {
        return text;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param text       with which to identify the item
     * @param searchType type search text element: see more details see {@link SearchType}
     * @param <T>        the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setText(final String text, final SearchType... searchType) {
        this.text = text;
//        notSupportedForCss(text, "text");
//        if(text == null) {
//            xpath.remove("text");
//        } else {
//            xpath.add("text");
//        }
        if (searchType != null && searchType.length > 0) {
            setSearchTextType(searchType);
        } else {
            this.searchTextType.addAll(defaultSearchTextType);
            this.searchTextType = cleanUpSearchType(this.searchTextType);
        }
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setSearchTextType(SearchType...)}
     */
    public List<SearchType> getSearchTextType() {
        return searchTextType;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * This method reset searchTextType and set to new searchTextType.
     *
     * @param searchTextType accepted values are: {@link SearchType#EQUALS}
     * @param <T>            the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setSearchTextType(SearchType... searchTextType) {
        if (searchTextType == null) {
            this.searchTextType = WebLocatorConfig.getSearchTextType();
        } else {
            this.searchTextType = new ArrayList<>();
            Collections.addAll(this.searchTextType, searchTextType);
        }
        this.searchTextType.addAll(defaultSearchTextType);
        this.searchTextType = cleanUpSearchType(this.searchTextType);
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * This method add new searchTextType to existing searchTextType.
     *
     * @param searchTextType accepted values are: {@link SearchType#EQUALS}
     * @param <T>            the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T addSearchTextType(SearchType... searchTextType) {
        if (searchTextType != null) {
            Collections.addAll(this.searchTextType, searchTextType);
        }
        this.searchTextType = cleanUpSearchType(this.searchTextType);
        return (T) this;
    }

    protected List<SearchType> cleanUpSearchType(List<SearchType> searchTextTypes) {
        Map<String, List<SearchType>> groups = getSearchTypesByGroups(searchTextTypes);

        List<SearchType> result = new ArrayList<>();
        for(Map.Entry<String, List<SearchType>> entry: groups.entrySet()) {
            List<SearchType> searchTypes = entry.getValue();
            if (searchTypes != null && !searchTypes.isEmpty()) {
                SearchType searchType = searchTypes.get(searchTypes.size() - 1);
                result.add(searchType);
            }
        }
        return result;
    }

    private Map<String, List<SearchType>> getSearchTypesByGroups(List<SearchType> searchTextTypes) {
        Map<String, List<SearchType>> groups = new HashMap<>();

        for(SearchType searchType : searchTextTypes) {
            if(groups.get(searchType.getGroup()) == null) {
                groups.put(searchType.getGroup(), new ArrayList<SearchType>());
            }
            List<SearchType> group = groups.get(searchType.getGroup());
            group.add(searchType);
            groups.put(searchType.getGroup(), group);
        }
        return groups;
    }

    /*public static void main(String[] args) {
        List<SearchType> searchTextType = new ArrayList<>();
        searchTextType.add(SearchType.CASE_SENSITIVE);
        searchTextType.add(SearchType.TRIM);
        searchTextType.add(SearchType.CASE_INSENSITIVE);
        searchTextType.remove(SearchType.CASE_INSENSITIVE);
        searchTextType.remove(SearchType.TRIM);
        searchTextType.add(SearchType.TRIM);
        searchTextType.add(SearchType.CASE_INSENSITIVE);
        searchTextType.add(SearchType.NO_TRIM);
        searchTextType.add(SearchType.TRIM);
        searchTextType.add(SearchType.EQUALS);
        searchTextType.remove(SearchType.EQUALS);
        searchTextType.add(SearchType.STARTS_WITH);
        searchTextType.add(SearchType.CONTAINS);
        searchTextType.remove(SearchType.CONTAINS);
        LOGGER.debug(new XPathBuilder().cleanUpSearchType(searchTextType).toString());
    }*/

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setSearchLabelType(SearchType...)}
     */
    public List<SearchType> getSearchLabelType() {
        return searchLabelType;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param searchLabelType accepted values are: {@link SearchType}
     * @param <T>             the element which calls this method
     * @return this element
     */
    private <T extends XPathBuilder> T setSearchLabelType(SearchType... searchLabelType) {
        this.searchLabelType = new ArrayList<>();
        if (searchLabelType != null) {
            Collections.addAll(this.searchLabelType, searchLabelType);
        }
        this.searchLabelType = cleanUpSearchType(this.searchLabelType);
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setStyle(String)}
     */
    public String getStyle() {
        return style;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param style of element
     * @param <T>   the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setStyle(final String style) {
        this.style = style;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     * <p><b>Title only applies to Panel, and if you set the item "setTemplate("title", "@title='%s'")" a template.</b></p>
     *
     * @return value that has been set in {@link #setTitle(String, SearchType...)}
     */
    public String getTitle() {
        return title;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param title of element
     * @param <T>   the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setTitle(final String title, final SearchType... searchType) {
        this.title = title;
        if (searchType != null && searchType.length > 0) {
            setSearchTitleType(searchType);
        } else {
            this.searchTitleType.addAll(defaultSearchTextType);
        }
        return (T) this;
    }

    public <T extends XPathBuilder> T setSearchTitleType(SearchType... searchTitleType) {
        if (searchTitleType == null) {
            this.searchTitleType = WebLocatorConfig.getSearchTextType();
        } else {
            this.searchTitleType = new ArrayList<>();
            Collections.addAll(this.searchTitleType, searchTitleType);
        }
        this.searchTitleType.addAll(defaultSearchTextType);
        this.searchTitleType = cleanUpSearchType(this.searchTitleType);
        return (T) this;
    }

    public List<SearchType> getSearchTitleType() {
        return searchTitleType;
    }

    public <T extends XPathBuilder> T setTemplateTitle(WebLocator titleEl) {
        if (titleEl == null) {
            templateTitle.remove("title");
        } else {
            templateTitle.put("title", titleEl);
        }
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p>Example:</p>
     * <pre>
     *     TODO
     * </pre>
     *
     * @param key          suffix key
     * @param elPathSuffix additional identification xpath element that will be added at the end
     * @param <T>          the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setElPathSuffix(final String key, final String elPathSuffix) {
        if (elPathSuffix == null || "".equals(elPathSuffix)) {
            this.elPathSuffix.remove(key);
        } else {
            this.elPathSuffix.put(key, elPathSuffix);
        }
        return (T) this;
    }

    public Map<String, String> getTemplatesValues() {
        return templatesValues;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p>Example:</p>
     * <pre>
     *     TODO
     * </pre>
     *
     * @param key   template
     * @param value template
     * @param <T>   the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setTemplateValue(final String key, final String value) {
        if (value == null) {
            this.templatesValues.remove(key);
        } else {
            this.templatesValues.put(key, value);
        }
        return (T) this;
    }

    /**
     * For customize template please see here: See http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#dpos
     *
     * @param key   name template
     * @param value template
     * @param <T>   the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setTemplate(final String key, final String value) {
        if (value == null) {
            templates.remove(key);
        } else {
            templates.put(key, value);
        }
        return (T) this;
    }

    public <T extends XPathBuilder> T addToTemplate(final String key, final String value) {
        String template = getTemplate(key);
        if (StringUtils.isNotEmpty(template)) {
            template += " and ";
        } else {
            template = "";
        }
        setTemplate(key, template + value);
        return (T) this;
    }

    public String getTemplate(final String key) {
        return templates.get(key);
    }

    /**
     * <p><b><i>Used in logging process</i></b></p>
     *
     * @return value that has been set in {@link #setInfoMessage(String)}
     */
    public String getInfoMessage() {
        return infoMessage;
    }

    /**
     * <p><b><i>Used in logging process</i></b></p>
     *
     * @param infoMessage info Message
     * @param <T>         the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setInfoMessage(final String infoMessage) {
        this.infoMessage = infoMessage;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setVisibility(boolean)}
     */
    public boolean isVisibility() {
        return visibility;
    }

    public <T extends XPathBuilder> T setVisibility(final boolean visibility) {
        this.visibility = visibility;
        return (T) this;
    }

    public long getRenderMillis() {
        return renderMillis;
    }

    public <T extends XPathBuilder> T setRenderMillis(final long renderMillis) {
        this.renderMillis = renderMillis;
        return (T) this;
    }

    public int getActivateSeconds() {
        return activateSeconds;
    }

    public <T extends XPathBuilder> T setActivateSeconds(final int activateSeconds) {
        this.activateSeconds = activateSeconds;
        return (T) this;
    }

    // TODO verify what type must return
    public WebLocator getContainer() {
        return container;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param container parent containing element.
     * @param <T>       the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setContainer(WebLocator container) {
        this.container = container;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setLabel(String, SearchType...)}
     */
    public String getLabel() {
        return label;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param label      text label element
     * @param searchType type search text element: see more details see {@link SearchType}
     * @param <T>        the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setLabel(final String label, final SearchType... searchType) {
        this.label = label;
        if (searchType != null && searchType.length > 0) {
            setSearchLabelType(searchType);
        }
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setLabel(String, SearchType...)}
     */
    public String getLabelTag() {
        return labelTag;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param labelTag label tag element
     * @param <T>      the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setLabelTag(final String labelTag) {
        this.labelTag = labelTag;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setLabelPosition(String)}
     */
    public String getLabelPosition() {
        return labelPosition;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param labelPosition position of this element reported to label
     * @param <T>           the element which calls this method
     * @return this element
     * @see <a href="http://www.w3schools.com/xpath/xpath_axes.asp">http://www.w3schools.com/xpath/xpath_axes.asp"</a>
     */
    public <T extends XPathBuilder> T setLabelPosition(final String labelPosition) {
        this.labelPosition = labelPosition;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setPosition(int)}
     */
    public int getPosition() {
        return position;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p>Result Example:</p>
     * <pre>
     *     //*[contains(@class, 'x-grid-panel')][position() = 1]
     * </pre>
     *
     * @param position starting index = 1
     * @param <T>      the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setPosition(final int position) {
        this.position = position;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setPosition(int)}
     */
    public int getResultIdx() {
        return resultIdx;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p>Result Example:</p>
     * <pre>
     *     (//*[contains(@class, 'x-grid-panel')])[1]
     * </pre>
     * More details please see: http://stackoverflow.com/questions/4961349/combine-xpath-predicate-with-position
     *
     * @param resultIdx starting index = 1
     * @param <T>       the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setResultIdx(final int resultIdx) {
        this.resultIdx = resultIdx;
        return (T) this;
    }

    /**
     * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
     *
     * @return value that has been set in {@link #setType(String)}
     */
    public String getType() {
        return type;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p>Result Example:</p>
     * <pre>
     *     //*[@type='type']
     * </pre>
     *
     * @param type eg. 'checkbox' or 'button'
     * @param <T>  the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setType(final String type) {
        this.type = type;
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p>Result Example:</p>
     * <pre>
     *     //*[@placeholder='Search']
     * </pre>
     *
     * @param attribute   eg. placeholder
     * @param value       eg. Search
     * @param searchTypes accept only SearchType.EQUALS, SearchType.CONTAINS, SearchType.STARTS_WITH, SearchType.TRIM
     * @param <T>         the element which calls this method
     * @return this element
     */
    public <T extends XPathBuilder> T setAttribute(final String attribute, final String value, final SearchType... searchTypes) {
        if (attribute != null) {
            if (value == null) {
                this.attribute.remove(attribute);
            } else {
                this.attribute.put(attribute, new SearchText(value, searchTypes));
            }
        }
        return (T) this;
    }

    // =========================================
    // =============== Methods =================
    // =========================================

    /**
     * <p>Used only to identify class type of current object</p>
     * <p> Not used for css class!</p>
     *
     * @return string
     */
    public String getClassName() {
        return className;
    }

    protected void setClassName(final String className) {
        this.className = className;
    }

    protected boolean hasId() {
        return id != null && !id.equals("");
    }

    protected boolean hasCls() {
        return cls != null && !cls.equals("");
    }

    protected boolean hasClasses() {
        return classes != null && classes.size() > 0;
    }

    protected boolean hasChildNodes() {
        return childNodes != null && childNodes.size() > 0;
    }

    protected boolean hasExcludeClasses() {
        return excludeClasses != null && excludeClasses.size() > 0;
    }

    protected boolean hasBaseCls() {
        return baseCls != null && !baseCls.equals("");
    }

    protected boolean hasName() {
        return name != null && !name.equals("");
    }

    protected boolean hasText() {
        return text != null && !text.equals("");
    }

    protected boolean hasStyle() {
        return style != null && !style.equals("");
    }

    protected boolean hasElPath() {
        return elPath != null && !elPath.equals("");
    }

    protected boolean hasTag() {
        return tag != null && !tag.equals("*");
    }

    protected boolean hasLabel() {
        return label != null && !label.equals("");
    }

    protected boolean hasTitle() {
        return title != null && !title.equals("");
    }

    protected boolean hasPosition() {
        return position > 0;
    }

    protected boolean hasResultIdx() {
        return resultIdx > 0;
    }

    protected boolean hasType() {
        return type != null && !type.equals("");
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
        List<String> selector = new ArrayList<String>();
        CollectionUtils.addIgnoreNull(selector, getBasePath());
        CollectionUtils.addIgnoreNull(selector, getItemPathText());

        if (!WebDriverConfig.isIE()) {
            if (hasStyle()) {
                selector.add("contains(@style ,'" + getStyle() + "')");
            }
            // TODO make specific for WebLocator
            if (isVisibility()) {
//               TODO selector.append(" and count(ancestor-or-self::*[contains(replace(@style, '\s*:\s*', ':'), 'display:none')]) = 0");
                CollectionUtils.addIgnoreNull(selector, applyTemplate("visibility", isVisibility()));
            }
        }

        return selector.isEmpty() ? "" : StringUtils.join(selector, " and ");
    }

    public String getBasePath() {
        List<String> selector = new ArrayList<String>();
        if (hasId()) {
            selector.add(applyTemplate("id", getId()));
        }
        if (hasName()) {
            selector.add(applyTemplate("name", getName()));
        }
        if (hasBaseCls()) {
            selector.add(applyTemplate("class", getBaseCls()));
        }
        if (hasCls()) {
            selector.add(applyTemplate("cls", getCls()));
        }
        if (hasClasses()) {
            for (String cls : getClasses()) {
                selector.add(applyTemplate("class", cls));
            }
        }
        if (hasExcludeClasses()) {
            for (String excludeClass : getExcludeClasses()) {
                selector.add(applyTemplate("excludeClass", excludeClass));
            }
        }
        if (hasTitle()) {
            WebLocator titleTplEl = templateTitle.get("title");
            if (titleTplEl != null) {
                titleTplEl.setText(getTitle(), searchTitleType.toArray(new SearchType[searchTitleType.size()]));
                setTemplate("title", "count(.%s) > 0");
                addTemplate(selector, "title", titleTplEl.getXPath());
            } else if (!searchTitleType.isEmpty()) {
                boolean hasContainsAll = searchTitleType.contains(SearchType.CONTAINS_ALL);
                String title = getTextAfterEscapeQuotes(hasContainsAll, getTitle(), searchTitleType);
                selector.add(getTextSearchTypePath(searchTitleType, title, hasContainsAll, "@title"));
            } else {
                addTemplate(selector, "title", getTitle());
            }
        }
        if (hasType()) {
            addTemplate(selector, "type", getType());
        }
        if (!attribute.isEmpty()) {
            for (Map.Entry<String, SearchText> entry : attribute.entrySet()) {
                List<SearchType> searchType = entry.getValue().getSearchType();
                boolean hasContainsAll = searchType.contains(SearchType.CONTAINS_ALL);
                String text = getTextAfterEscapeQuotes(hasContainsAll, entry.getValue().getText(), searchType);
                selector.add(getTextSearchTypePath(searchType, text, hasContainsAll, "@" + entry.getKey()));
            }
        }
        for (Map.Entry<String, String> entry : getTemplatesValues().entrySet()) {
            addTemplate(selector, entry.getKey(), entry.getValue());
        }
        for (String suffix : elPathSuffix.values()) {
            selector.add(suffix);
        }
        selector.addAll(getChildNodesToSelector());
        return selector.isEmpty() ? null : StringUtils.join(selector, " and ");
    }


    private List<String> getChildNodesToSelector() {
        List<String> selector = new ArrayList<>();
        if (hasChildNodes()) {
            for (WebLocator child : getChildNodes()) {
                selector.add(getChildNodeSelector(child));
            }
        }
        return selector;
    }

    private String getChildNodeSelector(WebLocator child) {
        WebLocator breakElement = null;
        WebLocator childIterator = child;
        WebLocator parentElement = null;
        // break parent tree if is necessary
        while (childIterator.getPathBuilder().getContainer() != null && breakElement == null) {
            WebLocator parentElementIterator = childIterator.getPathBuilder().getContainer();

            // child element has myself as parent
            if (parentElementIterator.getPathBuilder() == this) {
                childIterator.setContainer(null); // break parent tree while generating child address
                parentElement = parentElementIterator;
                breakElement = childIterator;
            } else {
                childIterator = parentElementIterator;
            }
        }

        String selector = "count(." + child.getXPath() + ") > 0";
        if (breakElement != null) {
            breakElement.setContainer(parentElement);
        }
        return selector;
    }

    private void addTemplate(List<String> selector, String key, Object... arguments) {
        String tpl = applyTemplate(key, arguments);
        if (StringUtils.isNotEmpty(tpl)) {
            selector.add(tpl);
        }
    }

    protected String applyTemplate(String key, Object... arguments) {
        String tpl = templates.get(key);
        if (StringUtils.isNotEmpty(tpl)) {
            return String.format(tpl, arguments);
        }
        return null;
    }

    private String applyTemplateValue(String key) {
        return applyTemplate(key, getTemplate(key));
    }

    /**
     * this method is meant to be overridden by each component
     *
     * @param disabled disabled
     * @return String
     */
    protected String getItemPath(boolean disabled) {
        String selector = getBaseItemPath();
        String subPath = applyTemplateValue(disabled ? "disabled" : "enabled");
        if (subPath != null) {
            selector += StringUtils.isNotEmpty(selector) ? " and " + subPath : subPath;
        }
        selector = getRoot() + getTag() + (StringUtils.isNotEmpty(selector) ? "[" + selector + "]" : "");
        return selector;
    }

    /**
     * Construct selector if WebLocator has text
     *
     * @return String
     */
    protected String getItemPathText() {
        String selector = null;
        if (hasText()) {
            selector = "";
            String text = getText();

            if (templates.get("text") != null) {
                return String.format(templates.get("text"), text);
            }
            boolean isDeepSearch = searchTextType.contains(SearchType.DEEP_CHILD_NODE) || searchTextType.contains(SearchType.DEEP_CHILD_NODE_OR_SELF);
            boolean useChildNodesSearch = isDeepSearch || searchTextType.contains(SearchType.CHILD_NODE);
            String pathText = "text()";
            if (useChildNodesSearch) {
                selector += "count(" + (isDeepSearch ? "*//" : "") + pathText + "[";
                pathText = ".";
            }
            boolean hasContainsAll = searchTextType.contains(SearchType.CONTAINS_ALL) || searchTextType.contains(SearchType.CONTAINS_ALL_CHILD_NODES);
            text = getTextAfterEscapeQuotes(hasContainsAll, text, searchTextType);
            selector += getTextSearchTypePath(searchTextType, text, hasContainsAll, pathText);

            if (useChildNodesSearch) {
                selector += "]) > 0";
            }

            if (searchTextType.contains(SearchType.DEEP_CHILD_NODE_OR_SELF)) {
                String selfPath = getTextSearchTypePath(searchTextType, text, hasContainsAll, ".");
                selector = "(" + selfPath + " or " + selector + ")";
            }

            if (searchTextType.contains(SearchType.HTML_NODE)) {
                String a = "normalize-space(concat(./*[1]//text(), ' ', text()[1], ' ', ./*[2]//text(), ' ', text()[2], ' ', ./*[3]//text(), ' ', text()[3], ' ', ./*[4]//text(), ' ', text()[4], ' ', ./*[5]//text(), ' ', text()[5]))=" + text;
                String b = "normalize-space(concat(text()[1], ' ', ./*[1]//text(), ' ', text()[2], ' ', ./*[2]//text(), ' ', text()[3], ' ', ./*[3]//text(), ' ', text()[4], ' ', ./*[4]//text(), ' ', text()[5], ' ', ./*[5]//text()))=" + text;

                selector = "(" + a + " or " + b + ")";
            }
        }
        return selector;
    }

    private String getTextSearchTypePath(List<SearchType> searchType, String text, boolean hasContainsAll, String pathText) {
        String selector;
        if (searchType.contains(SearchType.TRIM)) {
            pathText = "normalize-space(" + pathText + ")";
        }
        if (searchType.contains(SearchType.CASE_INSENSITIVE)) {
            pathText = "translate(" + pathText + "," + text.toUpperCase().replaceAll("CONCAT\\(", "concat(") + "," + text.toLowerCase() + ")";
            text = text.toLowerCase();
        }

        if (searchType.contains(SearchType.EQUALS)) {
            selector = pathText + "=" + text;
        } else if (searchType.contains(SearchType.STARTS_WITH)) {
            selector = "starts-with(" + pathText + "," + text + ")";
        } else if (hasContainsAll || searchType.contains(SearchType.CONTAINS_ANY)) {
            String splitChar = String.valueOf(text.charAt(0));
            Pattern pattern = Pattern.compile(Pattern.quote(splitChar));
            String[] strings = pattern.split(text.substring(1));
            for (int i = 0; i < strings.length; i++) {
                String escapeQuotesText = Utils.getEscapeQuotesText(strings[i]);
                if (searchType.contains(SearchType.CONTAINS_ALL_CHILD_NODES)) {
                    strings[i] = "count(*//text()[contains(.," + escapeQuotesText + ")]) > 0";
                } else {
                    strings[i] = "contains(" + pathText + "," + escapeQuotesText + ")";
                }
            }
            String operator = hasContainsAll ? " and " : " or ";
            selector = hasContainsAll ? StringUtils.join(strings, operator) : "(" + StringUtils.join(strings, operator) + ")";
        } else {
            selector = "contains(" + pathText + "," + text + ")";
        }
        return selector;
    }

    private String getTextAfterEscapeQuotes(boolean hasContainsAll, String text, List<SearchType> searchType) {
        if (hasContainsAll || searchType.contains(SearchType.CONTAINS_ANY)) {
            return text;
        }
        return Utils.getEscapeQuotesText(text);
    }

    private String getBaseItemPath() {
        return getBasePathSelector();
    }

    private String getItemCssSelector() {
        List<String> selector = new ArrayList<String>();
        if (hasTag()) {
            selector.add(getTag());
        }
        if (hasId()) {
            selector.add("#" + getId());
        }
        if (hasBaseCls()) {
            selector.add("." + getBaseCls());
        }
        if (hasCls()) {
            selector.add("." + getCls()); //TODO
        }
        if (hasClasses()) {
            for (String cls : getClasses()) {
                selector.add("." + cls);
            }
        }
        if (hasExcludeClasses()) {
            LOGGER.warn("excludeClasses is not supported yet");
//            for (String excludeClass : getExcludeClasses()) {
//                selector.add(applyTemplate("excludeClass", excludeClass));
//            }
        }
        if (hasName()) {
            selector.add("[name='" + getName() + "']");
        }
        if (hasType()) {
            selector.add("[type='" + getType() + "']");
        }
        if (!attribute.isEmpty()) {
            for (Map.Entry<String, SearchText> entry : attribute.entrySet()) {
                selector.add("[" + entry.getKey() + "='" + entry.getValue().getText() + "']");
            }
        }
//        for (Map.Entry<String, String> entry : getTemplatesValues().entrySet()) {
//            addTemplate(selector, entry.getKey(), entry.getValue());
//        }
//        for (String suffix : elPathSuffix.values()) {
//            selector.add(suffix);
//        }
        return selector.isEmpty() ? "*" : StringUtils.join(selector, "");
    }

    public final By getSelector() {
        String cssSelector = getCssSelector();
        return StringUtils.isNotEmpty(cssSelector) ? By.cssSelector(cssSelector) : By.xpath(getXPath());
    }

    private boolean isCssSelectorSupported() {
        return !(hasText() || hasElPath() || hasChildNodes() || hasExcludeClasses() || hasStyle() || hasLabel() || hasTitle() || hasPosition() || hasResultIdx());
    }

    public final String getCssSelector() {
        String cssSelector = null;

        cssSelector = getElCssSelector();
        if (WebLocatorConfig.isGenerateCssSelector()) {
            if (StringUtils.isEmpty(cssSelector)) {
                if (isCssSelectorSupported()) {
                    cssSelector = getItemCssSelector();
                }
            } else {
//            String baseCssSelector = getItemCssSelector();
//            if (StringUtils.isNotEmpty(baseCssSelector)) {
//                 TODO "inject" baseItemPath to elPath
//            }
            }
        }
        // add container path
        if (cssSelector != null && getContainer() != null) {
            String parentCssSelector = getContainer().getCssSelector();
            if (StringUtils.isEmpty(parentCssSelector)) {
                LOGGER.warn("Can't generate css selector for parent: {}", getContainer());
                cssSelector = null;
            } else {
                String root = getRoot();
                String deep = "";
                if (StringUtils.isNotEmpty(root)) {
                    if (root.equals("/")) {
                        deep = " > ";
                    } else if (root.equals("//")) {
                        deep = " ";
                    } else {
                        LOGGER.warn("this root ({}) is no implemented in css selector: ", root);
                    }
                }
                cssSelector = parentCssSelector + deep + cssSelector;
            }
        }

        return cssSelector;
    }

    /**
     * @return final xpath (including containers xpath), used for interacting with browser
     */
    public final String getXPath() {
        return getXPath(false);
    }

    public final String getXPath(boolean disabled) {
        String returnPath;
        if (hasElPath()) {
            returnPath = getElPath();

            String baseItemPath = getBaseItemPath();
            if (baseItemPath != null && !baseItemPath.equals("")) {
                // TODO "inject" baseItemPath to elPath
            }
        } else {
            returnPath = getItemPath(disabled);
        }

        returnPath = afterItemPathCreated(returnPath);

        // add container path
        if (getContainer() != null) {
            returnPath = getContainer().getXPath() + returnPath;
        }
        return addResultIndexToPath(returnPath);
    }

    /**
     * @deprecated use getXPath()
     */
    public final String getPath() {
        return getXPath();
    }

    /**
     * @param disabled disabled
     * @return String
     * @deprecated use getXPath(boolean disabled)
     */
    public String getPath(boolean disabled) {
        return getXPath(disabled);
    }

    private String addResultIndexToPath(String finalPath) {
        if (hasResultIdx()) {
            finalPath = "(" + finalPath + ")[" + getResultIdx() + "]";
        }
        return finalPath;
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
        } else if (hasElPath()) {
            info = getElPath();
        } else if (hasTag()) {
            info = getTag();
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
        if (searchLabelType.size() == 0) {
            searchLabelType.add(SearchType.EQUALS);
        }
        SearchType[] st = searchLabelType.toArray(new SearchType[searchLabelType.size()]);
        return new WebLocator().setText(getLabel(), st).setTag(getLabelTag()).getXPath();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        XPathBuilder builder = (XPathBuilder) super.clone();

        LinkedHashMap<String, String> templates = (LinkedHashMap<String, String>) builder.templates;
        LinkedHashMap<String, WebLocator> templateTitle = (LinkedHashMap<String, WebLocator>) builder.templateTitle;
        LinkedHashMap<String, String> templatesValues = (LinkedHashMap<String, String>) builder.templatesValues;
        LinkedHashMap<String, String> elPathSuffix = (LinkedHashMap<String, String>) builder.elPathSuffix;

        builder.templates = (Map<String, String>) templates.clone();
        builder.templatesValues = (Map<String, String>) templatesValues.clone();
        builder.elPathSuffix = (Map<String, String>) elPathSuffix.clone();

        builder.templateTitle = (Map<String, WebLocator>) templateTitle.clone();
        WebLocator titleTplEl = templateTitle.get("title");
        if (titleTplEl != null) {
            XPathBuilder titleTplElBuilder = (XPathBuilder) titleTplEl.getPathBuilder().clone();
            WebLocator titleTplElCloned = new WebLocator().setPathBuilder(titleTplElBuilder);
            builder.templateTitle.put("title", titleTplElCloned);
        }

        return builder;
    }
}