package com.sdl.unit.web;

import com.sdl.selenium.web.Position;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WebLocatorCloneTest {
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

                {new WebLocator(container).setTag("td").setAttribute("a", "b", SearchType.EQUALS), "//*[contains(concat(' ', @class, ' '), ' container ')]//td[@a='b']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(WebLocator el, String expectedXpath) {
        WebLocator clone = el.clone();
        String operand;
        if (clone.getPathBuilder().getContainer() == null) {
            clone.setContainer(new WebLocator().setId("xID"));
            operand = "//*[@id='xID']" + expectedXpath;
        } else {
            operand = expectedXpath;
        }
        assertThat(clone.getXPath(), equalTo(operand));
        assertThat(el.getXPath(), equalTo(expectedXpath));
    }
}
