package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.bootstrap.form.Form;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TooltipTest {

    private static Form container = new Form(null, "Form");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Tooltip(),                      "//*[contains(concat(' ', @class, ' '), ' tooltip ')]"},
                {new Tooltip("Message"),             "//*[contains(concat(' ', @class, ' '), ' tooltip ') and count(//*[@class='tooltip-inner' and text()='Message']) > 0]"},
                {new Tooltip("Message").setId("Id"), "//*[@id='Id' and contains(concat(' ', @class, ' '), ' tooltip ') and count(//*[@class='tooltip-inner' and text()='Message']) > 0]"},
                {new Tooltip("Message").setContainer(container), "//form[count(.//legend[text()='Form']) > 0]//*[contains(concat(' ', @class, ' '), ' tooltip ') and count(//*[@class='tooltip-inner' and text()='Message']) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Tooltip tooltip, String expectedXpath) {
        assertThat(tooltip.getXPath(), equalTo(expectedXpath));
    }
}
