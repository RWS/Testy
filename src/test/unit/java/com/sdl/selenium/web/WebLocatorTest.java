package com.sdl.selenium.web;

import com.sdl.selenium.extjs3.ExtJsComponent;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WebLocatorTest {
    private static final WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new WebLocator(), "//*"},
                {new WebLocator().setTag("td"), "//td"},
                {new WebLocator().setText("text"), "//*[contains(text(),'text')]"},
                {new WebLocator().setClasses("cls"), "//*[contains(concat(' ', @class, ' '), ' cls ')]"},
                {new WebLocator().setExcludeClasses("excludeCls"), "//*[not(contains(@class, 'excludeCls'))]"},
                {new WebLocator().setVisibility(true), "//*[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new WebLocator().setPosition(1), "//*[position() = 1]"},
                {new WebLocator().setPosition(Position.FIRST), "//*[position() = first()]"},
                {new WebLocator().setBaseCls("base"), "//*[contains(concat(' ', @class, ' '), ' base ')]"},
                {new WebLocator().setCls("cls"), "//*[@class='cls']"},
                {new WebLocator().setLabel("label"), "//label[text()='label']//following-sibling::*//*"},
                {new WebLocator().setId("id"), "//*[@id='id']"},
                {new WebLocator().setElPathSuffix("suffix", "//suffix"), "//*[//suffix]"},
                {new WebLocator().setLabelTag("labelT"), "//*"},
                {new WebLocator().setRoot("/"), "/*"},
                {new WebLocator().setStyle("style"), "//*[contains(@style, 'style')]"},
                {new WebLocator().setName("name"), "//*[@name='name']"},
                {new WebLocator().setAttribute("date", "day"), "//*[@date='day']"},
                {new WebLocator().setLabelPosition("//..//"), "//*"},
                {new WebLocator().setResultIdx(1), "(//*)[1]"},
                {new WebLocator().setSearchTitleType(SearchType.DEEP_CHILD_NODE), "//*"},
                {new WebLocator().setTitle("title"), "//*[@title='title']"},
                {new WebLocator().setChildNodes(new WebLocator("t", "cc")), "//*[count(.//t[contains(concat(' ', @class, ' '), ' cc ')]) > 0]"},
                {new WebLocator().setChildNodes(), "//*"},
                {new WebLocator().setChildNodes(new WebLocator("cls"), null), "//*[count(.//*[contains(concat(' ', @class, ' '), ' cls ')]) > 0]"},

                {new WebLocator().setTag("td").setBaseCls("BaseCls"), "//td[contains(concat(' ', @class, ' '), ' BaseCls ')]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes"), "//td[contains(concat(' ', @class, ' '), ' BaseCls ') and contains(concat(' ', @class, ' '), ' classes ')]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls"), "//td[contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ')]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls"), "//td[contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id"), "//td[@id='Id' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label"), "//label[text()='Label']//following-sibling::*//td[@id='Id' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info"), "//info[text()='Label']//following-sibling::*//td[@id='Id' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name"), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'"), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test']"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block"), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and contains(@style, 'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and contains(@style, 'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and contains(@style, 'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container).setText("Text"), "//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and contains(text(),'Text') and @value='Test' and contains(@style, 'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container).setText("Text").setTitle("Title"), "//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @title='Title' and contains(text(),'Text') and @value='Test' and contains(@style, 'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container).setText("Text").setTitle("Title").setRoot("./"), "//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//./td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @title='Title' and contains(text(),'Text') and @value='Test' and contains(@style, 'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container).setText("Text").setTitle("Title").setRoot("./").setResultIdx(2), "(//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//./td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @title='Title' and contains(text(),'Text') and @value='Test' and contains(@style, 'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2])[2]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container).setText("Text").setTitle("Title").setRoot("./").setResultIdx(2).setType("type"), "(//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//./td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @title='Title' and @type='type' and contains(text(),'Text') and @value='Test' and contains(@style, 'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2])[2]"},

                {new WebLocator().setClasses("cls1", "cls2"), "//*[contains(concat(' ', @class, ' '), ' cls1 ') and contains(concat(' ', @class, ' '), ' cls2 ')]"},

                {new WebLocator("testcls"), "//*[contains(concat(' ', @class, ' '), ' testcls ')]"},
                {new WebLocator(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*"},
                {new WebLocator(container).setClasses("Cls"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' Cls ')]"},
                {new WebLocator(container).setElPath("//*[contains(@class, 'testcls')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls')]"},
                {new WebLocator("testcls", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' testcls ')]"},
                {new WebLocator("text", "testcls", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' testcls ') and contains(text(),'text')]"},
                {new WebLocator().setId("ID"), "//*[@id='ID']"},
                {new WebLocator(container).setTag("textarea"), "//*[contains(concat(' ', @class, ' '), ' container ')]//textarea"},
                {new WebLocator(container).setElPath("//*[contains(@class, 'testcls')]").setTag("textarea"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls')]"},
                {new WebLocator().setElPathSuffix("has-div", "count(div) > 0"), "//*[count(div) > 0]"},
                {new WebLocator().setExcludeClasses("cls1", "cls2"), "//*[not(contains(@class, 'cls1')) and not(contains(@class, 'cls2'))]"},
        };
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProviderText() {
        String text = "WebLocator text for search type";
        String cls = "searchTextType";
        return new Object[][]{
                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and contains(text(),'" + text + "')]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and text()='" + text + "']"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.STARTS_WITH), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and starts-with(text(),'" + text + "')]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS, SearchType.CASE_SENSITIVE).addSearchTextType(SearchType.CASE_INSENSITIVE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and contains(translate(text(),'WEBLOCATOR TEXT FOR SEARCH TYPE','weblocator text for search type'),'weblocator text for search type')]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS, SearchType.CASE_SENSITIVE).addSearchTextType(SearchType.CASE_INSENSITIVE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and translate(text(),'WEBLOCATOR TEXT FOR SEARCH TYPE','weblocator text for search type')='weblocator text for search type']"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.STARTS_WITH, SearchType.CASE_SENSITIVE).addSearchTextType(SearchType.CASE_INSENSITIVE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and starts-with(translate(text(),'WEBLOCATOR TEXT FOR SEARCH TYPE','weblocator text for search type'),'weblocator text for search type')]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.CASE_INSENSITIVE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and contains(translate(text(),'WEBLOCATOR TEXT FOR SEARCH TYPE','weblocator text for search type'),'weblocator text for search type')]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS, SearchType.CASE_INSENSITIVE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and contains(translate(text(),'WEBLOCATOR TEXT FOR SEARCH TYPE','weblocator text for search type'),'weblocator text for search type')]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS, SearchType.CASE_INSENSITIVE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and translate(text(),'WEBLOCATOR TEXT FOR SEARCH TYPE','weblocator text for search type')='weblocator text for search type']"},
                {new WebLocator().setClasses(cls).setText(text.substring(0, 10), SearchType.STARTS_WITH, SearchType.CASE_INSENSITIVE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and starts-with(translate(text(),'WEBLOCATOR','weblocator'),'weblocator')]"},

                {new WebLocator().setClasses(cls).setText("|" + text.replaceAll(" ", "|"), SearchType.CASE_INSENSITIVE, SearchType.CONTAINS_ALL_CHILD_NODES), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[contains(translate(.,'WEBLOCATOR','weblocator'),'weblocator')]) > 0 and count(*//text()[contains(translate(.,'TEXT','text'),'text')]) > 0 and count(*//text()[contains(translate(.,'FOR','for'),'for')]) > 0 and count(*//text()[contains(translate(.,'SEARCH','search'),'search')]) > 0 and count(*//text()[contains(translate(.,'TYPE','type'),'type')]) > 0]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[contains(.,'" + text + "')]) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[.='" + text + "']) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.STARTS_WITH, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[starts-with(.,'" + text + "')]) > 0]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[contains(.,'" + text + "')]) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[.='" + text + "']) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.STARTS_WITH, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[starts-with(.,'" + text + "')]) > 0]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS, SearchType.TRIM), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and contains(normalize-space(text()),'" + text + "')]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS, SearchType.TRIM), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and normalize-space(text())='" + text + "']"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.STARTS_WITH, SearchType.TRIM), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and starts-with(normalize-space(text()),'" + text + "')]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[contains(normalize-space(.),'" + text + "')]) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[normalize-space(.)='" + text + "']) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.STARTS_WITH, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[starts-with(normalize-space(.),'" + text + "')]) > 0]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[contains(normalize-space(.),'" + text + "')]) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[normalize-space(.)='" + text + "']) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.STARTS_WITH, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[starts-with(normalize-space(.),'" + text + "')]) > 0]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS, SearchType.TRIM, SearchType.DEEP_CHILD_NODE_OR_SELF), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and (contains(normalize-space(.),'WebLocator text for search type') or count(*//text()[contains(normalize-space(.),'WebLocator text for search type')]) > 0)]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS, SearchType.TRIM, SearchType.DEEP_CHILD_NODE_OR_SELF), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and (normalize-space(.)='WebLocator text for search type' or count(*//text()[normalize-space(.)='WebLocator text for search type']) > 0)]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.STARTS_WITH, SearchType.TRIM, SearchType.DEEP_CHILD_NODE_OR_SELF), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and (starts-with(normalize-space(.),'WebLocator text for search type') or count(*//text()[starts-with(normalize-space(.),'WebLocator text for search type')]) > 0)]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.HTML_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and contains(.,'WebLocator text for search type')]"},
                {new ExtJsComponent().setVisibility(true), "//*[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0]"},
                {new ExtJsComponent().setCls("Cls").setVisibility(true), "//*[@class='Cls' and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0]"},

        };
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProviderAttributes() {
        return new Object[][]{
                {new WebLocator().setAttribute("data", ""), "//*[@data]"},
                {new WebLocator().setAttribute("data", "value", SearchType.CONTAINS), "//*[contains(@data,'value')]"},
                {new WebLocator().setAttribute("data", "value", SearchType.EQUALS), "//*[@data='value']"},
                {new WebLocator().setAttribute("data", "value", SearchType.STARTS_WITH), "//*[starts-with(@data,'value')]"},

                {new WebLocator().setAttribute("data", "value", SearchType.CONTAINS, SearchType.CASE_INSENSITIVE).addSearchTextType(SearchType.CASE_SENSITIVE), "//*[contains(translate(@data,'VALUE','value'),'value')]"},
                {new WebLocator().setAttribute("data", "value", SearchType.EQUALS, SearchType.CASE_INSENSITIVE).addSearchTextType(SearchType.CASE_SENSITIVE), "//*[translate(@data,'VALUE','value')='value']"},
                {new WebLocator().setAttribute("data", "value", SearchType.STARTS_WITH, SearchType.CASE_INSENSITIVE).addSearchTextType(SearchType.CASE_SENSITIVE), "//*[starts-with(translate(@data,'VALUE','value'),'value')]"},

                {new WebLocator().setAttribute("data", "value", SearchType.CONTAINS, SearchType.TRIM), "//*[contains(normalize-space(@data),'value')]"},
                {new WebLocator().setAttribute("data", "value", SearchType.EQUALS, SearchType.TRIM), "//*[normalize-space(@data)='value']"},
                {new WebLocator().setAttribute("data", "value", SearchType.STARTS_WITH, SearchType.TRIM), "//*[starts-with(normalize-space(@data),'value')]"},

                {new WebLocator().setAttributes("data", "value"), "//*[contains(concat(' ', @data, ' '), ' value ')]"},
                {new WebLocator().setAttributes("data", "value", "value2"), "//*[contains(concat(' ', @data, ' '), ' value ') and contains(concat(' ', @data, ' '), ' value2 ')]"},

//                {new WebLocator().setAttribute("data", "value", SearchType.CONTAINS, SearchType.CHILD_NODE), "//*[contains(@data,'value')]"},
//                {new WebLocator().setAttribute("data", "value", SearchType.EQUALS, SearchType.CHILD_NODE), "//*[@data='value']"},
//                {new WebLocator().setAttribute("data", "value", SearchType.STARTS_WITH, SearchType.CHILD_NODE), "//*[starts-with(@data,'value')]"},

//                {new WebLocator().setAttribute("data", "value", SearchType.CONTAINS, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[contains(.,'')]) > 0]"},
//                {new WebLocator().setAttribute("data", "value", SearchType.EQUALS, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[.='']) > 0]"},
//                {new WebLocator().setAttribute("data", "value", SearchType.STARTS_WITH, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[starts-with(.,'')]) > 0]"},


//                {new WebLocator().setAttribute("data", "value", SearchType.CONTAINS, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[contains(normalize-space(.),'')]) > 0]"},
//                {new WebLocator().setAttribute("data", "value", SearchType.EQUALS, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[normalize-space(.)='']) > 0]"},
//                {new WebLocator().setAttribute("data", "value", SearchType.STARTS_WITH, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[starts-with(normalize-space(.),'')]) > 0]"},

//                {new WebLocator().setAttribute("data", "value", SearchType.CONTAINS, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[contains(normalize-space(.),'')]) > 0]"},
//                {new WebLocator().setAttribute("data", "value", SearchType.EQUALS, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[normalize-space(.)='']) > 0]"},
//                {new WebLocator().setAttribute("data", "value", SearchType.STARTS_WITH, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[starts-with(normalize-space(.),'')]) > 0]"},

//                {new WebLocator().setAttribute("data", "value", SearchType.HTML_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and (normalize-space(concat(./*[1]//text(), ' ', text()[1], ' ', ./*[2]//text(), ' ', text()[2], ' ', ./*[3]//text(), ' ', text()[3], ' ', ./*[4]//text(), ' ', text()[4], ' ', ./*[5]//text(), ' ', text()[5]))='WebLocator text for search type' or normalize-space(concat(text()[1], ' ', ./*[1]//text(), ' ', text()[2], ' ', ./*[2]//text(), ' ', text()[3], ' ', ./*[3]//text(), ' ', text()[4], ' ', ./*[4]//text(), ' ', text()[5], ' ', ./*[5]//text()))='WebLocator text for search type')]"},
        };
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProviderNotAttributes() {
        return new Object[][]{
                {new WebLocator().setAttribute("data", null), "//*"},
                {new WebLocator().setAttribute("data", "value", SearchType.NOT, SearchType.CONTAINS), "//*[not(contains(@data,'value'))]"},
                {new WebLocator().setAttribute("data", "value", SearchType.NOT, SearchType.EQUALS), "//*[not(@data='value')]"},
                {new WebLocator().setAttribute("data", "value", SearchType.NOT, SearchType.STARTS_WITH), "//*[not(starts-with(@data,'value'))]"},

                {new WebLocator().setAttribute("data", "value", SearchType.NOT, SearchType.CONTAINS, SearchType.CASE_INSENSITIVE).addSearchTextType(SearchType.CASE_SENSITIVE), "//*[not(contains(translate(@data,'VALUE','value'),'value'))]"},
                {new WebLocator().setAttribute("data", "value", SearchType.NOT, SearchType.EQUALS, SearchType.CASE_INSENSITIVE).addSearchTextType(SearchType.CASE_SENSITIVE), "//*[not(translate(@data,'VALUE','value')='value')]"},
                {new WebLocator().setAttribute("data", "value", SearchType.NOT, SearchType.STARTS_WITH, SearchType.CASE_INSENSITIVE).addSearchTextType(SearchType.CASE_SENSITIVE), "//*[not(starts-with(translate(@data,'VALUE','value'),'value'))]"},

                {new WebLocator().setAttribute("data", "value", SearchType.NOT, SearchType.CONTAINS, SearchType.TRIM), "//*[not(contains(normalize-space(@data),'value'))]"},
                {new WebLocator().setAttribute("data", "value", SearchType.NOT, SearchType.EQUALS, SearchType.TRIM), "//*[not(normalize-space(@data)='value')]"},
                {new WebLocator().setAttribute("data", "value", SearchType.NOT, SearchType.STARTS_WITH, SearchType.TRIM), "//*[not(starts-with(normalize-space(@data),'value'))]"},
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

    @Test(dataProvider = "testConstructorPathDataProviderNotAttributes")
    public void getPathSelectorCorrectlyFromConstructorsByNotAttributes(WebLocator el, String expectedXpath) {
        assertThat(el.getXPath(), equalTo(expectedXpath));
    }

    @Test
    public void getPathSelectorSetTagWhenWebLocatorHasClsAndContainer() {
        WebLocator el = new WebLocator("testcls", container);
        el.setTag("textarea");
        assertThat(el.getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' container ')]//textarea[contains(concat(' ', @class, ' '), ' testcls ')]"));
    }

    @Test
    public void getPathSelectorSetTagWhenWebLocatorHasTextAndClsAndContainer() {
        WebLocator el = new WebLocator("text", "testcls", container);
        el.setTag("textarea");
        assertThat(el.getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' container ')]//textarea[contains(concat(' ', @class, ' '), ' testcls ') and contains(text(),'text')]"));
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasContainerAndPath() {
        WebLocator el = new WebLocator(container).setElPath("//*[contains(@class, 'testcls')]");
        el.setId("ID");
        assertThat(el.getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls')]"));
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasClsAndContainer() {
        WebLocator el = new WebLocator("testcls", container);
        el.setId("ID");
        assertThat(el.getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' container ')]//*[@id='ID' and contains(concat(' ', @class, ' '), ' testcls ')]"));
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasClsAndContainer1() {
        WebLocator el = new WebLocator(container);
        el.setClasses(Operand.OR, "ID", "CLASS");
        assertThat(el.getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' ID ') or contains(concat(' ', @class, ' '), ' CLASS ')]"));
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasTextAndClsAndContainer() {
        WebLocator el = new WebLocator("text", "testcls", container);
        el.setId("ID");
        assertThat(el.getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' container ')]//*[@id='ID' and contains(concat(' ', @class, ' '), ' testcls ') and contains(text(),'text')]"));
    }

    //@Test
    // TODO fix getPathSelectorSetIdWhenWebLocatorHasXPath
    public void getPathSelectorSetIdWhenWebLocatorHasXPath() {
        WebLocator el = new WebLocator().setElPath("//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls') and contains(text(),'text')]");
        el.setId("ID");
        assertThat(el.getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' container ')]//*[@id='ID' and contains(@class, 'testcls') and contains(text(),'text')]"));
    }

    @Test
    public void createInstancesWithBuilders() {
        WebLocator locatorBuilder1 = new WebLocator().setTag("div").setId("ID1");
        assertThat(locatorBuilder1.getPathBuilder().getTag(), equalTo("div"));
        assertThat(locatorBuilder1.getPathBuilder().getId(), equalTo("ID1"));
        assertThat(locatorBuilder1.getPathBuilder().getClassName(), equalTo("WebLocator"));
    }

    @Test
    public void shouldShowClassInToStringWhenHasOneClass() {
        WebLocator locator = new WebLocator().setClasses("cls1");
        assertThat(locator.getPathBuilder().toString(), equalTo("cls1"));
    }

    @Test
    public void shouldShowClassesInToStringWhenHasManyClass() {
        WebLocator locator = new WebLocator().setClasses("cls1", "cls2");
        assertThat(locator.getPathBuilder().toString(), equalTo("[cls1, cls2]"));
    }

    @Test
    public void resetSearchTextType() {
        WebLocator locator = new WebLocator().setText("text", SearchType.EQUALS);
        assertThat(locator.getXPath(), equalTo("//*[text()='text']"));
        locator.setSearchTextType(null);
        assertThat(locator.getXPath(), equalTo("//*[contains(text(),'text')]"));
    }

    @Test
    public void verifyxPath() {
        WebLocator locator = new WebLocator().setBaseCls("cls").setText("text", SearchType.DEEP_CHILD_NODE);
        assertThat(locator.getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' cls ') and count(*//text()[contains(.,'text')]) > 0]"));
        locator.setContainer(container);
        assertThat(locator.getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' cls ') and count(*//text()[contains(.,'text')]) > 0]"));
    }

    @Test
    public void setSearchTextType() {
        WebLocator locator = new WebLocator().setText("text", SearchType.STARTS_WITH);
        assertThat(locator.getPathBuilder().getSearchTextType().size(), is(1));
    }

    @Test
    public void setResultIdx() {
        WebLocator locator = new WebLocator().setText("text").setResultIdx(1);
        WebLocator locator1 = new WebLocator(locator).setText("text").setResultIdx(2);
        WebLocator locator2 = new WebLocator().setText("text").setResultIdx(Position.LAST);
        assertThat(locator2.getXPath(), equalTo("(//*[contains(text(),'text')])[last()]"));
        assertThat(locator1.getXPath(), equalTo("((//*[contains(text(),'text')])[1]//*[contains(text(),'text')])[2]"));
        locator1.setResultIdx(-1);
        assertThat(locator1.getXPath(), equalTo("(//*[contains(text(),'text')])[1]//*[contains(text(),'text')]"));
        locator.setResultIdx(-1);
        assertThat(locator1.getXPath(), equalTo("//*[contains(text(),'text')]//*[contains(text(),'text')]"));

        WebLocator locator3 = new WebLocator().setText("text").setResultIdx(Position.LAST).setPosition(Position.LAST);
        assertThat(locator3.getXPath(), equalTo("(//*[contains(text(),'text')][position() = last()])[last()]"));
    }

    @Test
    public void setPosition() {
        WebLocator locator = new WebLocator().setText("text").setPosition(1);
        WebLocator locator1 = new WebLocator(locator).setText("text").setPosition(2);
        WebLocator locator2 = new WebLocator().setText("text").setPosition(Position.LAST);
        assertThat(locator2.getXPath(), equalTo("//*[contains(text(),'text')][position() = last()]"));
        assertThat(locator1.getXPath(), equalTo("//*[contains(text(),'text')][position() = 1]//*[contains(text(),'text')][position() = 2]"));
        locator1.setPosition(-1);
        assertThat(locator1.getXPath(), equalTo("//*[contains(text(),'text')][position() = 1]//*[contains(text(),'text')]"));
        locator.setPosition(-1);
        assertThat(locator1.getXPath(), equalTo("//*[contains(text(),'text')]//*[contains(text(),'text')]"));
    }

    @Test
    public void setChildNodes() {
        WebLocator child = new WebLocator().setText("child").setPosition(1);
        WebLocator childFirst = new WebLocator().setText("childFirst").setPosition(Position.FIRST);
        WebLocator parent = new WebLocator().setClasses("parent").setChildNodes(child, childFirst);

        assertThat(parent.getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' parent ') and count(.//*[contains(text(),'child')][position() = 1]) > 0 and count(.//*[contains(text(),'childFirst')][position() = first()]) > 0]"));

        WebLocator childSecond = new WebLocator().setText("second");
        parent.setChildNodes(childSecond, child, null);
        assertThat(parent.getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' parent ') and count(.//*[contains(text(),'second')]) > 0 and count(.//*[contains(text(),'child')][position() = 1]) > 0]"));
    }

    @Test
    public void setChildNodes1() {
        WebLocator child = new WebLocator().setText("child").setPosition(1);
        WebLocator childFirst = new WebLocator().setText("childFirst").setPosition(Position.FIRST);
        WebLocator parent = new WebLocator().setClasses("parent").setChildNodes(SearchType.CONTAINS, child, childFirst);

        assertThat(parent.getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' parent ') and count(.//*[contains(text(),'child')][position() = 1]) > 0 or count(.//*[contains(text(),'childFirst')][position() = first()]) > 0]"));

        WebLocator childSecond = new WebLocator().setText("second");
        parent.setChildNodes(childSecond, child, null);
        assertThat(parent.getXPath(), equalTo("//*[contains(concat(' ', @class, ' '), ' parent ') and count(.//*[contains(text(),'second')]) > 0 or count(.//*[contains(text(),'child')][position() = 1]) > 0]"));
    }

    @Test
    public void setTemplate() {
        WebLocator child = new WebLocator().setTemplate("custom", "%1$s = %2$s");
        child.setTemplateValue("custom", "a", "b");

        assertThat(child.getXPath(), equalTo("//*[a = b]"));
    }

    @Test
    public void setSometimes() {
        WebLocator child = new WebLocator().setText("a", SearchType.CONTAINS, SearchType.DEEP_CHILD_NODE);
        child.setSearchTextType(SearchType.EQUALS);
        child.setSearchTextType(SearchType.STARTS_WITH);

        List<SearchType> types = new ArrayList<>();
        types.add(SearchType.STARTS_WITH);
        types.add(SearchType.DEEP_CHILD_NODE);
        List<SearchType> searchTextType = child.getPathBuilder().getSearchTextType();

        assertThat(searchTextType, contains(types.toArray()));
    }
}
