package com.sdl.unit.extjs6.slider;

import com.sdl.selenium.extjs6.slider.Slider;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SliderTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Slider(),                        "//*[contains(concat(' ', @class, ' '), ' x-slider ')]"},
                {new Slider(container),               "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-slider ')]"},
                {new Slider(container, "SliderText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='SliderText']//following-sibling::*//*[contains(concat(' ', @class, ' '), ' x-slider ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Slider slider, String expectedXpath) {
        assertThat(slider.getXPath(), equalTo(expectedXpath));
    }
}
