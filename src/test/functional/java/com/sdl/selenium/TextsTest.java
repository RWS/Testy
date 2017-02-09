package com.sdl.selenium;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by vculea on 21-10-2015.
 */
public class TextsTest extends WebLocator {
    private SearchType C1 = SearchType.EQUALS;
    private SearchType C2 = SearchType.TRIM;

    private WebLocator default_el = new WebLocator().setBaseCls("btn").setText("Cancel", C1, C2);
    private WebLocator deep_child = new WebLocator().setBaseCls("btn").setText("Cancel", SearchType.DEEP_CHILD_NODE, C1, C2);
    private WebLocator deep_child_self = new WebLocator().setBaseCls("btn").setText("Cancel", SearchType.DEEP_CHILD_NODE_OR_SELF, C1, C2);
    private WebLocator child_node = new WebLocator().setBaseCls("btn").setText("Cancel", SearchType.CHILD_NODE, C1, C2);
    private WebLocator containsAllChildNodes = new WebLocator().setBaseCls("btn").setText("|This's|was|good", SearchType.CONTAINS_ALL_CHILD_NODES);
    private WebLocator containsAll = new WebLocator().setBaseCls("btn").setText("|This's|was|good", SearchType.CONTAINS_ALL);
    private WebLocator containsAny = new WebLocator().setBaseCls("btn").setText("|This's|was|good", SearchType.CONTAINS_ANY);

    public WebLocator getDefault_el() {
        return default_el;
    }

    public WebLocator getDeep_child() {
        return deep_child;
    }

    public WebLocator getDeep_child_self() {
        return deep_child_self;
    }

    public WebLocator getChild_node() {
        return child_node;
    }

    public WebLocator getContainsAllChildNodes() {
        return containsAllChildNodes;
    }

    public WebLocator getContainsAll() {
        return containsAll;
    }

    public WebLocator getContainsAny() {
        return containsAny;
    }

    public static void main(String[] args) {
        TextsTest test = new TextsTest();
        assertThat(test.getDefault_el().getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' btn ') and normalize-space(text())='Cancel']"));
        assertThat(test.getDeep_child().getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' btn ') and count(*//text()[normalize-space(.)='Cancel']) > 0]"));
        assertThat(test.getDeep_child_self().getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' btn ') and (normalize-space(.)='Cancel' or count(*//text()[normalize-space(.)='Cancel']) > 0)]"));
        assertThat(test.getChild_node().getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' btn ') and count(text()[normalize-space(.)='Cancel']) > 0]"));
        assertThat(test.getContainsAny().getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' btn ') and (contains(text(),'\"This's\"') or contains(text(),''was'') or contains(text(),''good''))]"));
        assertThat(test.getContainsAll().getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' btn ') and contains(text(),'\"This's\"') and contains(text(),''was'') and contains(text(),''good'')]"));
        assertThat(test.getContainsAllChildNodes().getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' btn ') and count(*//text()[contains(.,'\"This's\"')]) > 0 and count(*//text()[contains(.,''was'')]) > 0 and count(*//text()[contains(.,''good'')]) > 0]"));
        WebLocatorUtils.getXPathScript(test);
    }
}
