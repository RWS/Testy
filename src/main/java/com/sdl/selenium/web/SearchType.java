package com.sdl.selenium.web;

/**
 * Contains all Search types :
 * see details for all in :
 * <p>{@link SearchType#EQUALS}</p>
 * <p>{@link SearchType#CONTAINS}</p>
 * <p>{@link SearchType#STARTS_WITH}</p>
 * <p>{@link SearchType#TRIM}</p>
 * <p>{@link SearchType#CHILD_NODE}</p>
 * <p>{@link SearchType#DEEP_CHILD_NODE}</p>
 */
public enum SearchType {

    EQUALS,

    CONTAINS,

    STARTS_WITH,

    /**
     * will use : normalize-spaces on text()
     */
    TRIM,

    /**
     * For finding elements that contain text (and text is not first node in that element).
     * eg. next button has the span.icon as first childNode in html:
     * <pre>
    &lt;div class="btn">
        &lt;span class="icon">&lt;/span>
        Cancel
    &lt;/div></pre>
     * <p>so must be used like:</p>
     * <pre>WebLocator cancelBtn = new WebLocator().setClasses("btn").setText("Cancel", SearchType.CHILD_NODE);</pre>
     */
    CHILD_NODE,

    /**
     * For finding elements that contain text (and text is not in any of direct chileNodes in that element, but inside of them).
     * eg. next button has the span.icon as first childNode in html, and text is inside span.btn-text childNode:
     * <pre>
     &lt;div class="btn">
        &lt;span class="icon">&lt;/span>
        &lt;span class="btn-text">Cancel&lt;/span>
     &lt;/div></pre>
     * <p>so must be used like:</p>
     * <pre>WebLocator cancelBtn = new WebLocator().setClasses("btn").setText("Cancel", SearchType.DEEP_CHILD_NODE);</pre>
     */
    DEEP_CHILD_NODE

}
