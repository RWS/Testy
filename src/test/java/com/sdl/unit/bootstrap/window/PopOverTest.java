package com.sdl.unit.bootstrap.window;

import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.bootstrap.window.PopOver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PopOverTest {
    private static Form container = new Form(null, "Form");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new PopOver("Title"),             "//*[contains(concat(' ', @class, ' '), ' popover ') and count(.//*[@class='popover-title' and text()='Title'])> 0]"},
                {new PopOver("Title").setContainer(container), "//form[count(.//legend[text()='Form']) > 0]//*[contains(concat(' ', @class, ' '), ' popover ') and count(.//*[@class='popover-title' and text()='Title'])> 0]"},
                {new PopOver("Title", "Message"),  "//*[contains(concat(' ', @class, ' '), ' popover ') and count(.//*[@class='popover-title' and text()='Title']//following-sibling::*[@class='popover-content' and text()='Message'])> 0]"},
                {new PopOver("Title", "Message").setContainer(container),  "//form[count(.//legend[text()='Form']) > 0]//*[contains(concat(' ', @class, ' '), ' popover ') and count(.//*[@class='popover-title' and text()='Title']//following-sibling::*[@class='popover-content' and text()='Message'])> 0]"},
        };
    }

    @Test (dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(PopOver popOver, String expectedXpath) {
        assertThat(popOver.getXPath(), equalTo(expectedXpath));
    }

}
