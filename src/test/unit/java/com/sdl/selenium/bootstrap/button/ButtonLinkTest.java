package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Cell;
import com.sdl.selenium.web.table.Table;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ButtonLinkTest {
    public static WebLocator container = new WebLocator().withId("ID");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new ButtonLink(),                  "//a[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new ButtonLink(container),         "//*[@id='ID']//a[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new ButtonLink(container, "ButtonText"), "//*[@id='ID']//a[contains(concat(' ', @class, ' '), ' btn ') and text()='ButtonText']"},
                {new ButtonLink(container, "ButtonText").withSearchTextType(SearchType.CONTAINS), "//*[@id='ID']//a[contains(concat(' ', @class, ' '), ' btn ') and contains(text(),'ButtonText')]"},
                {new ButtonLink(container).withId("ID"), "//*[@id='ID']//a[@id='ID' and contains(concat(' ', @class, ' '), ' btn ')]"},
                {new ButtonLink(new Table().getRow(new Cell(1, "Test", SearchType.EQUALS)), "ButtonText"), "//table//tr[count(.//td[1][(.='Test' or count(*//text()[.='Test']) > 0)]) > 0]//a[contains(concat(' ', @class, ' '), ' btn ') and text()='ButtonText']"},
                {new ButtonLink(container).withElxPathSuffix("a", "aa").withElxPathSuffix("a", ""), "//*[@id='ID']//a[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new ButtonLink(container).withTemplate("a", "aa").withTemplate("a", ""), "//*[@id='ID']//a[contains(concat(' ', @class, ' '), ' btn ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(ButtonLink button, String expectedXpath) {
        Assert.assertEquals(button.getXPath(), expectedXpath);
    }
}