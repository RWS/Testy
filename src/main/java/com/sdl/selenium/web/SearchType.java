package com.sdl.selenium.web;

/**
 * Contains all Search types :
 * see details for all in :
 * <p>text group</p>
 * <ul>
 * <li>{@link SearchType#EQUALS}</li>
 * <li>{@link SearchType#CONTAINS}</li>
 * <li>{@link SearchType#STARTS_WITH}</li>
 * </ul>
 * <p>trim group</p>
 * <ul>
 * <li>{@link SearchType#TRIM}</li>
 * </ul>
 * <p>child group</p>
 * <ul>
 * <li>{@link SearchType#CHILD_NODE}</li>
 * <li>{@link SearchType#DEEP_CHILD_NODE}</li>
 * <li>{@link SearchType#HTML_NODE}</li>
 * <li>{@link SearchType#CONTAINS_ALL}</li>
 * <li>{@link SearchType#CONTAINS_ALL_CHILD_NODES}</li>
 * <li>{@link SearchType#CONTAINS_ANY}</li>
 * </ul>
 */
public enum SearchType {

    EQUALS("text"),

    CONTAINS("text"),

    STARTS_WITH("text"),

    /**
     * will use : normalize-spaces on text()
     * * <pre>{@code
     * <div class="btn">            Cancel              </div>
     * }</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     * WebLocator cancelBtn = new WebLocator().setClasses("btn").setText("Cancel", SearchType.TRIM);
     * }</pre>
     */
    TRIM("trim"),

    /**
     * will not use : normalize-spaces on text()
     */
    NO_TRIM("trim"),

    /**
     * will use : translate on text()
     * * <pre>{@code
     * <div class="btn">CANCEL</div>
     * }</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     * WebLocator cancelBtn = new WebLocator().setClasses("btn").setText("cancel", SearchType.CASE_INSENSITIVE);
     * }</pre>
     */
    CASE_INSENSITIVE("sensitive"),

    /**
     * will not use : translate on text()
     */
    CASE_SENSITIVE("sensitive"),



    //FIRST_CHILD_NODE,

    /**
     * For finding elements that contain text (and text is not first node in that element).
     * eg. next button has the span.icon as first childNode in html:
     * <pre>{@code
     * <div class="btn">
     *   <span class="icon"></span>
     *   Cancel
     * </div>
     *
     * <div class="btn">
     *   Cancel
     *   <span class="icon"></span>
     * </div>
     *
     * <div class="btn">Cancel</div>
     * }</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     * WebLocator cancelBtn = new WebLocator().setClasses("btn").setText("Cancel", SearchType.CHILD_NODE);
     * }</pre>
     */
    CHILD_NODE("child"),

    /**
     * For finding elements that contain text (and text is not in any of direct childNodes in that element, but inside of them).
     * eg. next button has the span.icon as first childNode in html, and text is inside span.btn-text childNode:
     * <pre>{@code
     * <div class="btn">
     *   <span class="icon"></span>
     *   <span class="btn-text">Cancel</span>
     * </div>
     * }</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     * WebLocator cancelBtn = new WebLocator().setClasses("btn").setText("Cancel", SearchType.DEEP_CHILD_NODE);
     * }</pre>
     */
    DEEP_CHILD_NODE("child"),

    /**
     * For finding elements that contain text (and text is not in any of direct childNodes in that element, but inside of them or text is direct in that element).
     * eg. next button has the span.icon as first childNode in html, and text is inside span.btn-text childNode or text is direct in that element:
     * <pre>{@code
     * <div class="btn">
     *   <span class="icon"></span>
     *   <span class="btn-text">Cancel</span>
     * </div>
     * }</pre>
     *
     * <pre>{@code
     * <div class="btn">Cancel</div>}</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     * WebLocator cancelBtn = new WebLocator().setClasses("btn").setText("Cancel", SearchType.DEEP_CHILD_NODE_OR_SELF);
     * }</pre>
     */
    DEEP_CHILD_NODE_OR_SELF("child"),

    /**
     * TODO add better documentation and working example
     * For finding elements that contain text composed by html nodes
     * eg. "Get an instant Quote" button contains text containing html node <span>instant </span>
     * <pre>{@code
     * <div class="btn">
     *   Get an <span class="bold">instant</span> Quote
     * </div>
     * }</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     * WebLocator cancelBtn = new WebLocator().setClasses("btn").setText("Get an instant Quote", SearchType.HTML_NODE);
     * }</pre>
     */
    HTML_NODE("advance"),

    /**
     * <p>For finding elements that contain all text segments.</p>
     * <p>Segments will be made by splitting text into elements with first char of input text</p>
     * <pre>{@code
     * <div class="btn">
     *   <span class="btn-text">Cancel is a Button</span>
     * </div>}</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     *     WebLocator cancelBtn = new WebLocator().setClasses("btn")
     *          .setText("&Cancel&Button", SearchType.CONTAINS_ALL);
     * }</pre>
     */
    CONTAINS_ALL("advance"),

    /**
     * <p>For finding elements that contain all text segments.</p>
     * <p>Segments will be made by splitting text into elements with first char of input text</p>
     * <pre>{@code
     * <div class="btn">
     *   <span class="btn-text">Cancel is</span>
     *   <span class="btn-text">a Button</span>
     * </div>}</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     *     WebLocator cancelBtn = new WebLocator().setClasses("btn")
     *          .setText("&Cancel&Button", SearchType.CONTAINS_ALL_CHILD_NODES);
     * }</pre>
     */
    CONTAINS_ALL_CHILD_NODES("advance"),

    /**
     * <p>For finding elements that contain any text segments.</p>
     * <p>Segments will be made by splitting text into elements with first char of input text</p>
     * <pre>{@code
     * <div class="btn">
     *   <span class="btn-text">Cancel is a Button</span>
     * </div>
     * }</pre>
     * <p>so must be used like:</p>
     * <pre>{@code
     * WebLocator cancelBtn = new WebLocator().setClasses("btn")
     *      .setText("|Cancel|Button", SearchType.CONTAINS_ANY);
     * }</pre>
     */
    CONTAINS_ANY("advance"),

    /**
     * Override general setting regarding internationalized text for specific web locators
     * without disabling internationalization for all locators
     */
    NOT_INTERNATIONALIZED("text");

    private final String group;

    SearchType(String category) {
        this.group = category;
    }

    public String getGroup() {
        return group;
    }
}
