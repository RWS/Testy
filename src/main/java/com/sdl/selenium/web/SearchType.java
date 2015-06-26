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
 * <p>{@link SearchType#HTML_NODE}</p>
 * <p>{@link SearchType#CONTAINS_ALL}</p>
 * <p>{@link SearchType#CONTAINS_ANY}</p>
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
     * <pre>{@code
     * <div class="btn">
     *   <span class="icon"></span>
     *   Cancel
     * </div>
     * }</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     * WebLocator cancelBtn = new WebLocator().setClasses("btn").setText("Cancel", SearchType.CHILD_NODE);
     * }</pre>
     */
    CHILD_NODE,

    /**
     * For finding elements that contain text (and text is not in any of direct childNodes in that element, but inside of them).
     * eg. next button has the span.icon as first childNode in html, and text is inside span.btn-text childNode:
     * <pre>{@code
     * <div class="btn">
     *  <span class="icon"></span>
     *  <span class="btn-text">Cancel</span>
     * </div>
     * }</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     * WebLocator cancelBtn = new WebLocator().setClasses("btn").setText("Cancel", SearchType.DEEP_CHILD_NODE);
     * }</pre>
     */
    DEEP_CHILD_NODE,

    /**
     * For finding elements that contain text (and text is not in any of direct childNodes in that element, but inside of them or text is direct in that element).
     * eg. next button has the span.icon as first childNode in html, and text is inside span.btn-text childNode or text is direct in that element:
     * <pre>{@code
     * <div class="btn">
     *  <span class="icon"></span>
     *  <span class="btn-text">Cancel</span>
     * </div>
     * }</pre>
     *
     * <pre>{@code
     * <div class="btn">Cancel</div>}</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     * WebLocator cancelBtn = new WebLocator().setClasses("btn").setText("Cancel", SearchType.DEEP_CHILD_NODE);
     * }</pre>
     */
    DEEP_CHILD_NODE_OR_SELF,

    /**
     * TODO add better documentation and working example
     * For finding elements that contain text composed by html nodes
     * eg. "Get an instant Quote" button contains text containing html node <span>instant </span>
     * <pre>{@code
     * <div class="btn">
     * Get an <span class="bold">instant</span> Quote
     * </div>
     * }</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     * WebLocator cancelBtn = new WebLocator().setClasses("btn").setText("Get an instant Quote", SearchType.HTML_NODE);
     * }</pre>
     */
    HTML_NODE,

    /**
     * <p>For finding elements that contain all text segments.</p>
     * <p>Segments will be made by splitting text into elements with first char of input text</p>
     * <pre>{@code
     * <div class="btn">
     * <span class="btn-text">Cancel is a Button</span>
     * </div>}</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     *     WebLocator cancelBtn = new WebLocator().setClasses("btn")
     *          .setText("&Cancel&Button", SearchType.CONTAINS_ALL);
     * }</pre>
     */
    CONTAINS_ALL,

    /**
     * <p>For finding elements that contain any text segments.</p>
     * <p>Segments will be made by splitting text into elements with first char of input text</p>
     * <pre>{@code
     * <div class="btn">
     *  <span class="btn-text">Cancel is a Button</span>
     * </div>
     * }</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     * WebLocator cancelBtn = new WebLocator().setClasses("btn")
     *      .setText("|Cancel|Button", SearchType.CONTAINS_ANY);
     * }</pre>
     */
    CONTAINS_ANY

}
