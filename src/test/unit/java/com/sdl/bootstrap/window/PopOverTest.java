package com.sdl.bootstrap.window;

import com.sdl.bootstrap.form.Form;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
        Assert.assertEquals(popOver.getPath(), expectedXpath);
    }

}
