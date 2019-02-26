package com.sdl.selenium.web;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

// Aceasta interfata e la fel cu IWebLocator, doar reimplementata in alt mod
public interface ILocator {

    @Slf4j
    @Setter
    class Builder {
        private static XPathBuilder xpath;

        private static XPathBuilder create() {
            log.info("{}", "create");
            xpath = new XPathBuilder();
            return xpath;
        }

        private static XPathBuilder get() {
            log.info("{}", "get");
            return Builder.xpath == null ? create() : xpath;
        }

        private static XPathBuilder set(final XPathBuilder xPathBuilder) {
            return Builder.xpath = xPathBuilder;
        }
    }

    default  void setXPathBuilder(final XPathBuilder pathBuilder) {
        Builder.set(pathBuilder);
    }

    default XPathBuilder createXPathBuilder() {
        return Builder.create();
    }

    /**
     * <p><strong><i>Used for finding element process (to generate xpath address)</i></strong></p>
     *
     * @return {@link XPathBuilder}
     */
    default XPathBuilder getXPathBuilder() {
        return Builder.get();
    }

    default String getXPath() {
        return Builder.get().getXPath();
    }

    default By getSelector() {
        return Builder.get().getSelector();
    }

    default String getCssSelector() {
        return Builder.get().getCssSelector();
    }

    default <T extends ILocator> T setRoot(final String root) {
        Builder.get().setRoot(root);
        return (T) this;
    }

    default <T extends ILocator> T setTag(final String tag) {
        Builder.get().setTag(tag);
        return (T) this;
    }

    default <T extends ILocator> T setId(final String id) {
        Builder.get().setId(id);
        return (T) this;
    }

    default <T extends ILocator> T setElPath(final String elPath) {
        Builder.get().setElPath(elPath);
        return (T) this;
    }

    default <T extends ILocator> T setBaseCls(final String baseCls) {
        Builder.get().setBaseCls(baseCls);
        return (T) this;
    }

    default <T extends ILocator> T setCls(final String cls) {
        Builder.get().setCls(cls);
        return (T) this;
    }

    default <T extends ILocator> T setClasses(final String... classes) {
        Builder.get().setClasses(classes);
        return (T) this;
    }

    default <T extends ILocator> T setExcludeClasses(final String... excludeClasses) {
        Builder.get().setExcludeClasses(excludeClasses);
        return (T) this;
    }

    default <T extends ILocator> T setChildNodes(final Locator... childNodes) {
        Builder.get().setChildNodes(childNodes);
        return (T) this;
    }

    default <T extends ILocator> T setName(final String name) {
        Builder.get().setName(name);
        return (T) this;
    }

    default <T extends ILocator> T setText(final String text, final SearchType... searchTypes) {
        Builder.get().setText(text, searchTypes);
        return (T) this;
    }

    default <T extends ILocator> T setSearchTextType(SearchType... searchTextTypes) {
        Builder.get().setSearchTextType(searchTextTypes);
        return (T) this;
    }

    default <T extends ILocator> T addSearchTextType(SearchType... searchTextTypes) {
        Builder.get().addSearchTextType(searchTextTypes);
        return (T) this;
    }

    default <T extends ILocator> T setStyle(final String style) {
        Builder.get().setStyle(style);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default <T extends ILocator> T setTitle(final String title, SearchType... searchTypes) {
        Builder.get().setTitle(title, searchTypes);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default <T extends ILocator> T setSearchTitleType(SearchType... searchTitleTypes) {
        Builder.get().setSearchTitleType(searchTitleTypes);
        return (T) this;
    }

    default <T extends ILocator> T setTemplateTitle(Locator titleEl) {
        Builder.get().setTemplateTitle(titleEl);
        return (T) this;
    }

    default <T extends ILocator> T setElPathSuffix(final String key, final String elPathSuffix) {
        Builder.get().setElPathSuffix(key, elPathSuffix);
        return (T) this;
    }

    default <T extends ILocator> T setTemplateValue(final String key, final String... value) {
        Builder.get().setTemplateValue(key, value);
        return (T) this;
    }

    default <T extends ILocator> T setTemplate(final String key, final String value) {
        Builder.get().setTemplate(key, value);
        return (T) this;
    }

    default <T extends ILocator> T addToTemplate(final String key, final String value) {
        Builder.get().addToTemplate(key, value);
        return (T) this;
    }

    default <T extends ILocator> T setInfoMessage(final String infoMessage) {
        Builder.get().setInfoMessage(infoMessage);
        return (T) this;
    }

    default <T extends ILocator> T setVisibility(final boolean visibility) {
        Builder.get().setVisibility(visibility);
        return (T) this;
    }

    default <T extends ILocator> T setRenderMillis(final long renderMillis) {
        Builder.get().setRenderMillis(renderMillis);
        return (T) this;
    }

    default <T extends ILocator> T setActivateSeconds(final int activateSeconds) {
        Builder.get().setActivateSeconds(activateSeconds);
        return (T) this;
    }

    default <T extends ILocator> T setContainer(Locator container) {
        Builder.get().setContainer(container);
        return (T) this;
    }

    default <T extends ILocator> T setLabel(final String label, final SearchType... searchTypes) {
        Builder.get().setLabel(label, searchTypes);
        return (T) this;
    }

    default <T extends ILocator> T setLabelTag(final String labelTag) {
        Builder.get().setLabelTag(labelTag);
        return (T) this;
    }

    default <T extends ILocator> T setLabelPosition(final String labelPosition) {
        Builder.get().setLabelPosition(labelPosition);
        return (T) this;
    }

    default <T extends ILocator> T setPosition(final int position) {
        Builder.get().setPosition(position);
        return (T) this;
    }

    default <T extends ILocator> T setPosition(final Position position) {
        Builder.get().setPosition(position);
        return (T) this;
    }

    default <T extends ILocator> T setResultIdx(final int resultIdx) {
        Builder.get().setResultIdx(resultIdx);
        return (T) this;
    }

    default <T extends ILocator> T setType(final String type) {
        Builder.get().setType(type);
        return (T) this;
    }

    default <T extends ILocator> T setAttribute(final String attribute, final String value, final SearchType... searchTypes) {
        Builder.get().setAttribute(attribute, value, searchTypes);
        return (T) this;
    }

    default void setClassName(final String className) {
        Builder.get().setClassName(className);
    }
}