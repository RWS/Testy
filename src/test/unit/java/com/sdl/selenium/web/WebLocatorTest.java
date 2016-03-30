package com.sdl.selenium.web;

import com.sdl.selenium.extjs3.ExtJsComponent;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.Assert.assertEquals;

public class WebLocatorTest {
    private static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new WebLocator(), "//*"},
                {new WebLocator().withTag("td"), "//td"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls"), "//td[contains(concat(' ', @class, ' '), ' BaseCls ')]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes"), "//td[contains(concat(' ', @class, ' '), ' BaseCls ') and contains(concat(' ', @class, ' '), ' classes ')]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls"), "//td[contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ')]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls"), "//td[contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls").withId("Id"), "//td[@id='Id' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls").withId("Id").withLabel("Label"), "//label[text()='Label']//following-sibling::*//td[@id='Id' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls").withId("Id").withLabel("Label").withLabelTag("info"), "//info[text()='Label']//following-sibling::*//td[@id='Id' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls").withId("Id").withLabel("Label").withLabelTag("info").withName("Name"), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls").withId("Id").withLabel("Label").withLabelTag("info").withName("Name").withElxPathSuffix("elPath", "@value='Test'"), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test']"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls").withId("Id").withLabel("Label").withLabelTag("info").withName("Name").withElxPathSuffix("elPath", "@value='Test'").withVisibility(true), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls").withId("Id").withLabel("Label").withLabelTag("info").withName("Name").withElxPathSuffix("elPath", "@value='Test'").withVisibility(true).withStyle("display: block"), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and contains(@style ,'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls").withId("Id").withLabel("Label").withLabelTag("info").withName("Name").withElxPathSuffix("elPath", "@value='Test'").withVisibility(true).withStyle("display: block").withPosition(2), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and contains(@style ,'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls").withId("Id").withLabel("Label").withLabelTag("info").withName("Name").withElxPathSuffix("elPath", "@value='Test'").withVisibility(true).withStyle("display: block").withPosition(2).withContainer(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and contains(@style ,'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls").withId("Id").withLabel("Label").withLabelTag("info").withName("Name").withElxPathSuffix("elPath", "@value='Test'").withVisibility(true).withStyle("display: block").withPosition(2).withContainer(container).withText("Text"), "//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and contains(text(),'Text') and contains(@style ,'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls").withId("Id").withLabel("Label").withLabelTag("info").withName("Name").withElxPathSuffix("elPath", "@value='Test'").withVisibility(true).withStyle("display: block").withPosition(2).withContainer(container).withText("Text").withTitle("Title"), "//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @title='Title' and @value='Test' and contains(text(),'Text') and contains(@style ,'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls").withId("Id").withLabel("Label").withLabelTag("info").withName("Name").withElxPathSuffix("elPath", "@value='Test'").withVisibility(true).withStyle("display: block").withPosition(2).withContainer(container).withText("Text").withTitle("Title").withRoot("./"), "//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//./td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @title='Title' and @value='Test' and contains(text(),'Text') and contains(@style ,'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls").withId("Id").withLabel("Label").withLabelTag("info").withName("Name").withElxPathSuffix("elPath", "@value='Test'").withVisibility(true).withStyle("display: block").withPosition(2).withContainer(container).withText("Text").withTitle("Title").withRoot("./").withResultIdx(2), "(//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//./td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @title='Title' and @value='Test' and contains(text(),'Text') and contains(@style ,'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2])[2]"},
                {new WebLocator().withTag("td").withBaseCls("BaseCls").withClasses("classes").withCls("Cls").withExcludeClasses("noCls").withId("Id").withLabel("Label").withLabelTag("info").withName("Name").withElxPathSuffix("elPath", "@value='Test'").withVisibility(true).withStyle("display: block").withPosition(2).withContainer(container).withText("Text").withTitle("Title").withRoot("./").withResultIdx(2).withType("type"), "(//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//./td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @title='Title' and @type='type' and @value='Test' and contains(text(),'Text') and contains(@style ,'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2])[2]"},

                {new WebLocator("testcls"), "//*[contains(concat(' ', @class, ' '), ' testcls ')]"},
                {new WebLocator(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*"},
                {new WebLocator(container).withClasses("Cls"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' Cls ')]"},
                {new WebLocator(container).withElxPath("//*[contains(@class, 'testcls')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls')]"},
                {new WebLocator("testcls", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' testcls ')]"},
                {new WebLocator("text", "testcls", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' testcls ') and contains(text(),'text')]"},
                {new WebLocator().withId("ID"), "//*[@id='ID']"},
                {new WebLocator(container).withTag("textarea"), "//*[contains(concat(' ', @class, ' '), ' container ')]//textarea"},
                {new WebLocator(container).withElxPath("//*[contains(@class, 'testcls')]").withTag("textarea"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls')]"},
                {new WebLocator().withElxPathSuffix("has-div", "count(div) > 0"), "//*[count(div) > 0]"},
                {new WebLocator().withExcludeClasses("cls1", "cls2"), "//*[not(contains(@class, 'cls1')) and not(contains(@class, 'cls2'))]"},
        };
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProviderText() {
        String text = "WebLocator text for search type";
        String cls = "searchTextType";
        return new Object[][]{
                {new WebLocator().withClasses(cls).withText(text, SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and contains(text(),'" + text + "')]"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.EQUALS), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and text()='" + text + "']"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.STARTS_WITH), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and starts-with(text(),'" + text + "')]"},

                {new WebLocator().withClasses(cls).withText(text, SearchType.CONTAINS, SearchType.CASE_INSENSITIVE).addSearchTextType(SearchType.CASE_SENSITIVE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and contains(text(),'" + text + "')]"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.EQUALS, SearchType.CASE_INSENSITIVE).addSearchTextType(SearchType.CASE_SENSITIVE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and text()='" + text + "']"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.STARTS_WITH, SearchType.CASE_INSENSITIVE).addSearchTextType(SearchType.CASE_SENSITIVE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and starts-with(text(),'" + text + "')]"},

                {new WebLocator().withClasses(cls).withText(text, SearchType.CASE_INSENSITIVE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and contains(translate(text(),'WEBLOCATOR TEXT FOR SEARCH TYPE','weblocator text for search type'),'weblocator text for search type')]"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.CONTAINS, SearchType.CASE_INSENSITIVE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and contains(translate(text(),'WEBLOCATOR TEXT FOR SEARCH TYPE','weblocator text for search type'),'weblocator text for search type')]"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.EQUALS, SearchType.CASE_INSENSITIVE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and translate(text(),'WEBLOCATOR TEXT FOR SEARCH TYPE','weblocator text for search type')='weblocator text for search type']"},
                {new WebLocator().withClasses(cls).withText(text.substring(0, 10), SearchType.STARTS_WITH, SearchType.CASE_INSENSITIVE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and starts-with(translate(text(),'WEBLOCATOR','weblocator'),'weblocator')]"},

                {new WebLocator().withClasses(cls).withText("|" + text.replaceAll(" ", "|"), SearchType.CASE_INSENSITIVE, SearchType.CONTAINS_ALL_CHILD_NODES), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[contains(translate(.,'WEBLOCATOR','weblocator'),'weblocator')]) > 0 and count(*//text()[contains(translate(.,'TEXT','text'),'text')]) > 0 and count(*//text()[contains(translate(.,'FOR','for'),'for')]) > 0 and count(*//text()[contains(translate(.,'SEARCH','search'),'search')]) > 0 and count(*//text()[contains(translate(.,'TYPE','type'),'type')]) > 0]"},

                {new WebLocator().withClasses(cls).withText(text, SearchType.CONTAINS, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[contains(.,'" + text + "')]) > 0]"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.EQUALS, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[.='" + text + "']) > 0]"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.STARTS_WITH, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[starts-with(.,'" + text + "')]) > 0]"},

                {new WebLocator().withClasses(cls).withText(text, SearchType.CONTAINS, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[contains(.,'" + text + "')]) > 0]"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.EQUALS, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[.='" + text + "']) > 0]"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.STARTS_WITH, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[starts-with(.,'" + text + "')]) > 0]"},

                {new WebLocator().withClasses(cls).withText(text, SearchType.CONTAINS, SearchType.TRIM), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and contains(normalize-space(text()),'" + text + "')]"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.EQUALS, SearchType.TRIM), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and normalize-space(text())='" + text + "']"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.STARTS_WITH, SearchType.TRIM), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and starts-with(normalize-space(text()),'" + text + "')]"},

                {new WebLocator().withClasses(cls).withText(text, SearchType.CONTAINS, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[contains(normalize-space(.),'" + text + "')]) > 0]"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.EQUALS, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[normalize-space(.)='" + text + "']) > 0]"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.STARTS_WITH, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[starts-with(normalize-space(.),'" + text + "')]) > 0]"},

                {new WebLocator().withClasses(cls).withText(text, SearchType.CONTAINS, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[contains(normalize-space(.),'" + text + "')]) > 0]"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.EQUALS, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[normalize-space(.)='" + text + "']) > 0]"},
                {new WebLocator().withClasses(cls).withText(text, SearchType.STARTS_WITH, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[starts-with(normalize-space(.),'" + text + "')]) > 0]"},

                {new WebLocator().withClasses(cls).withText(text, SearchType.HTML_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and (normalize-space(concat(./*[1]//text(), ' ', text()[1], ' ', ./*[2]//text(), ' ', text()[2], ' ', ./*[3]//text(), ' ', text()[3], ' ', ./*[4]//text(), ' ', text()[4], ' ', ./*[5]//text(), ' ', text()[5]))='WebLocator text for search type' or normalize-space(concat(text()[1], ' ', ./*[1]//text(), ' ', text()[2], ' ', ./*[2]//text(), ' ', text()[3], ' ', ./*[3]//text(), ' ', text()[4], ' ', ./*[4]//text(), ' ', text()[5], ' ', ./*[5]//text()))='WebLocator text for search type')]"},
                {new ExtJsComponent().withVisibility(true), "//*[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0]"},
                {new ExtJsComponent().withCls("Cls").withVisibility(true), "//*[@class='Cls' and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0]"},

        };
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProviderAttributes() {
        return new Object[][]{
                {new WebLocator().withAttribute("data", "value", SearchType.CONTAINS), "//*[contains(@data,'value')]"},
                {new WebLocator().withAttribute("data", "value", SearchType.EQUALS), "//*[@data='value']"},
                {new WebLocator().withAttribute("data", "value", SearchType.STARTS_WITH), "//*[starts-with(@data,'value')]"},

                {new WebLocator().withAttribute("data", "value", SearchType.CONTAINS, SearchType.CASE_INSENSITIVE).addSearchTextType(SearchType.CASE_SENSITIVE), "//*[contains(translate(@data,'VALUE','value'),'value')]"},
                {new WebLocator().withAttribute("data", "value", SearchType.EQUALS, SearchType.CASE_INSENSITIVE).addSearchTextType(SearchType.CASE_SENSITIVE), "//*[translate(@data,'VALUE','value')='value']"},
                {new WebLocator().withAttribute("data", "value", SearchType.STARTS_WITH, SearchType.CASE_INSENSITIVE).addSearchTextType(SearchType.CASE_SENSITIVE), "//*[starts-with(translate(@data,'VALUE','value'),'value')]"},

                {new WebLocator().withAttribute("data", "value", SearchType.CONTAINS, SearchType.TRIM), "//*[contains(normalize-space(@data),'value')]"},
                {new WebLocator().withAttribute("data", "value", SearchType.EQUALS, SearchType.TRIM), "//*[normalize-space(@data)='value']"},
                {new WebLocator().withAttribute("data", "value", SearchType.STARTS_WITH, SearchType.TRIM), "//*[starts-with(normalize-space(@data),'value')]"},

//                {new WebLocator().withAttribute("data", "value", SearchType.CONTAINS, SearchType.CHILD_NODE), "//*[contains(@data,'value')]"},
//                {new WebLocator().withAttribute("data", "value", SearchType.EQUALS, SearchType.CHILD_NODE), "//*[@data='value']"},
//                {new WebLocator().withAttribute("data", "value", SearchType.STARTS_WITH, SearchType.CHILD_NODE), "//*[starts-with(@data,'value')]"},

//                {new WebLocator().withAttribute("data", "value", SearchType.CONTAINS, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[contains(.,'')]) > 0]"},
//                {new WebLocator().withAttribute("data", "value", SearchType.EQUALS, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[.='']) > 0]"},
//                {new WebLocator().withAttribute("data", "value", SearchType.STARTS_WITH, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[starts-with(.,'')]) > 0]"},


//                {new WebLocator().withAttribute("data", "value", SearchType.CONTAINS, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[contains(normalize-space(.),'')]) > 0]"},
//                {new WebLocator().withAttribute("data", "value", SearchType.EQUALS, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[normalize-space(.)='']) > 0]"},
//                {new WebLocator().withAttribute("data", "value", SearchType.STARTS_WITH, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[starts-with(normalize-space(.),'')]) > 0]"},

//                {new WebLocator().withAttribute("data", "value", SearchType.CONTAINS, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[contains(normalize-space(.),'')]) > 0]"},
//                {new WebLocator().withAttribute("data", "value", SearchType.EQUALS, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[normalize-space(.)='']) > 0]"},
//                {new WebLocator().withAttribute("data", "value", SearchType.STARTS_WITH, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[starts-with(normalize-space(.),'')]) > 0]"},

//                {new WebLocator().withAttribute("data", "value", SearchType.HTML_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and (normalize-space(concat(./*[1]//text(), ' ', text()[1], ' ', ./*[2]//text(), ' ', text()[2], ' ', ./*[3]//text(), ' ', text()[3], ' ', ./*[4]//text(), ' ', text()[4], ' ', ./*[5]//text(), ' ', text()[5]))='WebLocator text for search type' or normalize-space(concat(text()[1], ' ', ./*[1]//text(), ' ', text()[2], ' ', ./*[2]//text(), ' ', text()[3], ' ', ./*[3]//text(), ' ', text()[4], ' ', ./*[4]//text(), ' ', text()[5], ' ', ./*[5]//text()))='WebLocator text for search type')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(WebLocator el, String expectedXpath) {
        assertThat(el.getXPath(), equalTo(expectedXpath));
    }

    @Test(dataProvider = "testConstructorPathDataProviderText")
    public void getPathSelectorCorrectlyFromConstructorsByText(WebLocator el, String expectedXpath) {
        assertThat(el.getXPath(), equalTo(expectedXpath));
    }

    @Test(dataProvider = "testConstructorPathDataProviderAttributes")
    public void getPathSelectorCorrectlyFromConstructorsByAttributes(WebLocator el, String expectedXpath) {
        assertThat(el.getXPath(), equalTo(expectedXpath));
    }

    @Test
    public void getPathSelectorSetTagWhenWebLocatorHasClsAndContainer() {
        WebLocator el = new WebLocator("testcls", container);
        el.withTag("textarea");
        assertEquals(el.getXPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]//textarea[contains(concat(' ', @class, ' '), ' testcls ')]");
    }

    @Test
    public void getPathSelectorSetTagWhenWebLocatorHasTextAndClsAndContainer() {
        WebLocator el = new WebLocator("text", "testcls", container);
        el.withTag("textarea");
        assertEquals(el.getXPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]//textarea[contains(concat(' ', @class, ' '), ' testcls ') and contains(text(),'text')]");
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasContainerAndPath() {
        WebLocator el = new WebLocator(container).withElxPath("//*[contains(@class, 'testcls')]");
        el.withId("ID");
        assertEquals(el.getXPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls')]");
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasClsAndContainer() {
        WebLocator el = new WebLocator("testcls", container);
        el.withId("ID");
        assertEquals(el.getXPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[@id='ID' and contains(concat(' ', @class, ' '), ' testcls ')]");
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasTextAndClsAndContainer() {
        WebLocator el = new WebLocator("text", "testcls", container);
        el.withId("ID");
        assertEquals(el.getXPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[@id='ID' and contains(concat(' ', @class, ' '), ' testcls ') and contains(text(),'text')]");
    }

    //@Test
    // TODO fix getPathSelectorSetIdWhenWebLocatorHasXPath
    public void getPathSelectorSetIdWhenWebLocatorHasXPath() {
        WebLocator el = new WebLocator().withElxPath("//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls') and contains(text(),'text')]");
        el.withId("ID");
        assertEquals(el.getXPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[@id='ID' and contains(@class, 'testcls') and contains(text(),'text')]");
    }

    @Test
    public void createInstancesWithBuilders() {
        WebLocator locatorBuilder1 = new WebLocator().withTag("div").withId("ID1");
        assertEquals(locatorBuilder1.getPathBuilder().getTag(), "div");
        assertEquals(locatorBuilder1.getPathBuilder().getId(), "ID1");
        assertEquals(locatorBuilder1.getPathBuilder().getClassName(), "WebLocator");
    }

    @Test
    public void shouldShowClassInToStringWhenHasOneClass() {
        WebLocator locator = new WebLocator().withClasses("cls1");
        assertEquals(locator.getPathBuilder().toString(), "cls1");
    }

    @Test
    public void shouldShowClassesInToStringWhenHasManyClass() {
        WebLocator locator = new WebLocator().withClasses("cls1", "cls2");
        assertEquals(locator.getPathBuilder().toString(), "[cls1, cls2]");
    }

    @Test
    public void resetSearchTextType() {
        WebLocator locator = new WebLocator().withText("text", SearchType.EQUALS);
        assertEquals(locator.getXPath(), "//*[text()='text']");
        locator.withSearchTextType(null);
        assertEquals(locator.getXPath(), "//*[contains(text(),'text')]");
    }

    @Test
    public void setSearchTextType() {
        WebLocator locator = new WebLocator().withText("text", SearchType.STARTS_WITH);
        assertEquals(locator.getPathBuilder().getSearchTextType().size(), 1);
    }

    @Test
    public void setResultIdx() {
        WebLocator locator = new WebLocator().withText("text").withResultIdx(1);
        WebLocator locator1 = new WebLocator(locator).withText("text").withResultIdx(2);
        assertEquals(locator1.getXPath(), "((//*[contains(text(),'text')])[1]//*[contains(text(),'text')])[2]");
        locator1.withResultIdx(-1);
        assertEquals(locator1.getXPath(), "(//*[contains(text(),'text')])[1]//*[contains(text(),'text')]");
        locator.withResultIdx(-1);
        assertEquals(locator1.getXPath(), "//*[contains(text(),'text')]//*[contains(text(),'text')]");
    }
}
