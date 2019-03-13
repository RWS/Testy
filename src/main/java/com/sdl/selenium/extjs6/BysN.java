package com.sdl.selenium.extjs6;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.SearchText;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Getter
@Setter
@Slf4j
public class BysN {

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

    public Consumer<BysN> with(Consumer<BysN> builderFunction) {
        builderFunction.accept(this);
        return builderFunction;
    }

    public Bys build() {
        return new Bys(root, tag, id, baseCls, classes, excludeClasses, name, text, title, style, label, labelTag, labelPosition, position, resultIdx, type, visibility, attribute, container, childNodes, renderMillis, activateSeconds, elPathSuffix, elPath, elCssSelector, infoMessage, version, defaultSearchTextType, searchTextType, searchTitleType, searchLabelType, templates, templateTitle, templatesValues);
    }
}
