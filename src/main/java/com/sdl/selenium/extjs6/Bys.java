package com.sdl.selenium.extjs6;

import com.google.common.base.Strings;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.SearchText;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.XPathBuilder;
import com.sdl.selenium.web.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.By;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Bys {

    private String root = "//";
    private String tag = "*";
    private String id;
    private String baseCls;
    private List<String> classes;
    private List<String> excludeClasses;
    private String name;
    private String text;
    private String title;
    private String style;
    private String label;
    private String labelTag = "label";
    private String labelPosition = WebLocatorConfig.getDefaultLabelPosition();
    private String position;
    private String resultIdx;
    private String type;
    private boolean visibility;
    private Map<String, SearchText> attribute = new LinkedHashMap<>();
    private WebLocator container;
    private List<WebLocator> childNodes;
    private long renderMillis = WebLocatorConfig.getDefaultRenderMillis();
    private int activateSeconds = 60;
    private Map<String, String> elPathSuffix = new LinkedHashMap<>();
    private String elPath;
    private String elCssSelector;
    private String infoMessage;
    private String version = WebLocatorConfig.getExtJsVersion();

    public List<SearchType> defaultSearchTextType = new ArrayList<>();
    private List<SearchType> searchTextType = WebLocatorConfig.getSearchTextType();
    private List<SearchType> searchTitleType = new ArrayList<>();
    private List<SearchType> searchLabelType = new ArrayList<>();
    private Map<String, String> templates = new LinkedHashMap<>();
    private Map<String, WebLocator> templateTitle = new LinkedHashMap<>();
    private Map<String, String[]> templatesValues = new LinkedHashMap<>();

    public static BysBuilder with() {
        return new BysBuilder();
    }

    @Getter
    public static class BysBuilder {
        private String root;
        private String tag;
        private String id;
        private String baseCls;
        private List<String> classes;
        private List<String> excludeClasses;
        private String name;
        private String text;
        private String title;
        private String style;
        private String label;
        private String labelTag;
        private String labelPosition;
        private String position;
        private String resultIdx;
        private String type;
        private boolean visibility;
        private Map<String, SearchText> attribute;
        private WebLocator container;
        private List<WebLocator> childNodes;
        private long renderMillis;
        private int activateSeconds;
        private Map<String, String> elPathSuffix;
        private String elPath;
        private String elCssSelector;
        private String infoMessage;
        private String version;
        private List<SearchType> defaultSearchTextType;
        private List<SearchType> searchTextType;
        private List<SearchType> searchTitleType;
        private List<SearchType> searchLabelType;
        private Map<String, String> templates;
        private Map<String, WebLocator> templateTitle;
        private Map<String, String[]> templatesValues;

        BysBuilder() {
        }

        public BysBuilder root(String root) {
            this.root = root;
            return this;
        }

        public BysBuilder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public BysBuilder id(String id) {
            this.id = id;
            return this;
        }

        public BysBuilder baseCls(String baseCls) {
            this.baseCls = baseCls;
            return this;
        }

        public BysBuilder classes(List<String> classes) {
            this.classes = classes;
            return this;
        }

        public BysBuilder excludeClasses(List<String> excludeClasses) {
            this.excludeClasses = excludeClasses;
            return this;
        }

        public BysBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BysBuilder text(String text) {
            this.text = text;
            return this;
        }

        public BysBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BysBuilder style(String style) {
            this.style = style;
            return this;
        }

        public BysBuilder label(String label) {
            this.label = label;
            return this;
        }

        public BysBuilder labelTag(String labelTag) {
            this.labelTag = labelTag;
            return this;
        }

        public BysBuilder labelPosition(String labelPosition) {
            this.labelPosition = labelPosition;
            return this;
        }

        public BysBuilder position(String position) {
            this.position = position;
            return this;
        }

        public BysBuilder resultIdx(String resultIdx) {
            this.resultIdx = resultIdx;
            return this;
        }

        public BysBuilder type(String type) {
            this.type = type;
            return this;
        }

        public BysBuilder visibility(boolean visibility) {
            this.visibility = visibility;
            return this;
        }

        public BysBuilder attribute(Map<String, SearchText> attribute) {
            this.attribute = attribute;
            return this;
        }

        public BysBuilder container(WebLocator container) {
            this.container = container;
            return this;
        }

        public BysBuilder childNodes(List<WebLocator> childNodes) {
            this.childNodes = childNodes;
            return this;
        }

        public BysBuilder renderMillis(long renderMillis) {
            this.renderMillis = renderMillis;
            return this;
        }

        public BysBuilder activateSeconds(int activateSeconds) {
            this.activateSeconds = activateSeconds;
            return this;
        }

        public BysBuilder elPathSuffix(Map<String, String> elPathSuffix) {
            this.elPathSuffix = elPathSuffix;
            return this;
        }

        public BysBuilder elPath(String elPath) {
            this.elPath = elPath;
            return this;
        }

        public BysBuilder elCssSelector(String elCssSelector) {
            this.elCssSelector = elCssSelector;
            return this;
        }

        public BysBuilder infoMessage(String infoMessage) {
            this.infoMessage = infoMessage;
            return this;
        }

        public BysBuilder version(String version) {
            this.version = version;
            return this;
        }

        public BysBuilder defaultSearchTextType(List<SearchType> defaultSearchTextType) {
            this.defaultSearchTextType = defaultSearchTextType;
            return this;
        }

        public BysBuilder searchTextType(List<SearchType> searchTextType) {
            this.searchTextType = searchTextType;
            return this;
        }

        public BysBuilder searchTitleType(List<SearchType> searchTitleType) {
            this.searchTitleType = searchTitleType;
            return this;
        }

        public BysBuilder searchLabelType(List<SearchType> searchLabelType) {
            this.searchLabelType = searchLabelType;
            return this;
        }

        public BysBuilder templates(Map<String, String> templates) {
            this.templates = templates;
            return this;
        }

        public BysBuilder templateTitle(Map<String, WebLocator> templateTitle) {
            this.templateTitle = templateTitle;
            return this;
        }

        public BysBuilder templatesValues(Map<String, String[]> templatesValues) {
            this.templatesValues = templatesValues;
            return this;
        }

        public Bys build() {
            Bys bys = new Bys(root, tag, id, baseCls, classes, excludeClasses, name, text, title, style, label, labelTag, labelPosition, position, resultIdx, type, visibility, attribute, container, childNodes, renderMillis, activateSeconds, elPathSuffix, elPath, elCssSelector, infoMessage, version, defaultSearchTextType, searchTextType, searchTitleType, searchLabelType, templates, templateTitle, templatesValues);
            return bys;
        }

        public String toString() {
            return "Bys.BysBuilder(root=" + this.root + ", tag=" + this.tag + ", id=" + this.id + ", baseCls=" + this.baseCls + ", classes=" + this.classes + ", excludeClasses=" + this.excludeClasses + ", name=" + this.name + ", text=" + this.text + ", title=" + this.title + ", style=" + this.style + ", label=" + this.label + ", labelTag=" + this.labelTag + ", labelPosition=" + this.labelPosition + ", position=" + this.position + ", resultIdx=" + this.resultIdx + ", type=" + this.type + ", visibility=" + this.visibility + ", attribute=" + this.attribute + ", container=" + this.container + ", childNodes=" + this.childNodes + ", renderMillis=" + this.renderMillis + ", activateSeconds=" + this.activateSeconds + ", elPathSuffix=" + this.elPathSuffix + ", elPath=" + this.elPath + ", elCssSelector=" + this.elCssSelector + ", infoMessage=" + this.infoMessage + ", version=" + this.version + ", defaultSearchTextType=" + this.defaultSearchTextType + ", searchTextType=" + this.searchTextType + ", searchTitleType=" + this.searchTitleType + ", searchLabelType=" + this.searchLabelType + ", templates=" + this.templates + ", templateTitle=" + this.templateTitle + ", templatesValues=" + this.templatesValues + ")";
        }
    }


    protected boolean hasId() {
        return !Strings.isNullOrEmpty(id);
    }

//    protected boolean hasCls() {
//        return !Strings.isNullOrEmpty(cls);
//    }

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

    public String getTemplate(final String key) {
        return templates.get(key);
    }

    /**
     * Containing baseCls, class, name and style
     *
     * @return baseSelector
     */
    protected String getBasePathSelector() {
        // TODO use disabled
        // TODO verify what need to be equal OR contains
        List<String> selector = new ArrayList<>();
        CollectionUtils.addIgnoreNull(selector, getBasePath());

        if (!WebDriverConfig.isIE()) {
            if (hasStyle()) {
                selector.add(applyTemplate("style", getStyle()));
            }
            // TODO make specific for WebLocator
            if (isVisibility()) {
//               TODO selector.append(" and count(ancestor-or-self::*[contains(replace(@style, '\s*:\s*', ':'), 'display:none')]) = 0");
                CollectionUtils.addIgnoreNull(selector, applyTemplate("visibility", isVisibility()));
            }
        }

        return selector.isEmpty() ? "" : String.join(" and ", selector);
    }

    public String getBasePath() {
        List<String> selector = new ArrayList<>();
        if (hasId()) {
            selector.add(applyTemplate("id", getId()));
        }
        if (hasName()) {
            selector.add(applyTemplate("name", getName()));
        }
        if (hasBaseCls()) {
            selector.add(applyTemplate("class", getBaseCls()));
        }
//        if (hasCls()) {
//            selector.add(applyTemplate("cls", getCls()));
//        }
        if (hasClasses()) {
            selector.addAll(getClasses().stream().map(cls -> applyTemplate("class", cls)).collect(Collectors.toList()));
        }
        if (hasExcludeClasses()) {
            selector.addAll(getExcludeClasses().stream().map(excludeClass -> applyTemplate("excludeClass", excludeClass)).collect(Collectors.toList()));
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
                        addTemplate(selector, "titleEl", titleTplEl.getXPath());
                    }
                } else if (searchTitleType.isEmpty()) {
                    addTemplate(selector, "title", title);
                } else {
                    addTextInPath(selector, title, "@title", searchTitleType);
                }
            }
        }
        if (hasType()) {
            addTemplate(selector, "type", getType());
        }
        if (!attribute.isEmpty()) {
            for (Map.Entry<String, SearchText> entry : attribute.entrySet()) {
                List<SearchType> searchType = entry.getValue().getSearchTypes();
                String text = entry.getValue().getValue();
                addTextInPath(selector, text, "@" + entry.getKey(), searchType);
            }
        }
        if (hasText()) {
            if (!Strings.isNullOrEmpty(getTemplate("text"))) {
                selector.add(applyTemplate("text", Utils.getEscapeQuotesText(getText())));
            } else {
                addTextInPath(selector, getText(), ".", searchTextType);
            }
        }
        for (Map.Entry<String, String[]> entry : getTemplatesValues().entrySet()) {
            if (!"tagAndPosition".equals(entry.getKey())) {
                addTemplate(selector, entry.getKey(), entry.getValue());
            }
        }
        selector.addAll(elPathSuffix.values().stream().collect(Collectors.toList()));
        selector.addAll(getChildNodesToSelector());
        return selector.isEmpty() ? null : String.join(" and ", selector);
    }

    public void addTextInPath(List<String> selector, String text, String pattern, List<SearchType> searchTextType) {
        text = getTextAfterEscapeQuotes(text, searchTextType);
        boolean hasContainsAll = searchTextType.contains(SearchType.CONTAINS_ALL) || searchTextType.contains(SearchType.CONTAINS_ALL_CHILD_NODES);
        if (hasContainsAll || searchTextType.contains(SearchType.CONTAINS_ANY)) {
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
                    if (searchTextType.contains(SearchType.DEEP_CHILD_NODE_OR_SELF)) {
                        strings[i] = applyTemplate("DEEP_CHILD_NODE_OR_SELF", escapeQuotesText);
                    } else if (searchTextType.contains(SearchType.DEEP_CHILD_NODE)) {
                        strings[i] = applyTemplate("DEEP_CHILD_NODE", escapeQuotesText);
                    } else {
                        strings[i] = "contains(" + (".".equals(pattern) ? "." : pattern) + "," + escapeQuotesText + ")";
                    }
                }
            }
            selector.add(hasContainsAll ? String.join(" and ", strings) : "(" + String.join(" or ", strings) + ")");
        } else if (searchTextType.contains(SearchType.DEEP_CHILD_NODE_OR_SELF)) {
            String selfPath = getTextWithSearchType(searchTextType, text, pattern);
            addTemplate(selector, "DEEP_CHILD_NODE_OR_SELF", selfPath);
        } else if (searchTextType.contains(SearchType.DEEP_CHILD_NODE)) {
            String selfPath = getTextWithSearchType(searchTextType, text, pattern);
            addTemplate(selector, "DEEP_CHILD_NODE", selfPath);
        } else if (searchTextType.contains(SearchType.CHILD_NODE)) {
            String selfPath = getTextWithSearchType(searchTextType, text, pattern);
            addTemplate(selector, "CHILD_NODE", selfPath);
        } else if (searchTextType.contains(SearchType.HTML_NODE)) {
            addTemplate(selector, "HTML_NODE", text);
        } else {
            selector.add(getTextWithSearchType(searchTextType, text, ".".equals(pattern) ? "text()" : pattern));
        }
    }

    private List<String> getChildNodesToSelector() {
        List<String> selector = new ArrayList<>();
        if (hasChildNodes()) {
            selector.addAll(getChildNodes().stream().map(this::getChildNodeSelector).collect(Collectors.toList()));
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
//            if (parentElementIterator.getPathBuilder() == this) {
//                childIterator.setContainer(null); // break parent tree while generating child address
//                parentElement = parentElementIterator;
//                breakElement = childIterator;
//            } else {
//                childIterator = parentElementIterator;
//            }
        }

        String selector = applyTemplate("childNodes", child.getXPath());
        if (breakElement != null) {
            breakElement.setContainer(parentElement);
        }
        return selector;
    }

    private void addTemplate(List<String> selector, String key, Object... arguments) {
        String tpl = applyTemplate(key, arguments);
        if (!Strings.isNullOrEmpty(tpl)) {
            selector.add(tpl);
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
     * @return String
     */
    protected String getItemPath() {
        String selector = getBaseItemPath();
//        String subPath = applyTemplateValue(disabled ? "disabled" : "enabled");
//        if (subPath != null) {
//            selector += !Strings.isNullOrEmpty(selector) ? " and " + subPath : subPath;
//        }
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
            text = pattern + "=" + text;
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
        List<String> selector = new ArrayList<>();
        if (hasTag()) {
            selector.add(getTag());
        }
        if (hasId()) {
            selector.add("#" + getId());
        }
        if (hasBaseCls()) {
            selector.add("." + getBaseCls());
        }
//        if (hasCls()) {
//            selector.add("[class=" + getCls() + "]");
//        }
        if (hasClasses()) {
            selector.addAll(getClasses().stream().map(cls -> "." + cls).collect(Collectors.toList()));
        }
        if (hasExcludeClasses()) {
//            LOGGER.warn("excludeClasses is not supported yet");
            selector.addAll(getExcludeClasses().stream().map(excludeClass -> ":not(." + excludeClass + ")").collect(Collectors.toList()));
        }
        if (hasName()) {
            selector.add("[name='" + getName() + "']");
        }
        if (hasType()) {
            selector.add("[type='" + getType() + "']");
        }
        if (!attribute.isEmpty()) {
            selector.addAll(attribute.entrySet().stream()
                    .map(e -> "[" + e.getKey() + "='" + e.getValue().getValue() + "']")
                    .collect(Collectors.toList()));
        }
//        for (Map.Entry<String, String> entry : getTemplatesValues().entrySet()) {
//            addTemplate(selector, entry.getKey(), entry.getValue());
//        }
//        for (String suffix : elPathSuffix.values()) {
//            selector.add(suffix);
//        }
        return selector.isEmpty() ? "*" : String.join("", selector);
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
//        BysBuilder
        String returnPath;
        if (hasElPath()) {
            returnPath = getElPath();

//            String baseItemPath = getBaseItemPath();
//            if (!Strings.isNullOrEmpty(baseItemPath)) {
            // TODO "inject" baseItemPath to elPath
//            }
        } else {
            returnPath = getItemPath();
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
//        if (WebLocatorConfig.isLogUseClassName() && !getClassName().equals(info)) {
//            info += " - " + getClassName();
//        }
        // add container path
        if (WebLocatorConfig.isLogContainers() && getContainer() != null) {
            info = getContainer().toString() + " -> " + info;
        }
        return info;
    }

    public String itemToString() {
        String info = "";
        if (hasText()) {
            info = getText();
        } else if (hasTitle()) {
            info = getTitle();
        } else if (hasId()) {
            info = getId();
        } else if (hasName()) {
            info = getName();
        } else if (hasBaseCls()) {
            info = getBaseCls();
        } else if (hasClasses()) {
            info = classes.size() == 1 ? classes.get(0) : classes.toString();
        } /*else if (hasCls()) {
            info = getCls();
        }*/ else if (hasLabel()) {
            info = getLabel();
        } else if (hasElPath()) {
            info = getElPath();
        } else if (!attribute.isEmpty()) {
            for (Map.Entry<String, SearchText> entry : attribute.entrySet()) {
                info += "@" + entry.getKey() + "=" + entry.getValue().getValue();
            }
        } else if (hasTag()) {
            info = getTag();
        }
//        else {
//            info = getClassName();
//        }
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
            itemPath += applyTemplate("position", getPosition());
        }
        return itemPath;
    }

    protected String getLabelPath() {
        if (searchLabelType.size() == 0) {
            searchLabelType.add(SearchType.EQUALS);
        }
        SearchType[] st = searchLabelType.stream().toArray(SearchType[]::new);
        return new WebLocator().setText(getLabel(), st).setTag(getLabelTag()).getXPath();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object clone() throws CloneNotSupportedException {
        Bys builder = (Bys) super.clone();

        LinkedHashMap<String, String> templates = (LinkedHashMap<String, String>) builder.templates;
        LinkedHashMap<String, WebLocator> templateTitle = (LinkedHashMap<String, WebLocator>) builder.templateTitle;
        LinkedHashMap<String, String[]> templatesValues = (LinkedHashMap<String, String[]>) builder.templatesValues;
        LinkedHashMap<String, String> elPathSuffix = (LinkedHashMap<String, String>) builder.elPathSuffix;

        builder.templates = (Map<String, String>) templates.clone();
        builder.templatesValues = (Map<String, String[]>) templatesValues.clone();
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
