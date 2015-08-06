package com.sdl.selenium.web;

/**
 * @author nmatei
 * @since 2/25/14
 */
public interface IWebLocator {

    String getCssValue(String propertyName);

    String getAttributeId();

    String getAttributeClass();

    String getCurrentElementPath();

    /**
     * @param attribute e.g. class, id
     * @return String attribute, if element not exist return null.
     */
    public String getAttribute(String attribute);

    boolean isElementPresent();

    int size();

    boolean isVisible();

    boolean waitToRender();

    boolean waitToRender(final long millis);

    boolean ready();

    boolean ready(int seconds);

    <T extends WebLocatorAbstractBuilder> T setRoot(final String root);

    <T extends WebLocatorAbstractBuilder> T setTag(final String tag);

    <T extends WebLocatorAbstractBuilder> T setId(final String id);

    <T extends WebLocatorAbstractBuilder> T setElPath(final String elPath);

    <T extends WebLocatorAbstractBuilder> T setBaseCls(final String baseCls);

    <T extends WebLocatorAbstractBuilder> T setCls(final String cls);

    <T extends WebLocatorAbstractBuilder> T setClasses(final String... classes);

    <T extends WebLocatorAbstractBuilder> T setExcludeClasses(final String... excludeClasses);

    <T extends WebLocatorAbstractBuilder> T setChildNodes(final WebLocator... childNodes);

    <T extends WebLocatorAbstractBuilder> T setName(final String name);

    <T extends WebLocatorAbstractBuilder> T setText(final String text, final SearchType... searchType);

    <T extends WebLocatorAbstractBuilder> T setSearchTextType(SearchType... searchTextType);

//    <T extends WebLocatorAbstractBuilder> T setSearchLabelType(SearchType... searchLabelType);

    <T extends WebLocatorAbstractBuilder> T setStyle(final String style);

    <T extends WebLocatorAbstractBuilder> T setTitle(final String title, SearchType... searchType);

    <T extends WebLocatorAbstractBuilder> T setTemplateTitle(WebLocator titleEl);

    <T extends WebLocatorAbstractBuilder> T setElPathSuffix(final String key, final String elPathSuffix);

    <T extends WebLocatorAbstractBuilder> T setTemplateValue(final String key, final String value);

    <T extends WebLocatorAbstractBuilder> T setTemplate(final String key, final String value);

    <T extends WebLocatorAbstractBuilder> T addToTemplate(final String key, final String value);

    <T extends WebLocatorAbstractBuilder> T setInfoMessage(final String infoMessage);

    <T extends WebLocatorAbstractBuilder> T setVisibility(final boolean visibility);

    <T extends WebLocatorAbstractBuilder> T setRenderMillis(final long renderMillis);

    <T extends WebLocatorAbstractBuilder> T setActivateSeconds(final int activateSeconds);

    <T extends WebLocatorAbstractBuilder> T setContainer(WebLocator container);

    <T extends WebLocatorAbstractBuilder> T setLabel(final String label, final SearchType... searchType);

    <T extends WebLocatorAbstractBuilder> T setLabelTag(final String labelTag);

    <T extends WebLocatorAbstractBuilder> T setLabelPosition(final String labelPosition);

    <T extends WebLocatorAbstractBuilder> T setPosition(final int position);

    <T extends WebLocatorAbstractBuilder> T setResultIdx(final int resultIdx);

    <T extends WebLocatorAbstractBuilder> T setType(final String type);

    <T extends WebLocatorAbstractBuilder> T setAttribute(final String attribute, final String value, final SearchType ...searchType);

}
