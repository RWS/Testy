package com.sdl.selenium.web;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class WebLocatorCssTest {

    private static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorCssSelectorDataProvider() {
        return new Object[][]{
                {new WebLocator(), null},
                {new WebLocator().setTag("td"), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls"), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes"), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls"), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls"), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id"), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label"), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info"), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name"), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'"), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block"), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container).setText("Text"), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container).setText("Text").setTitle("Title"), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container).setText("Text").setTitle("Title").setRoot("./"), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container).setText("Text").setTitle("Title").setRoot("./").setResultIdx(2), null},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container).setText("Text").setTitle("Title").setRoot("./").setResultIdx(2).setType("type"), null},

                {new WebLocator("testcls"), null},
                {new WebLocator(container), null},
                {new WebLocator(container).setClasses("Cls"), null},
                {new WebLocator("testcls", container), null},
                {new WebLocator("text", "testcls", container), null},
                {new WebLocator(container).setTag("textarea"), null},
                {new WebLocator(container).setElCssSelector(".testcls").setTag("textarea"), ".testcls"},
                {new WebLocator().setElPathSuffix("has-div", "count(div) > 0"), null},
                {new WebLocator().setExcludeClasses("cls1", "cls2"), null},
        };
    }

    @Test(dataProvider = "testConstructorCssSelectorDataProvider")
    public void getCssSelectorCorrectlyFromConstructors(WebLocator el, String expectedXpath) {
        assertThat(el.getCssSelector(), equalTo(expectedXpath));
    }
}
