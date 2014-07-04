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
                {new PopOver("Title"),             "//*[count(.//*[@class='popover-title' and text()='Title'])> 0 and contains(concat(' ', @class, ' '), ' popover ')]"},
                {new PopOver("Title").setContainer(container), "//form[count(.//legend[text()='Form']) > 0]//*[count(.//*[@class='popover-title' and text()='Title'])> 0 and contains(concat(' ', @class, ' '), ' popover ')]"},
                {new PopOver("Title", "Message"),  "//*[count(.//*[@class='popover-title' and text()='Title']//following-sibling::*[@class='popover-content' and text()='Message'])> 0 and contains(concat(' ', @class, ' '), ' popover ')]"},
                {new PopOver("Title", "Message").setContainer(container),  "//form[count(.//legend[text()='Form']) > 0]//*[count(.//*[@class='popover-title' and text()='Title']//following-sibling::*[@class='popover-content' and text()='Message'])> 0 and contains(concat(' ', @class, ' '), ' popover ')]"},
        };
    }

    @Test (dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(PopOver popOver, String expectedXpath) {
        Assert.assertEquals(popOver.getPath(), expectedXpath);
    }

}
