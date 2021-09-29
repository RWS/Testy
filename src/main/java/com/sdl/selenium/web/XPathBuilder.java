package com.sdl.selenium.web;

import com.google.common.base.Strings;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.utils.Utils;
import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is used to simple construct xpath for WebLocator's
 */
public class XPathBuilder implements Cloneable {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(XPathBuilder.class);
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
    private Map<String, String[]> templatesValues = new LinkedHashMap<>();
    private Map<String, String> elPathSuffix = new LinkedHashMap<>();

    private String infoMessage;

    private String label;
    private String labelTag = "label";
    private String labelPosition = WebLocatorConfig.getDefaultLabelPosition();

    private String position;
    private String resultIdx;
    private String type;
    private String localName;
    private Map<String, SearchText> attribute = new LinkedHashMap<>();
    private Map<String, String[]> attributes = new LinkedHashMap<>();

    //private int elIndex; // TODO try to find how can be used

    private boolean visibility;
    private long renderMillis = WebLocatorConfig.getDefaultRenderMillis();
    private Duration render = Duration.ofMillis(WebLocatorConfig.getDefaultRenderMillis());
    private int activateSeconds = 60;
    private Duration activate = Duration.ofSeconds(60);

    private WebLocator container;
    private List<WebLocator> childNodes;

    protected XPathBuilder() {
        setTemplate("visibility", "count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0");
        setTemplate("style", "contains(@style, '%s')");
        setTemplate("id", "@id='%s'");
        setTemplate("position", "[position() = %s]");
        setTemplate("tagAndPosition", "%1$s[%2$s]");
        setTemplate("name", "@name='%s'");
        setTemplate("localName", "name()='%s'");
        setTemplate("class", "contains(concat(' ', @class, ' '), ' %s ')");
        setTemplate("excludeClass", "not(contains(@class, '%s'))");
        setTemplate("cls", "@class='%s'");
        setTemplate("type", "@type='%s'");
        setTemplate("title", "@title='%s'");
        setTemplate("titleEl", "count(.%s) > 0");
        setTemplate("DEEP_CHILD_NODE_OR_SELF", "(%1$s or count(*//text()[%1$s]) > 0)");
        setTemplate("DEEP_CHILD_NODE", "count(*//text()[%s]) > 0");
        setTemplate("CHILD_NODE", "count(text()[%s]) > 0");
        setTemplate("HTML_NODE", "%s");
        setTemplate("HTML_NODES", "(normalize-space(concat(./*[1]//text(), ' ', text()[1], ' ', ./*[2]//text(), ' ', text()[2], ' ', ./*[3]//text(), ' ', text()[3], ' ', ./*[4]//text(), ' ', text()[4], ' ', ./*[5]//text(), ' ', text()[5]))=%1$s or normalize-space(concat(text()[1], ' ', ./*[1]//text(), ' ', text()[2], ' ', ./*[2]//text(), ' ', text()[3], ' ', ./*[3]//text(), ' ', text()[4], ' ', ./*[4]//text(), ' ', text()[5], ' ', ./*[5]//text()))=%1$s)");
        setTemplate("childNodes", "count(.%s) > 0");
        setTemplate("attributes", "contains(concat(' ', @%1$s, ' '), ' %2$s ')");
    }

    // =========================================
    // ========== setters & getters ============
    // =========================================

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param root If the path starts with // then all elements in the document which fulfill following criteria are selected. eg. // or /
     * @param <T>  the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setRoot(final String root) {
        this.root = root;
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param tag (type of DOM element) eg. input or h2
     * @param <T> the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setTag(final String tag) {
        this.tag = tag;
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param id  eg. id="buttonSubmit"
     * @param <T> the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setId(final String id) {
        this.id = id;
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * Once used all other attributes will be ignored. Try using this class to a minimum!
     *
     * @param elPath absolute way (xpath) to identify element
     * @param <T>    the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setElPath(final String elPath) {
        this.elPath = elPath;
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate css selectors address)</b></p>
     * Once used all other attributes will be ignored. Try using this class to a minimum!
     *
     * @param elCssSelector absolute way (css selectors) to identify element
     * @param <T>           the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setElCssSelector(final String elCssSelector) {
        this.elCssSelector = elCssSelector;
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param baseCls base class
     * @param <T>     the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setBaseCls(final String baseCls) {
        this.baseCls = baseCls;
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p>Find element with <b>exact math</b> of specified class (equals)</p>
     *
     * @param cls class of element
     * @param <T> the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setCls(final String cls) {
        this.cls = cls;
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p>Use it when element must have all specified css classes (order is not important).</p>
     * <p>Example:</p>
     * <pre>
     *     WebLocator element = new WebLocator().setClasses("bg-btn", "new-btn");
     * </pre>
     * <ul>
     * <li>Provided classes must be conform css rules.</li>
     * </ul>
     *
     * @param classes list of classes
     * @param <T>     the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setClasses(final String... classes) {
        if (classes != null) {
            this.classes = Arrays.asList(classes);
        }
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param excludeClasses list of class to be excluded
     * @param <T>            the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setExcludeClasses(final String... excludeClasses) {
        if (excludeClasses != null) {
            this.excludeClasses = Arrays.asList(excludeClasses);
        }
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setChildNodes(final WebLocator... childNodes) {
        if (childNodes != null) {
            this.childNodes = Stream.of(childNodes).filter(Objects::nonNull).collect(Collectors.toList());
        }
//        if (childNodes != null) {
//            List<WebLocator> newList = this.childNodes == null ? new ArrayList<>() : this.childNodes;
//            List<WebLocator> webLocators = Arrays.asList(childNodes);
//            newList.addAll(webLocators);
//            this.childNodes = newList;
//        }
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param name eg. name="buttonSubmit"
     * @param <T>  the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setName(final String name) {
        this.name = name;
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param localName eg. name()="svg"
     * @param <T>       the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setLocalName(final String localName) {
        this.localName = localName;
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param text        with which to identify the item
     * @param searchTypes type search text element: see more details see {@link SearchType}
     * @param <T>         the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setText(final String text, final SearchType... searchTypes) {
        this.text = text;
//        notSupportedForCss(text, "text");
//        if(text == null) {
//            xpath.remove("text");
//        } else {
//            xpath.add("text");
//        }
        setSearchTextType(searchTypes);
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * This method reset searchTextTypes and set to new searchTextTypes.
     *
     * @param searchTextTypes accepted values are: {@link SearchType#EQUALS}
     * @param <T>             the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setSearchTextType(SearchType... searchTextTypes) {
        if (searchTextTypes == null) {
            this.searchTextType = WebLocatorConfig.getSearchTextType();
        } else {
            this.searchTextType.addAll(0, Arrays.asList(searchTextTypes));
        }
        this.searchTextType.addAll(defaultSearchTextType);
        this.searchTextType = cleanUpSearchType(this.searchTextType);
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * This method add new searchTextTypes to existing searchTextTypes.
     *
     * @param searchTextTypes accepted values are: {@link SearchType#EQUALS}
     * @param <T>             the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T addSearchTextType(SearchType... searchTextTypes) {
        if (searchTextTypes != null) {
            this.searchTextType.addAll(0, Arrays.asList(searchTextTypes));
        }
        this.searchTextType = cleanUpSearchType(this.searchTextType);
        return (T) this;
    }

    protected List<SearchType> cleanUpSearchType(List<SearchType> searchTextTypes) {
        List<SearchType> searchTypes = searchTextTypes;
        if (searchTextTypes.size() > 1) {
            Set<String> unique = new HashSet<>();
            searchTypes = searchTextTypes.stream()
                    .filter(c -> unique.add(c.getGroup()))
                    .collect(Collectors.toList());
        }
        return searchTypes;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param searchLabelTypes accepted values are: {@link SearchType}
     * @param <T>              the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setSearchLabelType(SearchType... searchLabelTypes) {
        this.searchLabelType = new ArrayList<>();
        if (searchLabelTypes != null) {
            this.searchLabelType.addAll(0, Arrays.asList(searchLabelTypes));
        }
        this.searchLabelType = cleanUpSearchType(this.searchLabelType);
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param style of element
     * @param <T>   the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setStyle(final String style) {
        this.style = style;
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p><b>Title only applies to Panel, and if you set the item "setTemplate("title", "@title='%s'")" a template.</b></p>
     *
     * @param title       of element
     * @param searchTypes see {@link SearchType}
     * @param <T>         the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setTitle(final String title, final SearchType... searchTypes) {
        this.title = title;
        if (searchTypes != null && searchTypes.length > 0) {
            setSearchTitleType(searchTypes);
        } else {
            this.searchTitleType.addAll(defaultSearchTextType);
        }
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setSearchTitleType(SearchType... searchTitleTypes) {
        if (searchTitleTypes == null) {
            this.searchTitleType = WebLocatorConfig.getSearchTextType();
        } else {
            this.searchTitleType.addAll(0, Arrays.asList(searchTitleTypes));
        }
        this.searchTitleType.addAll(defaultSearchTextType);
        this.searchTitleType = cleanUpSearchType(this.searchTitleType);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setTemplateTitle(WebLocator titleEl) {
        if (titleEl == null) {
            templateTitle.remove("title");
        } else {
            templateTitle.put("title", titleEl);
            setSearchTitleType(titleEl.getPathBuilder().getSearchTitleType().toArray(new SearchType[0]));
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
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setElPathSuffix(final String key, final String elPathSuffix) {
        if (Strings.isNullOrEmpty(elPathSuffix)) {
            this.elPathSuffix.remove(key);
        } else {
            this.elPathSuffix.put(key, elPathSuffix);
        }
        return (T) this;
    }

    public Map<String, String[]> getTemplatesValues() {
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
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setTemplateValue(final String key, final String... value) {
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
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setTemplate(final String key, final String value) {
        if (value == null) {
            templates.remove(key);
        } else {
            templates.put(key, value);
        }
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T addToTemplate(final String key, final String value) {
        String template = getTemplate(key);
        if (!Strings.isNullOrEmpty(template)) {
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
     * @param infoMessage info Message
     * @param <T>         the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setInfoMessage(final String infoMessage) {
        this.infoMessage = infoMessage;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setVisibility(final boolean visibility) {
        this.visibility = visibility;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Deprecated
    public <T extends XPathBuilder> T setRenderMillis(final long renderMillis) {
        this.renderMillis = renderMillis;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setRender(Duration duration) {
        this.render = duration;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Deprecated
    public <T extends XPathBuilder> T setActivateSeconds(final int activateSeconds) {
        this.activateSeconds = activateSeconds;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setActivate(Duration duration) {
        this.activate = duration;
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param container parent containing element.
     * @param <T>       the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setContainer(WebLocator container) {
        this.container = container;
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param label       text label element
     * @param searchTypes type search text element: see more details see {@link SearchType}
     * @param <T>         the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setLabel(final String label, final SearchType... searchTypes) {
        this.label = label;
        if (searchTypes != null && searchTypes.length > 0) {
            setSearchLabelType(searchTypes);
        }
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param labelTag label tag element
     * @param <T>      the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setLabelTag(final String labelTag) {
        this.labelTag = labelTag;
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     *
     * @param labelPosition position of this element reported to label
     * @param <T>           the element which calls this method
     * @return this element
     * @see <a href="http://www.w3schools.com/xpath/xpath_axes.asp">http://www.w3schools.com/xpath/xpath_axes.asp"</a>
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setLabelPosition(final String labelPosition) {
        this.labelPosition = labelPosition;
        return (T) this;
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
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setPosition(final int position) {
        this.position = position + "";
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p>Result Example:</p>
     * <pre>
     *     //*[contains(@class, 'x-grid-panel')][last()]
     * </pre>
     *
     * @param position {@link Position}
     * @param <T>      the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setPosition(final Position position) {
        this.position = position.getValue();
        return (T) this;
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
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setResultIdx(final int resultIdx) {
        this.resultIdx = resultIdx + "";
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p>Result Example:</p>
     * <pre>
     *     (//*[contains(@class, 'x-grid-panel')])[last()]
     * </pre>
     * More details please see: http://stackoverflow.com/questions/4961349/combine-xpath-predicate-with-position
     *
     * @param resultIdx {@link Position}
     * @param <T>       the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setResultIdx(final Position resultIdx) {
        this.resultIdx = resultIdx.getValue();
        return (T) this;
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setAttribute(final String attribute, String value, final SearchType... searchTypes) {
        if (attribute != null) {
            if (value == null) {
                this.attribute.remove(attribute);
            } else {
                this.attribute.put(attribute, new SearchText(value, searchTypes));
            }
        }
        return (T) this;
    }

    /**
     * <p><b>Used for finding element process (to generate xpath address)</b></p>
     * <p>Result Example:</p>
     * <pre>
     *     //*[@placeholder='Search']
     * </pre>
     *
     * @param attribute eg. placeholder
     * @param value     eg. Search
     * @param <T>       the element which calls this method
     * @return this element
     */
    @SuppressWarnings("unchecked")
    public <T extends XPathBuilder> T setAttributes(final String attribute, String... value) {
        if (attribute != null) {
            if (value == null) {
                this.attributes.remove(attribute);
            } else {
                this.attributes.put(attribute, value);
            }
        }
        return (T) this;
    }

    // =========================================
    // =============== Methods =================
    // =========================================

    protected void setClassName(final String className) {
        this.className = className;
    }

    protected boolean hasId() {
        return !Strings.isNullOrEmpty(id);
    }

    protected boolean hasCls() {
        return !Strings.isNullOrEmpty(cls);
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
        return !Strings.isNullOrEmpty(baseCls);
    }

    protected boolean hasName() {
        return !Strings.isNullOrEmpty(name);
    }

    protected boolean hasLocalName() {
        return !Strings.isNullOrEmpty(localName);
    }

    protected boolean hasText() {
        return !Strings.isNullOrEmpty(text);
    }

    protected boolean hasStyle() {
        return !Strings.isNullOrEmpty(style);
    }

    protected boolean hasElPath() {
        return !Strings.isNullOrEmpty(elPath);
    }

    protected boolean hasTag() {
        return tag != null && !"*".equals(tag);
    }

    protected boolean hasLabel() {
        return !Strings.isNullOrEmpty(label);
    }

    protected boolean hasTitle() {
        return !Strings.isNullOrEmpty(title) || !templateTitle.isEmpty();
    }

    protected boolean hasPosition() {
        int anInt;
        try {
            anInt = Integer.parseInt(position);
        } catch (NumberFormatException e) {
            anInt = 1;
        }
        return !Strings.isNullOrEmpty(position) && anInt > 0;
    }

    protected boolean hasResultIdx() {
        int anInt;
        try {
            anInt = Integer.parseInt(resultIdx);
        } catch (NumberFormatException e) {
            anInt = 1;
        }
        return !Strings.isNullOrEmpty(resultIdx) && anInt > 0;
    }

    protected boolean hasType() {
        return !Strings.isNullOrEmpty(type);
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
        List<String> selectors = new ArrayList<>();
        CollectionUtils.addIgnoreNull(selectors, getBasePath());

        if (!WebDriverConfig.isIE()) {
            if (hasStyle()) {
                selectors.add(applyTemplate("style", getStyle()));
            }
            // TODO make specific for WebLocator
            if (isVisibility()) {
//               TODO selectors.append(" and count(ancestor-or-self::*[contains(replace(@style, '\s*:\s*', ':'), 'display:none')]) = 0");
                CollectionUtils.addIgnoreNull(selectors, applyTemplate("visibility", isVisibility()));
            }
        }

        return selectors.isEmpty() ? "" : String.join(" and ", selectors);
    }

    public String getBasePath() {
        List<String> selectors = new ArrayList<>();
        if (hasId()) {
            selectors.add(applyTemplate("id", getId()));
        }
        if (hasName()) {
            selectors.add(applyTemplate("name", getName()));
        }
        if (hasLocalName()) {
            selectors.add(applyTemplate("localName", getLocalName()));
        }
        if (hasBaseCls()) {
            selectors.add(applyTemplate("class", getBaseCls()));
        }
        if (hasCls()) {
            selectors.add(applyTemplate("cls", getCls()));
        }
        if (hasClasses()) {
            selectors.addAll(getClasses().stream().map(cls -> applyTemplate("class", cls)).collect(Collectors.toList()));
        }
        if (hasExcludeClasses()) {
            selectors.addAll(getExcludeClasses().stream().map(excludeClass -> applyTemplate("excludeClass", excludeClass)).collect(Collectors.toList()));
        }
        if (hasTitle()) {
            String title = getTitle();
            WebLocator titleTplEl = templateTitle.get("title");
            if (!Strings.isNullOrEmpty(title) || titleTplEl != null) {
                if (titleTplEl != null) {
                    if (!Strings.isNullOrEmpty(title)) {
                        titleTplEl.setText(title, searchTitleType.toArray(new SearchType[0]));
                    }
                    if (titleTplEl.getPathBuilder().getText() != null) {
                        addTemplate(selectors, "titleEl", titleTplEl.getXPath());
                    }
                } else if (searchTitleType.isEmpty()) {
                    addTemplate(selectors, "title", title);
                } else {
                    addTextInPath(selectors, title, "@title", searchTitleType);
                }
            }
        }
        if (hasType()) {
            addTemplate(selectors, "type", getType());
        }
        if (!attribute.isEmpty()) {
            for (Map.Entry<String, SearchText> entry : attribute.entrySet()) {
                List<SearchType> searchType = entry.getValue().getSearchTypes();
                String text = entry.getValue().getValue();
                addTextInPath(selectors, text, "@" + entry.getKey(), searchType);
            }
        }
        if (!attributes.isEmpty()) {
            for (Map.Entry<String, String[]> entry : attributes.entrySet()) {
                String[] values = entry.getValue();
                for (String value : values) {
                    selectors.add(applyTemplate("attributes", entry.getKey(), value));
                }
            }
        }
        if (hasText()) {
            if (!Strings.isNullOrEmpty(getTemplate("text"))) {
                selectors.add(applyTemplate("text", Utils.getEscapeQuotesText(getText())));
            } else {
                addTextInPath(selectors, getText(), ".", searchTextType);
            }
        }
        for (Map.Entry<String, String[]> entry : getTemplatesValues().entrySet()) {
            if (!"tagAndPosition".equals(entry.getKey())) {
                addTemplate(selectors, entry.getKey(), entry.getValue());
            }
        }
        selectors.addAll(new ArrayList<>(elPathSuffix.values()));
        selectors.addAll(getChildNodesToSelector());
        return selectors.isEmpty() ? null : String.join(" and ", selectors);
    }

    public void addTextInPath(List<String> selectors, String text, String pattern, List<SearchType> searchTextType) {
        text = getTextAfterEscapeQuotes(text, searchTextType);
        boolean hasContainsAll = searchTextType.contains(SearchType.CONTAINS_ALL) || searchTextType.contains(SearchType.CONTAINS_ALL_CHILD_NODES);
        if (searchTextType.contains(SearchType.HTML_NODE)) {
            String selfPath = getTextWithSearchType(searchTextType, text, ".");
            addTemplate(selectors, "HTML_NODE", selfPath);
        } else if (searchTextType.contains(SearchType.HTML_NODES)) {
            addTemplate(selectors, "HTML_NODES", text);
        } else if (hasContainsAll || searchTextType.contains(SearchType.CONTAINS_ANY)) {
            String splitChar = String.valueOf(text.charAt(0));
            String[] strings = Pattern.compile(Pattern.quote(splitChar)).split(text.substring(1));
            for (int i = 0; i < strings.length; i++) {
                String escapeQuotesText = Utils.getEscapeQuotesText(strings[i]);
                if (searchTextType.contains(SearchType.CONTAINS_ALL_CHILD_NODES)) {
                    if (searchTextType.contains(SearchType.CASE_INSENSITIVE)) {
                        strings[i] = "count(*//text()[contains(translate(.," + escapeQuotesText.toUpperCase().replaceAll("CONCAT\\(", "concat(") + "," + escapeQuotesText.toLowerCase() + ")," + escapeQuotesText.toLowerCase() + ")]) > 0";
                    } else {
                        strings[i] = "count(*//text()[contains(.," + escapeQuotesText + ")]) > 0";
                    }
                } else {
                    String selfPath = getTextWithSearchType(searchTextType, escapeQuotesText, pattern);
                    if (searchTextType.contains(SearchType.DEEP_CHILD_NODE_OR_SELF)) {
                        strings[i] = applyTemplate("DEEP_CHILD_NODE_OR_SELF", selfPath);
                    } else if (searchTextType.contains(SearchType.DEEP_CHILD_NODE)) {
                        strings[i] = applyTemplate("DEEP_CHILD_NODE", selfPath);
                    } else {
                        strings[i] = "contains(" + (".".equals(pattern) ? "." : pattern) + "," + escapeQuotesText + ")";
                    }
                }
            }
            selectors.add(hasContainsAll ? String.join(" and ", strings) : "(" + String.join(" or ", strings) + ")");
        } else if (searchTextType.contains(SearchType.DEEP_CHILD_NODE_OR_SELF)) {
            String selfPath = getTextWithSearchType(searchTextType, text, pattern);
            addTemplate(selectors, "DEEP_CHILD_NODE_OR_SELF", selfPath);
        } else if (searchTextType.contains(SearchType.DEEP_CHILD_NODE)) {
            String selfPath = getTextWithSearchType(searchTextType, text, pattern);
            addTemplate(selectors, "DEEP_CHILD_NODE", selfPath);
        } else if (searchTextType.contains(SearchType.CHILD_NODE)) {
            String selfPath = getTextWithSearchType(searchTextType, text, pattern);
            addTemplate(selectors, "CHILD_NODE", selfPath);
        } else {
            selectors.add(getTextWithSearchType(searchTextType, text, ".".equals(pattern) ? "text()" : pattern));
        }
    }

    private List<String> getChildNodesToSelector() {
        List<String> selectors = new ArrayList<>();
        if (hasChildNodes()) {
            selectors.addAll(getChildNodes().stream().map(this::getChildNodeSelector).collect(Collectors.toList()));
        }
        return selectors;
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

        String selector = applyTemplate("childNodes", child.getXPath());
        if (breakElement != null) {
            breakElement.setContainer(parentElement);
        }
        return selector;
    }

    private void addTemplate(List<String> selectors, String key, Object... arguments) {
        String tpl = applyTemplate(key, arguments);
        if (!Strings.isNullOrEmpty(tpl)) {
            selectors.add(tpl);
        }
    }

    protected String applyTemplate(String key, Object... arguments) {
        String tpl = templates.get(key);
        if (!Strings.isNullOrEmpty(tpl)) {
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
            selector += !Strings.isNullOrEmpty(selector) ? " and " + subPath : subPath;
        }
        Map<String, String[]> templatesValues = getTemplatesValues();
        String[] tagAndPositions = templatesValues.get("tagAndPosition");
        List<String> tagAndPosition = new ArrayList<>();
        if (tagAndPositions != null) {
            tagAndPosition.addAll(Arrays.asList(tagAndPositions));
            tagAndPosition.add(0, getTag());
        }
        String tag;
        if (!tagAndPosition.isEmpty()) {
            tag = applyTemplate("tagAndPosition", tagAndPosition.toArray());
        } else {
            tag = getTag();
        }
        selector = getRoot() + tag + (!Strings.isNullOrEmpty(selector) ? "[" + selector + "]" : "");
        return selector;
    }

    private String getTextWithSearchType(List<SearchType> searchType, String text, String pattern) {
        if (searchType.contains(SearchType.TRIM)) {
            pattern = "normalize-space(" + pattern + ")";
        }
        if (searchType.contains(SearchType.CASE_INSENSITIVE)) {
            pattern = "translate(" + pattern + "," + text.toUpperCase().replaceAll("CONCAT\\(", "concat(") + "," + text.toLowerCase() + ")";
            text = text.toLowerCase();
        }
        if (searchType.contains(SearchType.EQUALS)) {
            if(Strings.isNullOrEmpty(text)){
                text = pattern;
            } else {
                text = pattern + "=" + text;
            }
        } else if (searchType.contains(SearchType.STARTS_WITH)) {
            text = "starts-with(" + pattern + "," + text + ")";
        } else {
            text = "contains(" + pattern + "," + text + ")";
        }
        return text;
    }

    private String getTextAfterEscapeQuotes(String text, List<SearchType> searchType) {
        if (searchType.contains(SearchType.CONTAINS_ALL) || searchType.contains(SearchType.CONTAINS_ANY) || searchType.contains(SearchType.CONTAINS_ALL_CHILD_NODES)) {
            return text;
        }
        return Utils.getEscapeQuotesText(text);
    }

    private String getBaseItemPath() {
        return getBasePathSelector();
    }

    private String getItemCssSelector() {
        List<String> selectors = new ArrayList<>();
        if (hasTag()) {
            selectors.add(getTag());
        }
        if (hasId()) {
            selectors.add("#" + getId());
        }
        if (hasBaseCls()) {
            selectors.add("." + getBaseCls());
        }
        if (hasCls()) {
            selectors.add("[class=" + getCls() + "]");
        }
        if (hasClasses()) {
            selectors.addAll(getClasses().stream().map(cls -> "." + cls).collect(Collectors.toList()));
        }
        if (hasExcludeClasses()) {
//            LOGGER.warn("excludeClasses is not supported yet");
            selectors.addAll(getExcludeClasses().stream().map(excludeClass -> ":not(." + excludeClass + ")").collect(Collectors.toList()));
        }
        if (hasName()) {
            selectors.add("[name='" + getName() + "']");
        }
        if (hasType()) {
            selectors.add("[type='" + getType() + "']");
        }
        if (!attribute.isEmpty()) {
            selectors.addAll(attribute.entrySet().stream()
                    .map(e -> "[" + e.getKey() + "='" + e.getValue().getValue() + "']")
                    .collect(Collectors.toList()));
        }
//        for (Map.Entry<String, String> entry : getTemplatesValues().entrySet()) {
//            addTemplate(selector, entry.getKey(), entry.getValue());
//        }
//        for (String suffix : elPathSuffix.values()) {
//            selector.add(suffix);
//        }
        return selectors.isEmpty() ? "*" : String.join("", selectors);
    }

    public final By getSelector() {
        String cssSelector = getCssSelector();
        return !Strings.isNullOrEmpty(cssSelector) ? By.cssSelector(cssSelector) : By.xpath(getXPath());
    }

    private boolean isCssSelectorSupported() {
        return !(hasText() || hasElPath() || hasChildNodes() || hasStyle() || hasLabel() || hasTitle() || hasResultIdx());
    }

    public final String getCssSelector() {
        String cssSelector = null;

        cssSelector = getElCssSelector();
        if (WebLocatorConfig.isGenerateCssSelector()) {
            if (Strings.isNullOrEmpty(cssSelector)) {
                if (isCssSelectorSupported()) {
                    cssSelector = getItemCssSelector();
                    if (hasPosition()) {
                        if ("first()".equals(position)) {
                            cssSelector += ":first-child";
                        } else if ("last()".equals(position)) {
                            cssSelector += ":last-child";
                        } else {
                            cssSelector += ":nth-child(" + getPosition() + ")";
                        }
                    }
                }
            } //else {
//            String baseCssSelector = getItemCssSelector();
//            if (StringUtils.isNotEmpty(baseCssSelector)) {
//                 TODO "inject" baseItemPath to elPath
//            }
//            }
        }
        // add container path
        if (cssSelector != null && getContainer() != null) {
            String parentCssSelector = getContainer().getCssSelector();
            if (Strings.isNullOrEmpty(parentCssSelector)) {
                log.warn("Can't generate css selector for parent: {}", getContainer());
                cssSelector = null;
            } else {
                String root = getRoot();
                String deep = "";
                if (!Strings.isNullOrEmpty(root)) {
                    if (root.equals("/")) {
                        deep = " > ";
                    } else if (root.equals("//")) {
                        deep = " ";
                    } else {
                        log.warn("this root ({}) is no implemented in css selector: ", root);
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

//            String baseItemPath = getBaseItemPath();
//            if (!Strings.isNullOrEmpty(baseItemPath)) {
            // TODO "inject" baseItemPath to elPath
//            }
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

    private String addResultIndexToPath(String xPath) {
        if (hasResultIdx()) {
            xPath = "(" + xPath + ")[" + getResultIdx() + "]";
        }
        return xPath;
    }

    @Override
    public String toString() {
        String info = getInfoMessage();
        if (Strings.isNullOrEmpty(info)) {
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
        StringBuilder info = new StringBuilder();
        if (hasText()) {
            info.append(getText());
        } else if (hasTitle()) {
            info.append(getTitle());
        } else if (hasId()) {
            info.append(getId());
        } else if (hasName()) {
            info.append(getName());
        } else if (hasBaseCls()) {
            info.append(getBaseCls());
        } else if (hasClasses()) {
            info.append(classes.size() == 1 ? classes.get(0) : classes.toString());
        } else if (hasCls()) {
            info.append(getCls());
        } else if (hasLabel()) {
            info.append(getLabel());
        } else if (hasElPath()) {
            info.append(getElPath());
        } else if (!attribute.isEmpty()) {
            for (Map.Entry<String, SearchText> entry : attribute.entrySet()) {
                info.append("@").append(entry.getKey()).append("=").append(entry.getValue().getValue());
            }
        } else if (hasTag()) {
            info.append(getTag());
        } else {
            info.append(getClassName());
        }
        return info.toString();
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
            itemPath += applyTemplate("position", getPosition());
        }
        return itemPath;
    }

    protected String getLabelPath() {
        if (searchLabelType.size() == 0) {
            searchLabelType.add(SearchType.EQUALS);
        }
        SearchType[] st = searchLabelType.toArray(new SearchType[0]);
        return new WebLocator().setText(getLabel(), st).setTag(getLabelTag()).getXPath();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object clone() throws CloneNotSupportedException {
        XPathBuilder builder = (XPathBuilder) super.clone();

        builder.defaultSearchTextType = (List<SearchType>) ((ArrayList) defaultSearchTextType).clone();
        builder.searchTextType = (List<SearchType>) ((ArrayList) searchTextType).clone();
        builder.searchTitleType = (List<SearchType>) ((ArrayList) searchTitleType).clone();
        builder.searchLabelType = (List<SearchType>) ((ArrayList) searchLabelType).clone();

        LinkedHashMap<String, String> templates = (LinkedHashMap<String, String>) builder.templates;
        LinkedHashMap<String, WebLocator> templateTitle = (LinkedHashMap<String, WebLocator>) builder.templateTitle;
        LinkedHashMap<String, String[]> templatesValues = (LinkedHashMap<String, String[]>) builder.templatesValues;
        LinkedHashMap<String, String> elPathSuffix = (LinkedHashMap<String, String>) builder.elPathSuffix;
        LinkedHashMap<String, SearchText> attribute = (LinkedHashMap<String, SearchText>) builder.attribute;
        LinkedHashMap<String, String[]> attributes = (LinkedHashMap<String, String[]>) builder.attributes;

        builder.templates = (Map<String, String>) templates.clone();
        builder.templatesValues = (Map<String, String[]>) templatesValues.clone();
        builder.elPathSuffix = (Map<String, String>) elPathSuffix.clone();
        builder.attribute = (Map<String, SearchText>) attribute.clone();
        builder.attributes = (Map<String, String[]>) attributes.clone();

        builder.templateTitle = (Map<String, WebLocator>) templateTitle.clone();
        WebLocator titleTplEl = templateTitle.get("title");
        if (titleTplEl != null) {
            XPathBuilder titleTplElBuilder = (XPathBuilder) titleTplEl.getPathBuilder().clone();
            WebLocator titleTplElCloned = new WebLocator().setPathBuilder(titleTplElBuilder);
            builder.templateTitle.put("title", titleTplElCloned);
        }

        return builder;
    }

    public List<SearchType> getDefaultSearchTextType() {
        return this.defaultSearchTextType;
    }

    public String getClassName() {
        return this.className;
    }

    public String getRoot() {
        return this.root;
    }

    public String getTag() {
        return this.tag;
    }

    public String getId() {
        return this.id;
    }

    public String getElPath() {
        return this.elPath;
    }

    public String getElCssSelector() {
        return this.elCssSelector;
    }

    public String getBaseCls() {
        return this.baseCls;
    }

    public String getCls() {
        return this.cls;
    }

    public List<String> getClasses() {
        return this.classes;
    }

    public List<String> getExcludeClasses() {
        return this.excludeClasses;
    }

    public String getName() {
        return this.name;
    }

    public String getLocalName() {
        return this.localName;
    }

    public String getText() {
        return this.text;
    }

    public List<SearchType> getSearchTextType() {
        return this.searchTextType;
    }

    public List<SearchType> getSearchTitleType() {
        return this.searchTitleType;
    }

    public List<SearchType> getSearchLabelType() {
        return this.searchLabelType;
    }

    public String getStyle() {
        return this.style;
    }

    public String getTitle() {
        return this.title;
    }

    public Map<String, String> getTemplates() {
        return this.templates;
    }

    public Map<String, WebLocator> getTemplateTitle() {
        return this.templateTitle;
    }

    public Map<String, String> getElPathSuffix() {
        return this.elPathSuffix;
    }

    public String getInfoMessage() {
        return this.infoMessage;
    }

    public String getLabel() {
        return this.label;
    }

    public String getLabelTag() {
        return this.labelTag;
    }

    public String getLabelPosition() {
        return this.labelPosition;
    }

    public String getPosition() {
        return this.position;
    }

    public String getResultIdx() {
        return this.resultIdx;
    }

    public String getType() {
        return this.type;
    }

    public Map<String, SearchText> getAttribute() {
        return this.attribute;
    }

    public Map<String, String[]> getAttributes() {
        return this.attributes;
    }

    public boolean isVisibility() {
        return this.visibility;
    }

    public Duration getRender() {
        return this.render;
    }

    @Deprecated
    public long getRenderMillis() {
        return this.renderMillis;
    }

    public Duration getActivate() {
        return this.activate;
    }

    @Deprecated
    public int getActivateSeconds() {
        return this.activateSeconds;
    }

    public WebLocator getContainer() {
        return this.container;
    }

    public List<WebLocator> getChildNodes() {
        return this.childNodes;
    }
}