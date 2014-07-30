package com.sdl.bootstrap.window;

import com.sdl.bootstrap.form.Form;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TooltipTest {

    private static Form container = new Form(null, "Form");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Tooltip(),                      "//*[contains(concat(' ', @class, ' '), ' tooltip ')]"},
                {new Tooltip("Message"),             "//*[count(//*[@class='tooltip-inner' and text()='Message']) > 0 and contains(concat(' ', @class, ' '), ' tooltip ')]"},
                {new Tooltip("Message").setId("Id"), "//*[@id='Id' and count(//*[@class='tooltip-inner' and text()='Message']) > 0 and contains(concat(' ', @class, ' '), ' tooltip ')]"},
                {new Tooltip("Message").setContainer(container), "//form[count(.//legend[text()='Form']) > 0]//*[count(//*[@class='tooltip-inner' and text()='Message']) > 0 and contains(concat(' ', @class, ' '), ' tooltip ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Tooltip tooltip, String expectedXpath) {
        Assert.assertEquals(tooltip.getPath(), expectedXpath);
    }
}
